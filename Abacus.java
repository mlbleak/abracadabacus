package abracadabacus;

import java.awt.Point;
import java.util.*;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * A chinese abacus
 * 
 * @author Myron Burton
 *
 */
public class Abacus {

	public int columns;
	private int frameWidth;
	private int frameHeight = 500;
	private Point frameLoc = new Point(50, 50);
	private int midBarY = 200;
	private int strokeWidth = 25;
	private Color frameColor = Color.web("5D6A9F");
	private Map<Column, List<Bead>> beads = new HashMap<>();
	private long count = 0;
	private Label valueOut;
	private AbracadAbacus parent;
	
	Abacus (AbracadAbacus parent, int columns, int size) {
		this.parent = parent;
		if (columns > 18) {
			columns = 18;
		}
		if (columns < 1) {
			columns = 1;
		}
		strokeWidth = size;
		frameHeight = size * 20;
		midBarY = (int) (frameLoc.getY() + size * 6);
		
		this.columns = columns;
		frameWidth = (int)((columns + 1) * strokeWidth * 3.25);

		Abacus self = this;
	    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), ev -> {
	    	try {
	    		self.parent.sendData(self.count);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }));
	    timeline.setCycleCount(Animation.INDEFINITE);
	    timeline.play();
	}
	
	public Scene getScene() throws Exception {

		// build pane and scene
		Pane pane = new Pane();
		pane.setPrefSize(frameWidth + frameLoc.getX() * 2, frameHeight + frameLoc.getY() * 2 + strokeWidth);
		Scene scene = new Scene(pane);
		
		// outer frame of the abacus
		Rectangle frame = new Rectangle();
		frame.setX(frameLoc.getX());
		frame.setY(frameLoc.getY());
		frame.setWidth(frameWidth);
		frame.setHeight(frameHeight);
		frame.setArcWidth(75);
		frame.setArcHeight(75);
		frame.setFill(Color.WHITE);
		frame.setStroke(frameColor);
		frame.setStrokeWidth(strokeWidth);
		// add the outer frame to the pane
		pane.getChildren().add(frame);
		
		// separator of Ones and Fives values
		Rectangle separator = new Rectangle();
		separator.setX(frameLoc.getX());
		separator.setY(midBarY);
		separator.setWidth(frameWidth);
		separator.setHeight(strokeWidth);
		separator.setFill(frameColor);
		// add the mid bar to the pane
		pane.getChildren().add(separator);
		
		// Calculates the spacing of the columns
		int columnSpacing = frameWidth / (columns + 1);
		
		// build the columns and beads. Building goes from right to left
		for (int columnNumber = columns; columnNumber > 0; columnNumber--) {
			
			Column currentColumn = Column.values()[columns - columnNumber];
			beads.put(currentColumn, new ArrayList<Bead>());

			// building of the column
			Rectangle column = new Rectangle();
			column.setX(frameLoc.getX() + columnSpacing * columnNumber - strokeWidth/2);
			column.setY(frameLoc.getY());
			column.setHeight(frameHeight);
			column.setWidth(strokeWidth);
			column.setFill(frameColor);
			// add the column to the frame.
			pane.getChildren().add(column);
			
			// builds the Ones beads.
			for (int beadNumber = 0; beadNumber < 5; beadNumber++) {
				// make the bead
				beads.get(currentColumn).add(new Bead(currentColumn, Value.ONES,
						columnSpacing, frameLoc, columns, frameHeight - strokeWidth*3/4, beadNumber,
						midBarY, strokeWidth, this));
				// add the bead's ellipse to be drawn
				pane.getChildren().add(beads.get(currentColumn).get(beadNumber).getEllipse());
			}
			
			// builds the Fives beads.
			for (int beadNumber = 0; beadNumber < 2; beadNumber ++) {
				beads.get(currentColumn).add(new Bead(currentColumn, Value.FIVES,
							columnSpacing, frameLoc, columns,
							frameLoc.y + strokeWidth/4, beadNumber,
							midBarY, strokeWidth, this));
				// make the bead and then grab the ellipse
				pane.getChildren().add(beads.get(currentColumn).get(beadNumber + 5).getEllipse());
			}
		}
		
		valueOut = new Label("0");
		valueOut.setLayoutX(frameLoc.getX() - strokeWidth);
		valueOut.setLayoutY(frameHeight + frameLoc.getY() + strokeWidth/2);
		valueOut.setMinWidth(frameWidth);
		valueOut.setMaxWidth(frameWidth);
		valueOut.setAlignment(Pos.CENTER_RIGHT);
		valueOut.setTextFill(frameColor);
		valueOut.setFont(new Font("Gill Sans Light", 52));
		valueOut.setMaxHeight(frameLoc.getY() - strokeWidth/2);
		pane.getChildren().add(valueOut);
		
		// return the scene
		return scene;
	}

	/**
	 * This will move any beads that need to be moved given the starting bead number,
	 * counter, column and value.
	 * 
	 * @param beadNumber number of the bead
	 * @param currentCount current counter of the bead
	 * @param column column of the bead
	 * @param value value of the bead
	 */
	public void move(int beadNumber, Counter currentCounter, Column column, Value value) {
		
		// fives
		if (value == Value.FIVES) {
			
			// add five to our bead number because generation of beads goes from bottom to top.
			// Thus the fives beads are 5 and 6
			beadNumber += 5;
			
			// figure out if we are decrementing or incrementing
			// decrement
			if (currentCounter == Counter.COUNTED) {
				// move the bead that called the function
				beads.get(column).get(beadNumber).move();
				// move the other bead if applicable
				if (beadNumber == 5 && beads.get(column).get(6).getCounter() == Counter.COUNTED) {
					beads.get(column).get(6).move();
				}
			}
			// increment
			else {
				// move the bead that called the function
				beads.get(column).get(beadNumber).move();
				// move the other bead if applicable
				if (beadNumber == 6 && beads.get(column).get(5).getCounter() == Counter.NOT_COUNTED) {
					beads.get(column).get(5).move();
				}
			}
		}
		
		// ones
		else {
			
			// check to see if we are incrementing or decrementing
			// decrement
			if (currentCounter == Counter.COUNTED) {
				// move the bead that called
				beads.get(column).get(beadNumber).move();
				// see if there are any beads that are below the bead clicked on that also need to be moved
				for (int i = 1; beadNumber - i >= 0
						&& beads.get(column).get(beadNumber - i).getCounter() == Counter.COUNTED; i++) {
					beads.get(column).get(beadNumber - i).move();
				}
			}
			// increment
			else {
				// first move the bead that called this
				beads.get(column).get(beadNumber).move();
				// see if there are any beads that are above the bead clicked on that also need to be moved
				for (int i = 1;
						beads.get(column).get(beadNumber + i).getCounter() == Counter.NOT_COUNTED
						&& beadNumber + i < 5; i++) {
					beads.get(column).get(beadNumber + i).move();
				}				
			}
		}
		
		// find the count to update on the screen
		count();
	}

	/**
	 * Tallies up the total value of the beads.
	 */
	private void count() {

		// start fresh
		count = 0;
		
		// grabs each list and then each item
		// and adds its value to the count.
		beads.values().forEach(column -> {
			column.forEach(bead -> {
				count += bead.getValue();
			});
		});
		
		// change the textual output of the value
		valueOut.setText(String.valueOf(count));
	}
}

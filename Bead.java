package abracadabacus;

import java.awt.Point;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.util.Duration;

/**
 * Beads to be placed in the abacus
 * 
 * @author Myron Burton
 *
 */
@SuppressWarnings("unused")
public class Bead {
	
	private Column column;
	private Value value;
	private Counter counter = Counter.NOT_COUNTED;
	private Abacus parent;
	private Color color;
	private Ellipse bead;
	private int beadOffset;
	private boolean isTranslated = false;
	private int beadNumber;
	private int midBarY;

	/**
	 * This class builds a bead. The bead includes the logic of what its value is
	 * and where it is located on the board. It also includes the ellipse that is
	 * drawn to the board. To draw the ellipse use the .toEllipse function.
	 * 
	 * @param column Column enum where the bead is.
	 * @param value Value enum determining whether the bead is a Five or a One.
	 * @param counter Whether or not the bead is currently counted.
	 * @param columnWidth Distance between columns on the board
	 * @param frameLoc Point where the frame is on the board.
	 * @param columns Number of columns being drawn.
	 * @param beadStartY Where the first bead in the column and value is to be located on the Y axis.
	 * @param beadNumber Which bead this is in the column and value.
	 * @param midBarY Location of the separator of Ones and Fives values.
	 * @param strokeWidth Width of the frame for positioning.
	 */
	Bead(Column column, Value value, int columnWidth, Point frameLoc,
			int columns, int beadStartY, int beadNumber,
			int midBarY, int strokeWidth, Abacus parent) {
		
		this.column = column;
		this.value = value;
		this.parent = parent;
		this.beadNumber = beadNumber;
		this.midBarY = midBarY;
		// the offset is determined with relation to the stroke width.
		// 1.5 is an arbitrary value that may be changed.
		beadOffset = (int)(strokeWidth * 1.5);
		
		// set the color depending on what value was given
		if (value == Value.FIVES) {
			// yellow
			color = Color.web("F5E379");
		}
		else {
			// green
			color = Color.web("88B560");
		}
		
		// make the ellipse
		bead = new Ellipse();
		bead.setCenterX(frameLoc.getX() + (columnWidth) * (columns - column.ordinal()));
		bead.setCenterY(frameLoc.getY() + beadStartY - beadNumber * beadOffset);
		bead.setRadiusX(strokeWidth * 1.5);
		bead.setRadiusY(strokeWidth * .75);
		bead.setFill(color);
		
		// make the beads clickable
		bead.setOnMouseClicked(new EventHandler<MouseEvent>() {
		
			@Override
			public void handle(MouseEvent event) {
				parent.move(beadNumber, counter, column, value);
			}
		});
	}
	
	/**
	 * This gives the JavaFX ellipse drawing of the bead.
	 * @return 
	 */
	public Ellipse getEllipse() {
		return bead;
	}
	
	/**
	 * Moves the bead within the area available
	 */
	public void move() {

		// build the transition
		TranslateTransition transition = new TranslateTransition(Duration.millis(100), getEllipse());

		// if it hasn't been translated, translate
		if (counter == Counter.NOT_COUNTED) {
			transition.setToX(0);
			// this moves the fives towards the distance to the midbar minus the center
			// minus the number of beads before it
			if (value == Value.FIVES) {
				transition.setToY(midBarY - getEllipse().getCenterY() - beadNumber * beadOffset);
			}
			// this moves the ones down the distance of the midbar and then removes the
			// the distance where it was originally. This puts the bead on the midbar.
			// then we move it down the number of beads above it. (Sorry, this one is complicated)
			else {
				transition.setToY(midBarY - getEllipse().getCenterY() - beadOffset * (beadNumber - 5));
			}
			
			transition.setCycleCount(1);
			transition.setAutoReverse(false);
			transition.play();
			
		}
		// if it has been translated, restore
		else {
			// move the bead back to its original position.
			transition.setToX(0);
			transition.setToY(0);
			transition.setCycleCount(1);
			transition.setAutoReverse(false);
			transition.play();
		}
		
		// update the count
		changeCounter();
	}
	
	/**
	 * Flips the position to counted or not counted.
	 */
	public void changeCounter() {
		if (counter.equals(Counter.COUNTED)) {
			counter = Counter.NOT_COUNTED;
		}
		else {
			counter = Counter.COUNTED;
		}
	}
	
	/**
	 * Returns whether the bead is counted or not.
	 * 
	 * @return current position
	 */
	public Counter getCounter() {
		return counter;
	}

	/**
	 * Calculates the current value of the bead.
	 * 
	 * @return integer value of bead
	 */
	public long getValue() {
		
		int count = counter.ordinal();
		int val = (value.ordinal() == 1) ? 1 : 5;
		int col = column.ordinal();
		
		return (long)(Math.pow(10, col) * val * count);
	}
}
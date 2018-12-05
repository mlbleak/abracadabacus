package abracadabacus;

import java.awt.Point;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ChooseAdventure {
	public int columns;
	
	private int rectangleWidth;
	private int rectangleHeight;
	private int spaceBetweenRect;
	private int paneHeight;
	private int rectangleHeightStretched;
	private int paneWidth = 1000;
	private String rectangleColorHover = "9f9f9f";
	private AbracadAbacus parentController;
	
	ChooseAdventure (AbracadAbacus parent) {
		parentController = parent;
		paneHeight = paneWidth * 7 / 10;
		spaceBetweenRect = paneWidth / 16;
		rectangleWidth = paneWidth / 5;
		rectangleHeight = paneHeight / 6;
		rectangleHeightStretched = rectangleHeight * 3;
	}
	
	public Text generateText(String contents, int position, double fontSize,
			Color color, String type, Rectangle frame) {
		Text text = new Text(contents);
		text.setX(position);
		text.setFont(new Font(fontSize));
		text.setFill(Color.WHITE);
		text.setOnMouseEntered(e -> {
			frame.setFill(Color.web(rectangleColorHover));
			frame.setCursor(Cursor.HAND);
			text.setCursor(Cursor.HAND);
			}
		);
		text.setOnMouseExited(e -> frame.setFill(color));
		text.setOnMouseClicked(e -> {try {
			parentController.setAdventure(type);
		} catch (Exception e1) {
			e1.printStackTrace();
		}});

		return text;
	}
	
	public ImageView getImage(String type, Color color) {
		final ImageView out = new ImageView();
		Image image1 = null;
		if (type == "count") {
			image1 = new Image(GradeChoose.class.getResourceAsStream("count123.png"));
		} else if (type == "subtract") {
			image1 = new Image(GradeChoose.class.getResourceAsStream("minus.png"));
		} else if (type == "add") {
			image1 = new Image(GradeChoose.class.getResourceAsStream("plus.png"));
		}
		out.setImage(image1);
		out.setFitWidth(paneWidth/5);
		out.setFitHeight(paneHeight/2.5);
		out.setLayoutX(spaceBetweenRect);
		
		return out;
	}
	
	public StackPane gradeChoiceFrame(Point loc, String type, Color color) {
		Rectangle frame = new Rectangle();
		frame.setWidth(rectangleWidth);
		frame.setHeight(rectangleHeightStretched);
		frame.setArcWidth(75);
		frame.setArcHeight(75);
		frame.setFill(color);
		frame.setStrokeWidth(0);
		frame.setOnMouseEntered(e -> {
			frame.setCursor(Cursor.HAND);
			}
		);
		
		frame.setOnMouseExited(e -> frame.setFill(color));
		frame.setOnMouseClicked(e -> {
			try {
				parentController.setAdventure(type);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		StackPane stack = new StackPane();
		stack.setLayoutX(loc.getX());
		stack.setLayoutY(loc.getY());
		stack.getChildren().addAll(frame);
		ImageView pict = getImage(type, color);
		pict.setOnMouseClicked(e -> {try {
			parentController.setAdventure(type);
		} catch (Exception e1) {
			e1.printStackTrace();
		}});
		pict.setOnMouseEntered(e -> {
			pict.setCursor(Cursor.HAND);
			}
		);
		stack.getChildren().addAll(pict);
		
		return stack;
	}
	
	public Point calcRectPoint(int posX, int posY) {
		int x = 0;
		int y = 0;
		x = (spaceBetweenRect * 2) + (rectangleWidth + spaceBetweenRect) * posX;
		y = paneHeight - (rectangleHeightStretched) - (spaceBetweenRect * 2)
				- (rectangleHeight + spaceBetweenRect) * posY;
		return new Point(x, y);
	}
	
	public Scene getScene() throws Exception {

		// build pane and scene
		Pane pane = new Pane();
		pane.setPrefSize(paneWidth,paneHeight);
		Scene scene = new Scene(pane);
        final ImageView selectedImage = new ImageView();   
        Image image1 = new Image(GradeChoose.class.getResourceAsStream("abracadabracus.png"));
        selectedImage.setImage(image1);
        selectedImage.setFitWidth(paneWidth/5);
        selectedImage.setFitHeight(paneHeight/5);
        selectedImage.setLayoutX(spaceBetweenRect);
        pane.getChildren().add(selectedImage);
        

		Text textTop = new Text(spaceBetweenRect + paneWidth/5, spaceBetweenRect*1.5, "Choose your adventure!");
		textTop.setFont(new Font(rectangleHeight/2.5));
		textTop.setFill(Color.web("6172B3"));
		pane.getChildren().add(textTop);

		
		StackPane count = gradeChoiceFrame(calcRectPoint(0, 0), "count", Color.web("D36B51"));
		StackPane add = gradeChoiceFrame(calcRectPoint(1, 0), "add", Color.web("F5E379"));
		StackPane subtract = gradeChoiceFrame(calcRectPoint(2, 0), "subtract", Color.web("8B6396"));
		pane.getChildren().add(count);
		pane.getChildren().add(add);
		pane.getChildren().add(subtract);

		return scene;
	}
}

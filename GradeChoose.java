package abracadabacus;

import java.awt.Point;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GradeChoose {

	public int columns;
	private int rectangleWidth;
	private int rectangleHeight;
	private int spaceBetweenRect;
	private int paneHeight;
	private int paneWidth = 1000;
	private String rectangleColor = "8EBC64";
	private String rectangleColorHover = "6C8D4E";
	private AbracadAbacus parentController;
	
	GradeChoose (AbracadAbacus parent) {
		parentController =  parent;
		paneHeight = paneWidth * 7 / 10;
		spaceBetweenRect = paneWidth / 16;
		rectangleWidth = paneWidth / 5;
		rectangleHeight = paneHeight / 6;
	}
	
	public StackPane gradeChoiceFrame(Point loc, String letter) {
		Rectangle frame = new Rectangle();
		Text text = new Text(rectangleWidth/2, rectangleHeight, letter);
		text.setFont(new Font(rectangleHeight/1.5));
		text.setFill(Color.WHITE);
		text.setOnMouseEntered(e -> {
			frame.setFill(Color.web(rectangleColorHover));
			frame.setCursor(Cursor.HAND);
			text.setCursor(Cursor.HAND);
			}	
		);
		text.setOnMouseExited(e -> frame.setFill(Color.web(rectangleColor)));

		frame.setWidth(rectangleWidth);
		frame.setHeight(rectangleHeight);
		frame.setArcWidth(75);
		frame.setArcHeight(75);
		frame.setFill(Color.web(rectangleColor));
		frame.setStrokeWidth(0);
		frame.setOnMouseEntered(e -> {
			frame.setFill(Color.web(rectangleColorHover));
			frame.setCursor(Cursor.HAND);
			text.setCursor(Cursor.HAND);
			}	
		);
		
		frame.setOnMouseExited(e -> frame.setFill(Color.web(rectangleColor)));
		frame.setOnMouseClicked(e -> {try {
			parentController.setGrade(letter);
		} catch (Exception e1) {
			e1.printStackTrace();
		}});
		text.setOnMouseClicked(e -> {try {
			parentController.setGrade(letter);
		} catch (Exception e1) {
			e1.printStackTrace();
		}});

		StackPane stack = new StackPane();
		stack.setLayoutX(loc.getX());
		stack.setLayoutY(loc.getY());
		stack.getChildren().addAll(frame, text);
		
		return stack;
	}
	
	public Point calcRectPoint(int posX, int posY) {
		int x = 0; 
		int y = 0;
		x = (spaceBetweenRect * 2) + (rectangleWidth + spaceBetweenRect) * posX;
		y = paneHeight - rectangleHeight - (spaceBetweenRect * 2)
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
        selectedImage.setLayoutX(paneWidth - (spaceBetweenRect)*5);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setLayoutX(150);
        grid.setLayoutY(30);
        grid.setHgap(-90);
        grid.setVgap(-paneHeight/15);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.RIGHT);
        grid.getColumnConstraints().add(column1);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHalignment(HPos.LEFT);
        grid.getColumnConstraints().add(column2);
        
        grid.add(selectedImage, 1, 0);
        
		Text textTop = new Text("Hello! Welcome  to Abracad");
		textTop.setTextAlignment(TextAlignment.RIGHT);
		textTop.setLayoutY(spaceBetweenRect*1.5);
		textTop.setFont(new Font(rectangleHeight/2.5));
		textTop.setFill(Color.web("6172B3"));
        grid.add(textTop, 0, 0);
		
		Text textTop2 = new Text("Abacus");
		textTop2.setTextAlignment(TextAlignment.RIGHT);
		textTop2.setLayoutX(paneWidth - (spaceBetweenRect)*6);
		textTop2.setLayoutY(spaceBetweenRect*1.5+rectangleHeight/2.5);
		textTop2.setFont(new Font(rectangleHeight/2.5));
		textTop2.setFill(Color.web("40559F"));
		grid.add(textTop2, 0, 1);
		pane.getChildren().add(grid);
		
		Text gradeQuestion = new Text(spaceBetweenRect*4, spaceBetweenRect*4, "What grade are you in?");
		gradeQuestion.setFont(new Font(rectangleHeight/2.5));
		gradeQuestion.setFill(Color.web(rectangleColor));
		pane.getChildren().add(gradeQuestion);

		StackPane frame3 = gradeChoiceFrame(calcRectPoint(0, 0), "3");
		StackPane frame4 = gradeChoiceFrame(calcRectPoint(1, 0), "4");
		StackPane frame5 = gradeChoiceFrame(calcRectPoint(2, 0), "5");
		StackPane frameK = gradeChoiceFrame(calcRectPoint(0, 1), "K");
		StackPane frame1 = gradeChoiceFrame(calcRectPoint(1, 1), "1");
		StackPane frame2 = gradeChoiceFrame(calcRectPoint(2, 1), "2");
		
		pane.getChildren().add(frameK);
		pane.getChildren().add(frame1);
		pane.getChildren().add(frame2);
		pane.getChildren().add(frame3);
		pane.getChildren().add(frame4);
		pane.getChildren().add(frame5);

		return scene;
	}
	
}

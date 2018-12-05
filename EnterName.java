package abracadabacus;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class EnterName {
	private AbracadAbacus parentController;
	private int paneWidth = 1000;
	private int paneHeight;
	private int spaceBetweenRect;
	private int rectangleWidth;
	private int rectangleHeight;
	
	EnterName(AbracadAbacus parent) {
		this.parentController = parent;
		this.paneHeight = this.paneWidth * 7 / 10;
		this.spaceBetweenRect = this.paneWidth / 16;
		this.rectangleWidth = this.paneWidth / 5;
		this.rectangleHeight = this.paneHeight / 6;
	}
	public Scene getScene() throws Exception {
		
		Pane pane = new Pane();
		pane.setPrefSize(this.paneWidth,this.paneHeight);
		Scene scene = new Scene(pane);
		
		Text textTop = new Text(this.spaceBetweenRect, this.spaceBetweenRect*1.5, "What is your name?");
		textTop.setFont(new Font(this.rectangleHeight / 1.2));
		textTop.setFill(Color.web("6172B3"));
		textTop.setLayoutY(this.rectangleHeight + 50);
		pane.getChildren().add(textTop);
		
		TextField textField = new TextField ();
		textField.setFont(Font.font("Verdana", FontWeight.BOLD, this.rectangleHeight / 2));
		textField.setPrefHeight(this.rectangleHeight / 1.2);
		textField.setPrefWidth(this.rectangleWidth * 2.5);
		textField.setOnKeyReleased(event -> {
			  if (event.getCode() == KeyCode.ENTER){
				  try {
						this.parentController.setName(textField.getText());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			  }
			});
		
		HBox hb = new HBox();
		hb.getChildren().add(textField);
		hb.setSpacing(10);
		hb.setLayoutX(this.paneWidth / 2 - this.rectangleWidth * 1.4);
		hb.setLayoutY(this.paneHeight / 2);
		
		pane.getChildren().add(hb);

		Button button = new Button("Enter");
		button.setFont(Font.font("Verdana", FontWeight.BOLD, this.rectangleHeight / 2));
		button.setPrefHeight(this.rectangleHeight / 2);
		button.setPrefWidth(this.rectangleWidth * 2);
		button.setLayoutX(this.paneWidth / 2 - this.rectangleWidth * 1.15);
		button.setLayoutY(this.paneHeight / 2 + (this.rectangleHeight) + 25);
		button.setOnMouseClicked(e -> {try {
			this.parentController.setName(textField.getText());
		} catch (Exception e1) {
			e1.printStackTrace();
		}});

		
		pane.getChildren().add(button);
		
		return scene;
	}
	
}

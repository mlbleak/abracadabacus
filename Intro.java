package abracadabacus;


import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Intro {
	private AbracadAbacus parentController;
	private int paneWidth = 1000;
	private int paneHeight;
	private int spaceBetweenRect;
	private int rectangleHeight;
	
	Intro(AbracadAbacus parent) {
		this.parentController = parent;
		this.paneHeight = this.paneWidth * 7 / 10;
		this.spaceBetweenRect = this.paneWidth / 16;
		this.rectangleHeight = this.paneHeight / 6;
	}
	

	public Scene getScene() throws Exception {

		// build pane and scene
		Pane pane = new Pane();
		pane.setPrefSize(this.paneWidth,this.paneHeight);
		Scene scene = new Scene(pane);
        final ImageView selectedImage = new ImageView();   
        Image image1 = new Image(GradeChoose.class.getResourceAsStream("abracadabracus.png"));
        selectedImage.setImage(image1);
        selectedImage.setFitWidth(this.paneWidth/2);
        selectedImage.setFitHeight(this.paneHeight/2);
        selectedImage.setLayoutX(this.paneWidth/2);
        pane.getChildren().add(selectedImage);
        

		Text textTop = new Text(this.spaceBetweenRect, this.spaceBetweenRect*1.5, "Abracad");
		textTop.setFont(new Font(this.rectangleHeight * 1.3));
		textTop.setFill(Color.web("6172B3"));
		textTop.setLayoutY(this.rectangleHeight + 50);
		pane.getChildren().add(textTop);
		
		Text textMiddle = new Text(this.spaceBetweenRect + 60, this.spaceBetweenRect*1.5, "Abacus");
		textMiddle.setFont(new Font(this.rectangleHeight * 1.3));
		textMiddle.setFill(Color.web("40559F"));
		textMiddle.setLayoutY(this.rectangleHeight * 2.2 + 50);
		pane.getChildren().add(textMiddle);
		
		pane.setOnMouseClicked(e -> {try {
			this.parentController.endIntro();
		} catch (Exception e1) {
			e1.printStackTrace();
		}});

		

		return scene;
	}
	
}

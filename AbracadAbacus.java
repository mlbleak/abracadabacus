package abracadabacus;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;


public class AbracadAbacus extends Application {
		
	static String[] arg;
	private int grade;
	private String Adventure;
	public Stage primaryStage;
	private String name;
	private Boolean showIntro = true;
	private int abacusColumns = 8;
	private int abacusSize = 32;
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void sendData(long data) {
		try {
			if (grade != 0) {
				SendData.send(name, abacusColumns, abacusSize, Integer.toString(grade), Adventure, data);
			}
			else {
				SendData.send(name, abacusColumns, abacusSize, "K", Adventure, data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void endIntro() throws Exception {
		if (showIntro) {
			primaryStage.setScene(new EnterName(this).getScene());
			showIntro = false;
		}
	}
	
	public void setName(String name) throws Exception {
		this.name = name;
		System.out.println(name);
		if (name.equals("Teacher")  || name.equals("teacher")) {
			new TeacherView(this);
		} else {
			primaryStage.setScene(new GradeChoose(this).getScene());
		}
	}
	
	public void setGrade(String newGrade) throws Exception {
		if (newGrade.equals("K")) {
			grade = 0;
		}
		else {
			grade = Integer.parseInt(newGrade);
		}
		System.out.println(grade);
		primaryStage.setScene(new ChooseAdventure(this).getScene());
	}

	public void setAdventure(String adv) throws Exception {
		Adventure = adv;
		System.out.println(Adventure);
		primaryStage.setScene(new Abacus(this, (int)((grade + 2) * 1.1), abacusSize).getScene());
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		primaryStage.setScene(new Intro(this).getScene());
		primaryStage.show();
		AbracadAbacus self = this;
	    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), ev -> {
	    	try {
				self.endIntro();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }));
	    timeline.play();
	}
	
}

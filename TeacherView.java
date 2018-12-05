package abracadabacus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class TeacherView {

	private int paneWidth = 1000;
	private int paneHeight;
	private int spaceBetweenRect;
	
	TeacherView(AbracadAbacus parent) {
		this.paneHeight = this.paneWidth * 7 / 10;
		this.spaceBetweenRect = this.paneWidth / 16;
		
		Pane pane = new Pane();
		pane.setPrefSize(this.paneWidth,this.paneHeight);
		TableView<StudentAttempt> table = new TableView<>();
		updateTable(table);

		parent.primaryStage.setScene(this.getScene(pane, table));
	    
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), ev -> {
	    		updateTable(table);
	    }));
	    timeline.setCycleCount(Animation.INDEFINITE);
	    timeline.play();
	}
	
	private String getData() throws IOException {
		String url = "https://abacus-e3165.firebaseio.com/results.json";

        URL oracle = new URL(url);
        BufferedReader in = new BufferedReader(
        new InputStreamReader(oracle.openStream()));

        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null)
        	response.append(inputLine);
        in.close();
//        System.out.println(response.toString());
       
		
		return response.toString();
	}
	
	public StudentAttempt getRow(Map<String, String> jsonRow) throws Exception {

		return	new StudentAttempt(jsonRow.get("abacusColumns"), 
				jsonRow.get("abacusSize"), jsonRow.get("grade"), 
				jsonRow.get("lesson"), jsonRow.get("name"), 
				jsonRow.get("result"));

	}
		
	private Scene getScene(Pane pane, TableView<StudentAttempt> table) {

		// build pane and scene
		Text textTop = new Text(0, 0, "Student Info");
		textTop.setFont(new Font(spaceBetweenRect));
		textTop.setFill(Color.BLUE);
		textTop.setLayoutY(spaceBetweenRect + 25);
		pane.getChildren().add(textTop);

		table.setLayoutY(spaceBetweenRect * 2 + 50);
		table.setPrefWidth(this.paneWidth);
        table.setEditable(false);
        
        TableColumn<StudentAttempt, String> nameCol = new TableColumn<>("Student Name");
        TableColumn<StudentAttempt, String> lessonCol = new TableColumn<>("Lesson");
        TableColumn<StudentAttempt, String> gradeCol = new TableColumn<>("Grade");
        TableColumn<StudentAttempt, String> resultCol = new TableColumn<>("Result");

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        lessonCol.setCellValueFactory(new PropertyValueFactory<>("lesson"));
        gradeCol.setCellValueFactory(new PropertyValueFactory<>("grade"));
        resultCol.setCellValueFactory(new PropertyValueFactory<>("result"));
        
        table.getColumns().add(nameCol);
        table.getColumns().add(lessonCol);
        table.getColumns().add(gradeCol);
        table.getColumns().add(resultCol);
       		
		pane.getChildren().add(table);

		return new Scene(pane);
	}
	
	@SuppressWarnings("unchecked")
	private void updateTable(TableView<StudentAttempt> table) {
		
		String studentsJSON = null;
		try {
			studentsJSON = this.getData();
		}
		catch (IOException e1) {}
		
		ObjectMapper mapper = new ObjectMapper();

		Map<String, Object> students = new HashMap<String, Object>();

		try {
			students = mapper.readValue(studentsJSON, new TypeReference<Map<String, Object>>(){});
			final ObservableList<StudentAttempt> data = FXCollections.observableArrayList();
			table.setItems(data);
			for (Entry<String, Object> student: students.entrySet()) {
				data.add(this.getRow((Map<String, String>) student.getValue()));
			}
		} 
		catch (JsonParseException e) {}
		catch (JsonMappingException e) {}
		catch (IOException e) {}
		catch (Exception e) {}
	}
}

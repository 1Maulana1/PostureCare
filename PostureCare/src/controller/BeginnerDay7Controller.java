package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.util.LinkedList;
import java.util.Queue;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;


public class BeginnerDay7Controller implements Initializable {
    private Queue<String> myQueue = new LinkedList<>();

    @FXML
    private Button start;

    @FXML
    private VBox previewExercise;

    private void displayQueue(){
        previewExercise.getChildren().clear();
        for(String data : myQueue){
            Label label = new Label(data);
            label.getStyleClass().add("label-queue"); // Gunakan CSS
            previewExercise.getChildren().add(label);
        }
    }

    @FXML
    private void startExercise(ActionEvent event) {
        try {
            //ganti sesuai fxml
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/beginnerday7exercise.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            javafx.stage.Stage stage = (javafx.stage.Stage) start.getScene().getWindow(); // Ambil stage dari tombol start
            stage.setScene(scene);
            stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        myQueue.add("1. The spinner");
        myQueue.add("2. Shoulder circles");
        myQueue.add("3. The hammer");
        myQueue.add("4. A hundred");
        myQueue.add("5. Reverse crunch");
        myQueue.add("6. Scissors");
        myQueue.add("7. Straight leg scissors");
        myQueue.add("8. Twists in the arc");

        displayQueue();
    }
}

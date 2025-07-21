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


public class AdvanceDay2Controller implements Initializable {
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
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/advanceday2exercise.fxml"));
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
        myQueue.add("1. Hold The Lotus");
        myQueue.add("2. Slopes In Lotus");
        myQueue.add("3. Wide Slopes");
        myQueue.add("4. Arms Behind The Head");
        myQueue.add("5. Alternating Knee To The Chest");
        myQueue.add("6. A Hundred");
        myQueue.add("7. Leg And Core Lift");
        myQueue.add("8. Leg Lift");
        myQueue.add("9. Side Forearm Plank");
        myQueue.add("10. Table");
        myQueue.add("11. Shortened Plate");
        

        displayQueue();
    }
}

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


public class AdvanceDay5Controller implements Initializable {
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
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/advanceday5exercise.fxml"));
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
        myQueue.add("1. The Clap");
        myQueue.add("2. The Spinner");
        myQueue.add("3. Deflection");
        myQueue.add("4. Head Nod");
        myQueue.add("5. Dead Bug");
        myQueue.add("6. Scissors");
        myQueue.add("7. Heel Slides With Knee To Chest");
        myQueue.add("8. Side Plank On The Palms");
        myQueue.add("9. Cat 1");
        myQueue.add("10. Cat 2");
        

        displayQueue();
    }
}

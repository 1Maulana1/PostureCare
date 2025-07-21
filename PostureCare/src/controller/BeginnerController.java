package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.PosturData;
import model.XmlDataWriter;
import util.AppData;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class BeginnerController implements Initializable {

    @FXML
    private BorderPane mainPane;

    @FXML
    private Button backButton;

    @FXML
    private void handleNotificationsClick() { System.out.println("Tombol Notifikasi diklik!"); }
    
    @FXML
    private void handleSettingsClick() { System.out.println("Tombol Settings diklik!"); }

    @FXML
    private void handleProfileClick() { System.out.println("Tombol Profil diklik!"); }

    @FXML
    private void handleBackToDashboard() {
        loadPage("/view/Dashboard.fxml");
    }

    private void loadPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void keDay1(ActionEvent event){
        OpenSceneLevel object = new OpenSceneLevel();
        Pane halaman = object.getPane("beginnerday1");
        mainPane.setLeft(halaman);
    }

    @FXML
    private void keDay2(ActionEvent event){
        OpenSceneLevel object = new OpenSceneLevel();
        Pane halaman = object.getPane("beginnerday2");
        mainPane.setLeft(halaman);
    }

    @FXML
    private void keDay3(ActionEvent event){
        OpenSceneLevel object = new OpenSceneLevel();
        Pane halaman = object.getPane("beginnerday3");
        mainPane.setLeft(halaman);
    }

    @FXML
    private void keDay4(ActionEvent event){
        OpenSceneLevel object = new OpenSceneLevel();
        Pane halaman = object.getPane("beginnerday4");
        mainPane.setLeft(halaman);
    }

    @FXML
    private void keDay5(ActionEvent event){
        OpenSceneLevel object = new OpenSceneLevel();
        Pane halaman = object.getPane("beginnerday5");
        mainPane.setLeft(halaman);
    }

    @FXML
    private void keDay6(ActionEvent event){
        OpenSceneLevel object = new OpenSceneLevel();
        Pane halaman = object.getPane("beginnerday6");
        mainPane.setLeft(halaman);
    }

    @FXML
    private void keDay7(ActionEvent event){
        OpenSceneLevel object = new OpenSceneLevel();
        Pane halaman = object.getPane("beginnerday7");
        mainPane.setLeft(halaman);
    }

    @FXML
    void handleExercise1Complete(ActionEvent event) {
        PosturData newData = new PosturData(LocalDate.now(), 20.0, 5.0, "Beginner");
        saveProgress(newData, "Cat-Cow Stretch");
    }

    private void saveProgress(PosturData data, String exerciseName) {
        AppData.progressHistory.add(data);
        XmlDataWriter.saveData(AppData.progressHistory, "data.xml");
        JOptionPane.showMessageDialog(null, "Progres '" + exerciseName + "' berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        OpenSceneLevel object = new OpenSceneLevel();
        Pane halaman = object.getPane("beginnerday1");
        mainPane.setLeft(halaman);
    }
}

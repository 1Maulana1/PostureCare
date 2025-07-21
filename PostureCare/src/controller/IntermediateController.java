package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class IntermediateController implements Initializable {

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
        Pane halaman = object.getPane("intermediateday1");
        mainPane.setLeft(halaman);
    }

    @FXML
    private void keDay2(ActionEvent event){
        OpenSceneLevel object = new OpenSceneLevel();
        Pane halaman = object.getPane("intermediateday2");
        mainPane.setLeft(halaman);
    }

    @FXML
    private void keDay3(ActionEvent event){
        OpenSceneLevel object = new OpenSceneLevel();
        Pane halaman = object.getPane("intermediateday3");
        mainPane.setLeft(halaman);
    }

    @FXML
    private void keDay4(ActionEvent event){
        OpenSceneLevel object = new OpenSceneLevel();
        Pane halaman = object.getPane("intermediateday4");
        mainPane.setLeft(halaman);
    }

    @FXML
    private void keDay5(ActionEvent event){
        OpenSceneLevel object = new OpenSceneLevel();
        Pane halaman = object.getPane("intermediateday5");
        mainPane.setLeft(halaman);
    }

    @FXML
    private void keDay6(ActionEvent event){
        OpenSceneLevel object = new OpenSceneLevel();
        Pane halaman = object.getPane("intermediateday6");
        mainPane.setLeft(halaman);
    }

    @FXML
    private void keDay7(ActionEvent event){
        OpenSceneLevel object = new OpenSceneLevel();
        Pane halaman = object.getPane("intermediateday7");
        mainPane.setLeft(halaman);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        OpenSceneLevel object = new OpenSceneLevel();
        Pane halaman = object.getPane("intermediateday1");
        mainPane.setLeft(halaman);
    }
}

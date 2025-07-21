package src.log;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import src.log.model.UserData;
import src.log.util.AppData;
import src.log.util.XmlManager;

public class Main extends Application {

    @Override
    public void init() throws Exception {
        super.init();
        UserData loadedData = XmlManager.loadData();
        if (loadedData != null && loadedData.getUsers() != null) {
            AppData.userList.addAll(loadedData.getUsers());
        }

        System.out.println("Data pengguna berhasil dimuat ke dalam ArrayList. Jumlah: " + AppData.userList.size());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/src/log/view/Welcome.fxml"));
            primaryStage.setTitle("PostureCare - Fix Your Posture");
            primaryStage.setScene(new Scene(root, 1280, 720)); 
            primaryStage.setMinWidth(1024);
            primaryStage.setMinHeight(768);
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Failed to load FXML: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
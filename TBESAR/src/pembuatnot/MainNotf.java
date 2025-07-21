package src.pembuatnot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainNotf extends Application {

    private NotfCont controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("notifikasi.fxml"));
        Scene scene = new Scene(loader.load());
        controller = loader.getController();

        primaryStage.setTitle("Pengingat Postur Tubuh");
        primaryStage.setScene(scene);
        primaryStage.show();

        controller.handleMuat(); // Muat data saat aplikasi dimulai
    }

    @Override
    public void stop() throws Exception {
        controller.handleSimpan(); // Simpan data saat aplikasi ditutup
    }

    public static void main(String[] args) {
        launch(args);
    }
}
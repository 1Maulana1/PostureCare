package src.cha.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // --- Ubah baris ini untuk memuat ProgressView.fxml secara langsung ---
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProgressView.fxml")); // Muat ProgressView
        Parent root = loader.load();

        primaryStage.setTitle("PosturCare - Progres Saya"); // Ganti judul ke judul halaman chart
        Scene scene = new Scene(root, 1000, 700); // Sesuaikan ukuran dengan ProgressView
        scene.getStylesheets().add(getClass().getResource("/assets/style.css").toExternalForm()); // Tambahkan CSS untuk chart
        // --- Akhir perubahan ---

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
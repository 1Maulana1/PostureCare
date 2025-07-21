package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.User;
import java.io.File;
import java.io.IOException;

public class ProfileViewController {

    @FXML private Button backButton;
    @FXML private ImageView profileImageView;
    @FXML private Label fullNameLabel;
    @FXML private Label usernameLabel;
    @FXML private Label bioLabel;
    @FXML private Label emailLabel;
    @FXML private Label ageLabel;

    private User currentUser;

    public void initData(User user) {
        this.currentUser = user;
        displayUserData();
    }

    private void displayUserData() {
        fullNameLabel.setText(currentUser.getFullName());
        usernameLabel.setText("@" + currentUser.getUsername());
        bioLabel.setText(currentUser.getBio());
        emailLabel.setText(currentUser.getEmail());
        ageLabel.setText(currentUser.getAge() > 0 ? currentUser.getAge() + " Tahun" : "Belum diatur");

        String imagePath = currentUser.getImagePath();
        if (imagePath != null && new File(imagePath).exists()) {
            profileImageView.setImage(new Image(new File(imagePath).toURI().toString()));
        } else {
            profileImageView.setImage(new Image(getClass().getResourceAsStream("/assets/user.png")));
        }
    }

    @FXML
    private void handleEditProfile() {
        loadPage("/view/ProfileEdit.fxml");
    }

    @FXML
    private void handleBackToDashboard() {
        loadPage("/view/Dashboard.fxml");
    }

    private void loadPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Kirim data pengguna ke controller halaman tujuan
            if (fxmlPath.contains("Edit")) {
                ProfileEditController controller = loader.getController();
                controller.initData(this.currentUser);
            } else if (fxmlPath.contains("Dashboard")) {
                DashboardController controller = loader.getController();
                controller.initData(this.currentUser);
            }
            
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
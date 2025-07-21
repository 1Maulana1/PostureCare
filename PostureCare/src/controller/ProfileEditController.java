package controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.User;
import model.UserData;
import util.AppData;
import util.XmlManager;
import javax.swing.JOptionPane;
import java.io.File;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class ProfileEditController {

    @FXML private ImageView profileImageView;
    @FXML private TextField fullNameField;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private TextField ageField;
    @FXML private TextArea bioField;
    @FXML private PasswordField passwordField;

    private User currentUser;
    private File newImageFile;

    public void initData(User user) {
        this.currentUser = user;
        populateFields();
    }

    private void populateFields() {
        fullNameField.setText(currentUser.getFullName());
        usernameField.setText(currentUser.getUsername());
        emailField.setText(currentUser.getEmail());
        ageField.setText(String.valueOf(currentUser.getAge()));
        bioField.setText(currentUser.getBio());
        String imagePath = currentUser.getImagePath();
        if (imagePath != null && new File(imagePath).exists()) {
            profileImageView.setImage(new Image(new File(imagePath).toURI().toString()));
        } else {
            profileImageView.setImage(new Image(getClass().getResourceAsStream("/assets/user.png")));
        }
    }

    @FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(profileImageView.getScene().getWindow());
        if (selectedFile != null) {
            this.newImageFile = selectedFile;
            profileImageView.setImage(new Image(selectedFile.toURI().toString()));
        }
    }

    @FXML
    private void handleSave() {
        try {
            currentUser.setFullName(fullNameField.getText());
            currentUser.setUsername(usernameField.getText());
            currentUser.setEmail(emailField.getText());
            currentUser.setAge(Integer.parseInt(ageField.getText()));
            currentUser.setBio(bioField.getText());

            String newPassword = passwordField.getText();
            if (newPassword != null && !newPassword.isEmpty()) {
                currentUser.setPassword(newPassword);
            }

            if (newImageFile != null) {
                // Untuk simple, kita simpan path absolutnya.
                // Anda bisa menambahkan logika copy file ke folder assets di sini.
                currentUser.setImagePath(newImageFile.getAbsolutePath());
            }

            UserData dataToSave = new UserData();
            dataToSave.setUsers(AppData.userList);
            XmlManager.saveData(dataToSave);

            JOptionPane.showMessageDialog(null, "Profil berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            handleCancel();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Umur harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void handleCancel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProfileView.fxml"));
            Parent root = loader.load();
            ProfileViewController controller = loader.getController();
            controller.initData(this.currentUser);
            Stage stage = (Stage) fullNameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
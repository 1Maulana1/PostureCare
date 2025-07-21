package controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.User;
import model.UserData;
import util.AppData;
import util.XmlManager;

public class RegisterController {

    @FXML private TextField emailField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;

    @FXML private TextField passwordTextField;
    @FXML private ToggleButton togglePasswordButton;
    @FXML private ImageView passwordEyeIcon;

    @FXML private TextField confirmPasswordTextField;
    @FXML private ToggleButton toggleConfirmPasswordButton;
    @FXML private ImageView confirmPasswordEyeIcon;

    private final Image eyeOpen = new Image(getClass().getResourceAsStream("/assets/eye-open.png"));
    private final Image eyeClosed = new Image(getClass().getResourceAsStream("/assets/eye-closed.png"));

    @FXML
    void handleRegister() {
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username tidak boleh kosong.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.toLowerCase().trim().endsWith("@gmail.com")) {
            JOptionPane.showMessageDialog(null, "Email harus menggunakan domain @gmail.com", "Email Tidak Valid", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (password.length() < 8 || !password.matches(".*[A-Z].*") || !password.matches(".*[!@#$%^&*()].*")) {
            JOptionPane.showMessageDialog(null, "Password tidak memenuhi syarat.", "Password Tidak Valid", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (User existingUser : AppData.userList) {
            if (existingUser.getEmail().equalsIgnoreCase(email)) {
                JOptionPane.showMessageDialog(null, "Email sudah terdaftar.", "Registrasi Gagal", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        User newUser = new User(email, password, username);

        AppData.userList.add(newUser);

        UserData dataToSave = new UserData();
        dataToSave.setUsers(AppData.userList);

        XmlManager.saveData(dataToSave);
        JOptionPane.showMessageDialog(null, "Registrasi berhasil! Silakan login.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        handleBackToLogin();
    }

    @FXML
    void handleBackToLogin() {
        try {
            Stage stage = (Stage) emailField.getScene().getWindow();
            
            Parent loginView = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            stage.setScene(new Scene(loginView));
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupPasswordToggle(PasswordField pField, TextField tField, ToggleButton toggleButton, ImageView eyeIcon) {
        tField.textProperty().bindBidirectional(pField.textProperty());

        tField.visibleProperty().bind(toggleButton.selectedProperty());
        pField.visibleProperty().bind(toggleButton.selectedProperty().not());
        tField.managedProperty().bind(toggleButton.selectedProperty());
        pField.managedProperty().bind(toggleButton.selectedProperty().not());

        toggleButton.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                eyeIcon.setImage(eyeOpen);
            } else {
                eyeIcon.setImage(eyeClosed);
            }
        });
    }

    @FXML
    public void initialize() {
        setupPasswordToggle(passwordField, passwordTextField, togglePasswordButton, passwordEyeIcon);
        setupPasswordToggle(confirmPasswordField, confirmPasswordTextField, toggleConfirmPasswordButton, confirmPasswordEyeIcon);
    }
}
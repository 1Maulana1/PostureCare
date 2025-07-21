package src.log.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import src.log.model.User;
import src.log.model.UserData;
import src.log.util.AppData;
import src.log.util.XmlManager;
import javax.swing.JOptionPane;

public class ResetPasswordController {

    @FXML private Label emailLabel;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;

    private String userEmail;

    public void initData(String email) {
        userEmail = email;
        emailLabel.setText("Resetting password for: " + userEmail);
    }

    @FXML
    void handleResetPassword() {
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (newPassword.isEmpty() || !newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Password baru tidak cocok atau kosong.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (newPassword.length() < 8 || !newPassword.matches(".*[A-Z].*") || !newPassword.matches(".*[!@#$%^&*()].*")) {
            JOptionPane.showMessageDialog(null, "Password baru tidak memenuhi syarat.", "Password Tidak Valid", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean userFound = false;

        for (User user : AppData.userList) {
            if (user.getEmail().equals(userEmail)) {
                user.setPassword(newPassword);
                userFound = true;
                break;
            }
        }

        if (userFound) {
            UserData dataToSave = new UserData();
            dataToSave.setUsers(AppData.userList);

            XmlManager.saveData(dataToSave);

            JOptionPane.showMessageDialog(null, "Password berhasil diubah!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            
            try {
                Stage stage = (Stage) emailLabel.getScene().getWindow();
                Parent loginView = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
                stage.setScene(new Scene(loginView));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menemukan pengguna untuk mengubah password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
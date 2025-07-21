package controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import javax.swing.JOptionPane;

public class ForgotPasswordController {

    @FXML private TextField emailField;
    
    @FXML
    void handleResetPassword() {
        String email = emailField.getText();

        boolean userExists = false;
        for (User user : util.AppData.userList) {
            if (user.getEmail().equals(email)) {
                userExists = true;
                break;
            }
        }

        if (userExists) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ResetPassword.fxml"));
                Parent root = loader.load();

                ResetPasswordController controller = loader.getController();
                controller.initData(email);

                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.setScene(new Scene(root));
                
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            JOptionPane.showMessageDialog(null, "If an account with " + email + " exists, you will be redirected.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    @FXML
    void handleBackToLogin() {
        try {
            Parent loginView = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(loginView));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
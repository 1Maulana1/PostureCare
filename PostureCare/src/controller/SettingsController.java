package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.User;
import model.UserData;
import util.AppData;
import util.XmlManager;

import javax.swing.JOptionPane;
import java.io.IOException;

public class SettingsController {

    @FXML
    private Button backButton;
    private User currentUser;

    // Method untuk menerima data user dari Dashboard
    public void initData(User user) {
        this.currentUser = user;
    }

    @FXML
    private void handleLogOut(ActionEvent event) {
        int response = JOptionPane.showConfirmDialog(
            null, 
            "Apakah Anda yakin ingin keluar?", 
            "Konfirmasi Log Out", 
            JOptionPane.YES_NO_OPTION
        );

        if (response == JOptionPane.YES_OPTION) {
            System.out.println("Pengguna log out...");
            navigateTo("/view/Login.fxml");
        }
    }

    @FXML
    private void handleDeleteAccount(ActionEvent event) {
        int response = JOptionPane.showConfirmDialog(
            null, 
            "PERINGATAN: Aksi ini tidak dapat dibatalkan.\nSemua data Anda akan dihapus secara permanen.\n\nApakah Anda yakin ingin menghapus akun ini?", 
            "Konfirmasi Hapus Akun", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE
        );

        if (response == JOptionPane.YES_OPTION) {
            System.out.println("Menghapus akun: " + currentUser.getEmail());
            
            // 1. Hapus pengguna dari ArrayList di memori
            AppData.userList.remove(this.currentUser);

            // 2. Siapkan data baru untuk disimpan ke XML
            UserData dataToSave = new UserData();
            dataToSave.setUsers(AppData.userList);

            // 3. Simpan seluruh list yang sudah di-update ke file XML
            XmlManager.saveData(dataToSave);

            JOptionPane.showMessageDialog(null, "Akun Anda telah berhasil dihapus.", "Akun Dihapus", JOptionPane.INFORMATION_MESSAGE);
            
            // 4. Arahkan kembali ke halaman login
            navigateTo("/view/Login.fxml");
        }
    }

    @FXML
    private void handleBackToDashboard(ActionEvent event) {
        navigateTo("/view/Dashboard.fxml");
    }

    private void navigateTo(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Jika kembali ke dashboard, kirim kembali data pengguna
            if (fxmlPath.contains("Dashboard")) {
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
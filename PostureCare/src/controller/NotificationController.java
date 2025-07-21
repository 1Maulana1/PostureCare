package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Notification;
import model.User;
import util.NotificationManager;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NotificationController implements Initializable {

    @FXML private Button backButton;
    @FXML private ListView<Notification> notificationListView;
    @FXML private TextField messageField;
    @FXML private TextField intervalField;
    @FXML private ComboBox<String> unitComboBox;
    @FXML private ToggleButton startStopButton;
    @FXML private Label statusLabel;

    private User currentUser;
    private ObservableList<Notification> notificationList;
    private Timeline timeline;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inisialisasi daftar notifikasi dari file
        notificationList = FXCollections.observableArrayList(NotificationManager.loadNotifications());
        notificationListView.setItems(notificationList);

        // Setup ComboBox
        unitComboBox.getItems().addAll("Menit", "Jam");
        unitComboBox.setValue("Menit");

        // Listener untuk menampilkan detail saat item dipilih
        notificationListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                messageField.setText(newVal.getMessage());
                intervalField.setText(String.valueOf(newVal.getInterval()));
                unitComboBox.setValue(newVal.getUnit());
            }
        });
    }

    public void initData(User user) {
        this.currentUser = user;
    }

    @FXML
    private void handleAdd() {
        try {
            String message = messageField.getText();
            int interval = Integer.parseInt(intervalField.getText());
            String unit = unitComboBox.getValue();
            if (message.isEmpty() || interval <= 0) {
                showAlert("Input tidak valid.");
                return;
            }
            notificationList.add(new Notification(message, interval, unit));
            NotificationManager.saveNotifications(notificationList);
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Interval harus berupa angka.");
        }
    }

    @FXML
    private void handleUpdate() {
        Notification selected = notificationListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Pilih item yang ingin diubah.");
            return;
        }
        try {
            selected.setMessage(messageField.getText());
            selected.setInterval(Integer.parseInt(intervalField.getText()));
            selected.setUnit(unitComboBox.getValue());
            notificationListView.refresh();
            NotificationManager.saveNotifications(notificationList);
            showAlert("Pengingat berhasil diperbarui.");
        } catch (NumberFormatException e) {
            showAlert("Interval harus berupa angka.");
        }
    }

    @FXML
    private void handleDelete() {
        Notification selected = notificationListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Pilih item yang ingin dihapus.");
            return;
        }
        notificationList.remove(selected);
        NotificationManager.saveNotifications(notificationList);
        clearFields();
    }

    @FXML
    private void handleStartStop() {
        if (startStopButton.isSelected()) {
            if (notificationList.isEmpty()) {
                showAlert("Tidak ada pengingat untuk dimulai.");
                startStopButton.setSelected(false);
                return;
            }
            startStopButton.setText("Hentikan Pengingat");
            startTimer();
        } else {
            startStopButton.setText("Mulai Pengingat");
            stopTimer();
        }
    }

    private void startTimer() {
        if (timeline != null) timeline.stop();
        
        timeline = new Timeline(new KeyFrame(Duration.minutes(1), e -> showNotification()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        statusLabel.setText("Status: Aktif. Menunggu pengingat berikutnya...");
    }
    
    private void stopTimer(){
        if(timeline != null) timeline.stop();
        statusLabel.setText("Status: Tidak Aktif");
    }

    private void showNotification() {
        // Logika untuk menampilkan notifikasi berdasarkan interval waktu
        // Ini adalah versi sederhana, bisa dikembangkan lebih lanjut
        if (!notificationList.isEmpty()) {
            Notification firstNotif = notificationList.get(0);
            JOptionPane.showMessageDialog(null, firstNotif.getMessage(), "Pengingat PostureCare", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void clearFields() {
        messageField.clear();
        intervalField.clear();
        notificationListView.getSelectionModel().clearSelection();
    }
    
    private void showAlert(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    @FXML
    private void handleBackToDashboard(ActionEvent event) {
        stopTimer(); // Hentikan timer sebelum pindah halaman
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Dashboard.fxml"));
            Parent root = loader.load();
            DashboardController controller = loader.getController();
            controller.initData(this.currentUser);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) { e.printStackTrace(); }
    }
}
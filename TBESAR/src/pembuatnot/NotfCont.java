package src.pembuatnot;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import java.io.*;

public class NotfCont {
    @FXML private ListView<String> notifikasiListView;
    @FXML private TextField inputField;
    @FXML private TextField intervalField;
    @FXML private ComboBox<String> intervalTypeBox;
    @FXML private Label statusLabel;

    private final List<NotifikasiItem> daftarNotifikasi = new ArrayList<>();
    private Timeline timeline;
    private int currentIndex = 0;
    private final String FILE_PATH = "notifikasi_xstream.xml";
    private static final XStream xstream = new XStream();

    private long countdownMillis = 0;

    static {
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.alias("notifikasi", NotifikasiItem.class);
        xstream.alias("notifikasiList", List.class);
    }

    @FXML
    public void initialize() {
        intervalTypeBox.getItems().addAll("Menit", "Jam");
        intervalTypeBox.setValue("Menit");
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        notifikasiListView.getItems().clear();
    }

    @FXML
    private void handleTambah() {
        String pesan = inputField.getText().trim();
        String tipe = intervalTypeBox.getValue();
        String intervalStr = intervalField.getText().trim();
        if (pesan.isEmpty() || intervalStr.isEmpty()) {
            showAlert("Peringatan", "Pesan dan interval harus diisi!");
            return;
        }
        int interval;
        try {
            interval = Integer.parseInt(intervalStr);
        } catch (NumberFormatException ex) {
            showAlert("Peringatan", "Interval harus berupa angka!");
            return;
        }
        NotifikasiItem item = new NotifikasiItem(pesan, interval, tipe, "");
        daftarNotifikasi.add(item);
        notifikasiListView.getItems().add(item.toString());
        inputField.clear();
        intervalField.clear();
    }

    @FXML
    private void handlePilih() {
        int selectedIndex = notifikasiListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            showAlert("Peringatan", "Pilih notifikasi yang ingin diubah terlebih dahulu!");
            return;
        }
        NotifikasiItem item = daftarNotifikasi.get(selectedIndex);
        inputField.setText(item.pesan);
        intervalField.setText(String.valueOf(item.interval));
        intervalTypeBox.setValue(item.tipe);
    }

    @FXML
    private void handleTerapkan() {
        int selectedIndex = notifikasiListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            showAlert("Peringatan", "Pilih notifikasi yang ingin diubah terlebih dahulu!");
            return;
        }
        String pesanBaru = inputField.getText().trim();
        String intervalStr = intervalField.getText().trim();
        if (pesanBaru.isEmpty() || intervalStr.isEmpty()) {
            showAlert("Peringatan", "Pesan dan interval harus diisi!");
            return;
        }
        int intervalBaru;
        try {
            intervalBaru = Integer.parseInt(intervalStr);
        } catch (NumberFormatException ex) {
            showAlert("Peringatan", "Interval harus berupa angka!");
            return;
        }
        String tipeBaru = intervalTypeBox.getValue();
        NotifikasiItem itemBaru = new NotifikasiItem(pesanBaru, intervalBaru, tipeBaru, "");
        daftarNotifikasi.set(selectedIndex, itemBaru);
        notifikasiListView.getItems().set(selectedIndex, itemBaru.toString());
        inputField.clear();
        intervalField.clear();
        intervalTypeBox.setValue("Menit");
        showAlert("Informasi", "Notifikasi berhasil diperbarui!");
    }

    @FXML
    private void handleHapus() {
        int selectedIndex = notifikasiListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            daftarNotifikasi.remove(selectedIndex);
            notifikasiListView.getItems().remove(selectedIndex);
        }
    }

    @FXML
    private void handleMulaiPengingat() {
        if (daftarNotifikasi.isEmpty()) {
            statusLabel.setText("Status: Tidak ada notifikasi.");
            return;
        }
        timeline.stop();
        timeline.getKeyFrames().clear();
        currentIndex = 0;
        NotifikasiItem item = daftarNotifikasi.get(currentIndex);
        Duration durasi = getDuration(item);
        countdownMillis = (long) durasi.toMillis();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), this::handleCountdownTick));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        updateStatusLabel();
    }

    private void handleCountdownTick(ActionEvent e) {
        if (daftarNotifikasi.isEmpty()) return;
        countdownMillis -= 1000;
        if (countdownMillis <= 0) {
            NotifikasiItem item = daftarNotifikasi.get(currentIndex);
            showNotification(item.pesan + " (Interval: " + item.interval + " " + item.tipe + ")");
            currentIndex = (currentIndex + 1) % daftarNotifikasi.size();
            Duration durasi = getDuration(daftarNotifikasi.get(currentIndex));
            countdownMillis = (long) durasi.toMillis();
        }
        updateStatusLabel();
    }

    private void updateStatusLabel() {
        long totalSeconds = countdownMillis / 1000;
        long menit = totalSeconds / 60;
        long detik = totalSeconds % 60;
        String sisa = String.format("Notifikasi berikutnya dalam %02d menit %02d detik", menit, detik);
        statusLabel.setText("Status: Pengingat aktif (" + sisa + ")");
    }

    private Duration getDuration(NotifikasiItem item) {
        if (item == null || item.tipe == null) return Duration.minutes(1);
        if ("Jam".equals(item.tipe)) {
            return Duration.hours(item.interval);
        } else {
            return Duration.minutes(item.interval);
        }
    }

    private void showNotification(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pengingat");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
        showSystemTrayNotification("Pengingat", message);
    }

    private void showSystemTrayNotification(String title, String message) {
        if (!SystemTray.isSupported()) return;
        try {
            SystemTray tray = SystemTray.getSystemTray();
            BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            TrayIcon trayIcon = new TrayIcon(image, "Notifikasi");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("Notifikasi");
            tray.add(trayIcon);
            trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
            Thread remover = new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    tray.remove(trayIcon);
                } catch (Exception ignored) {}
            });
            remover.setDaemon(true);
            remover.start();
        } catch (Exception e) {
            System.err.println("Tray error: " + e.getMessage());
        }
    }

    @FXML
    public void handleSimpan() {
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
            xstream.toXML(daftarNotifikasi, fos);
            showAlert("Sukses", "Data notifikasi berhasil disimpan!");
        } catch (IOException e) {
            showAlert("Error", "Gagal menyimpan data: " + e.getMessage());
        }
    }

    @FXML
    public void handleMuat() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            showAlert("Info", "Belum ada data notifikasi yang disimpan.");
            return;
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            List<NotifikasiItem> loaded = (List<NotifikasiItem>) xstream.fromXML(fis);
            daftarNotifikasi.clear();
            daftarNotifikasi.addAll(loaded);
            notifikasiListView.getItems().clear();
            for (NotifikasiItem item : daftarNotifikasi) {
                notifikasiListView.getItems().add(item.toString());
            }
            showAlert("Sukses", "Data notifikasi berhasil dimuat!");
        } catch (Exception e) {
            showAlert("Error", "Gagal memuat data: " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }
}
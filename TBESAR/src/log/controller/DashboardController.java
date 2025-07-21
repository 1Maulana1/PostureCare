package src.log.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class DashboardController {

    @FXML
    private Label greetingLabel;
    @FXML
    private ListView<String> historyListView;
    @FXML
    private LineChart<String, Number> progressChart;

    @FXML
    public void initialize() {
        // Atur teks sapaan (nantinya nama diambil dari data user yang login)
        greetingLabel.setText("Halo, Pengguna!");

        // Isi data contoh ke dalam daftar History Exercise
        ObservableList<String> historyItems = FXCollections.observableArrayList(
            "Cat-Cow Stretch - 15 Jul",
            "Plank 30s - 15 Jul",
            "Child's Pose - 14 Jul",
            "Neck Stretch - 14 Jul"
        );
        historyListView.setItems(historyItems);

        // Isi data contoh ke dalam Chart Progress
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Latihan Selesai");
        series.getData().add(new XYChart.Data<>("Sen", 5));
        series.getData().add(new XYChart.Data<>("Sel", 7));
        series.getData().add(new XYChart.Data<>("Rab", 6));
        series.getData().add(new XYChart.Data<>("Kam", 8));
        series.getData().add(new XYChart.Data<>("Jum", 9));
        
        progressChart.getData().add(series);
    }

    @FXML
    private void handleNotificationsClick() { System.out.println("Tombol Notifikasi diklik!"); }
    
    @FXML
    private void handleSettingsClick() { System.out.println("Tombol Settings diklik!"); }

    @FXML
    private void handleProfileClick() { System.out.println("Tombol Profil diklik!"); }
    
    @FXML
    private void handleArticleClick(MouseEvent event) { System.out.println("Panel Artikel diklik!"); }

    @FXML
    private void handleBeginnerClick(MouseEvent event) { System.out.println("Panel Beginner diklik!"); }

    @FXML
    private void handleIntermediateClick(MouseEvent event) { System.out.println("Panel Intermediate diklik!"); }

    @FXML
    private void handleAdvancedClick(MouseEvent event) { System.out.println("Panel Advanced diklik!"); }
}
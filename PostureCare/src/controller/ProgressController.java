package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.PosturData;
import model.User;
import util.AppData;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ProgressController implements Initializable {

    // FXML Fields
    @FXML private Button backButton;
    @FXML private ComboBox<String> levelFilterComboBox;
    @FXML private Label currentLevelLabel;
    @FXML private Label totalCaloriesLabel;
    @FXML private Label totalDurationLabel;
    @FXML private Label totalExercisesLabel;
    @FXML private LineChart<String, Number> dailyProgressLineChart;
    @FXML private CategoryAxis dailyXAxis;
    @FXML private NumberAxis dailyYAxis;
    @FXML private HBox chartOptionsBox;

    private User currentUser;
    private String currentChartMetric = "Kalori Terbakar";
    
    // Method untuk menerima data user dari Dashboard
    public void initData(User user) {
        this.currentUser = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        levelFilterComboBox.getItems().addAll("Total Latihan", "Beginner", "Intermediate", "Advanced");
        levelFilterComboBox.getSelectionModel().select("Total Latihan");
        levelFilterComboBox.setOnAction(event -> updateDisplays());
        addChartOptionButtons();
        updateDisplays();
    }

    private void addChartOptionButtons() {
        ToggleGroup group = new ToggleGroup();
        String[] metrics = {"Kalori Terbakar", "Durasi Latihan"};
        for (String metric : metrics) {
            ToggleButton btn = new ToggleButton(metric);
            btn.setToggleGroup(group);
            btn.setOnAction(event -> {
                currentChartMetric = metric;
                updateDisplays();
            });
            chartOptionsBox.getChildren().add(btn);
        }
        if (!chartOptionsBox.getChildren().isEmpty()) {
            ((ToggleButton) chartOptionsBox.getChildren().get(0)).setSelected(true);
        }
    }

    private void updateDisplays() {
        updateSummary(AppData.progressHistory); 
        String selectedFilter = levelFilterComboBox.getValue();
        List<PosturData> filteredData = filterDataByLevel(selectedFilter);
        updateDailyChart(filteredData);
    }

    private List<PosturData> filterDataByLevel(String level) {
        if ("Total Latihan".equals(level) || level == null) return AppData.progressHistory;
        return AppData.progressHistory.stream()
                .filter(d -> d.getLevel().equalsIgnoreCase(level))
                .collect(Collectors.toList());
    }

    private void updateSummary(List<PosturData> allData) {
        double totalCalories = allData.stream().mapToDouble(PosturData::getCaloriesBurned).sum();
        double totalDuration = allData.stream().mapToDouble(PosturData::getDurationMinutes).sum();
        totalCaloriesLabel.setText(String.format("%.0f Kcal", totalCalories));
        totalDurationLabel.setText(String.format("%.0f Menit", totalDuration));
        totalExercisesLabel.setText(String.format("%d Hari", allData.size()));
        if (!allData.isEmpty()) {
            currentLevelLabel.setText(allData.get(allData.size() - 1).getLevel());
        } else {
            currentLevelLabel.setText("N/A");
        }
    }

    private void updateDailyChart(List<PosturData> data) {
        dailyProgressLineChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM");
        List<PosturData> chartData = data.size() > 7 ? data.subList(data.size() - 7, data.size()) : data;
        for (PosturData entry : chartData) {
            String dateLabel = entry.getDate().format(formatter);
            Number value = "Kalori Terbakar".equals(currentChartMetric) ? entry.getCaloriesBurned() : entry.getDurationMinutes();
            series.getData().add(new XYChart.Data<>(dateLabel, value));
        }
        dailyProgressLineChart.getData().add(series);
        dailyXAxis.setLabel("Tanggal");
        dailyYAxis.setLabel(currentChartMetric);
    }

    @FXML
    private void handleBackToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Dashboard.fxml"));
            Parent dashboardRoot = loader.load();
            DashboardController controller = loader.getController();
            controller.initData(this.currentUser);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(dashboardRoot));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
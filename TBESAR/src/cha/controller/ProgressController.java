package src.cha.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox; 

import src.cha.model.PosturData;
// import model.XmlDataReader; // TIDAK DIGUNAKAN JIKA DATA DIKOSONGKAN

import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.Comparator;

public class ProgressController implements Initializable {

    @FXML private Label pageTitleLabel;
    @FXML private ComboBox<String> levelFilterComboBox;
    @FXML private Label currentLevelLabel;
    @FXML private Label totalCaloriesLabel;
    @FXML private Label totalDurationLabel;
    @FXML private Label targetAchievementLabel; 
    @FXML private Label totalExercisesLabel;

    @FXML private LineChart<String, Number> dailyProgressLineChart; 
    @FXML private CategoryAxis dailyXAxis;
    @FXML private NumberAxis dailyYAxis;

    @FXML private HBox chartOptionsBox; 
    @FXML private VBox chartContainer; 

    // Menggunakan Deque untuk menyimpan semua data postur. Akan kosong secara default.
    private Deque<PosturData> allPosturData = new ArrayDeque<>(); 
    
    private String currentChartMetric = "Kalori Terbakar";
    private String achievedLevel = "Beginner";
    private int daysInAchievedLevel = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pageTitleLabel.setText("Progres Postur Tubuh Saya");

        // --- KOSONGKAN DATA AWAL: Komentari atau hapus baris pemuatan XML ini ---
        // String xmlFilePath = "data.xml"; 
        // List<PosturData> loadedData = XmlDataReader.readPosturDataFromXml(xmlFilePath);
        // loadedData.sort(Comparator.comparing(PosturData::getDate));
        // loadedData.forEach(allPosturData::addLast);
        // --- AKHIR KOSONGKAN DATA AWAL ---

        levelFilterComboBox.getItems().addAll("Beginner", "Intermediate", "Advanced", "Total Latihan");
        levelFilterComboBox.setPromptText("Pilih Tingkat Kesulitan"); 
        levelFilterComboBox.getSelectionModel().select("Beginner"); 

        levelFilterComboBox.setOnAction(event -> {
            updateAllDisplays();
        });

        addChartOptionButtons(); 

        dailyXAxis.setLabel("Hari Latihan"); 
        
        updateAllDisplays();
    }

    private void addChartOptionButtons() {
        String[] metrics = {"Kalori Terbakar", "Durasi Latihan"}; 
        chartOptionsBox.getChildren().clear();
        for (String metric : metrics) {
            Button btn = new Button(metric);
            btn.setOnAction(event -> {
                currentChartMetric = metric; 
                updateDailyChart(levelFilterComboBox.getSelectionModel().getSelectedItem(), currentChartMetric);
            });
            chartOptionsBox.getChildren().add(btn);
        }
    }

    private void updateAllDisplays() {
        String selectedFilterLevel = levelFilterComboBox.getSelectionModel().getSelectedItem();
        
        List<PosturData> last7DaysOfAllData = new ArrayList<>(allPosturData);
        if (last7DaysOfAllData.size() > 7) {
            last7DaysOfAllData = last7DaysOfAllData.subList(last7DaysOfAllData.size() - 7, last7DaysOfAllData.size());
        }
        updateAchievedLevel(last7DaysOfAllData);
        
        updateSummary(selectedFilterLevel); 
        
        updateDailyChart(selectedFilterLevel, currentChartMetric);
    }

    private void updateDailyChart(String selectedFilterLevel, String metric) { 
        dailyProgressLineChart.getData().clear();

        dailyYAxis.setForceZeroInRange(false);
        if ("Kalori Terbakar".equals(metric)) {
            dailyYAxis.setLowerBound(50);
            dailyYAxis.setUpperBound(500);
            dailyYAxis.setTickUnit(50);
        } else if ("Durasi Latihan".equals(metric)) {
            dailyYAxis.setLowerBound(10);
            dailyYAxis.setUpperBound(60);
            dailyYAxis.setTickUnit(5);
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(metric);

        List<PosturData> dataForChart = new ArrayList<>();

        if ("Total Latihan".equals(selectedFilterLevel)) {
            if (allPosturData.size() > 7) {
                dataForChart = new ArrayList<>(allPosturData).subList(allPosturData.size() - 7, allPosturData.size());
            } else {
                dataForChart.addAll(allPosturData);
            }
        } else {
            List<PosturData> filteredByLevel = new ArrayList<>();
            for (PosturData entry : allPosturData) {
                if (entry.getLevel().equals(selectedFilterLevel)) {
                    filteredByLevel.add(entry);
                }
            }
            if (filteredByLevel.size() > 7) {
                dataForChart = filteredByLevel.subList(filteredByLevel.size() - 7, filteredByLevel.size());
            } else {
                dataForChart.addAll(filteredByLevel);
            }
        }

        if (!dataForChart.isEmpty()) {
            int dayCounter = 1; 
            for (PosturData entry : dataForChart) {
                String category = "Hari " + dayCounter;
                
                if ("Kalori Terbakar".equals(metric)) {
                    series.getData().add(new XYChart.Data<>(category, entry.getCaloriesBurned()));
                } else if ("Durasi Latihan".equals(metric)) {
                    series.getData().add(new XYChart.Data<>(category, entry.getDurationMinutes()));
                }
                dayCounter++; 
            }
        }

        dailyProgressLineChart.getData().add(series);
        dailyYAxis.setLabel(getAxisLabel(metric));
        dailyProgressLineChart.setTitle("Progres Harian " + metric + " untuk " + (selectedFilterLevel.equals("Total Latihan") ? "Semua Level" : selectedFilterLevel));
    }

    private void updateSummary(String selectedFilterLevel) {
        List<PosturData> dataToSummarize = new ArrayList<>();

        if ("Total Latihan".equals(selectedFilterLevel)) {
            dataToSummarize.addAll(allPosturData);
            totalExercisesLabel.setText(String.format("Total Latihan: %d Hari", allPosturData.size()));
        } else {
            for (PosturData entry : allPosturData) {
                if (entry.getLevel().equals(selectedFilterLevel)) {
                    dataToSummarize.add(entry);
                }
            }
            totalExercisesLabel.setText(String.format("Total Latihan: %d Hari", dataToSummarize.size()));
        }
        
        double totalCalories = dataToSummarize.stream().mapToDouble(PosturData::getCaloriesBurned).sum();
        double totalDuration = dataToSummarize.stream().mapToDouble(PosturData::getDurationMinutes).sum();

        totalCaloriesLabel.setText(String.format("Total Kalori Terbakar: %.0f Kcal", totalCalories));
        totalDurationLabel.setText(String.format("Total Durasi Latihan: %.0f Menit", totalDuration));
        
        // --- UBAH INI: Atur "Pencapaian Target" berdasarkan apakah ada data atau tidak ---
        if (dataToSummarize.isEmpty()) {
             targetAchievementLabel.setText("Pencapaian Target: 0/0"); 
        } else {
            // Placeholder: Anda bisa menambahkan logika dinamis di sini nanti
            // Untuk saat ini, kita akan membuatnya 0/0 jika dataToSummarize kosong,
            // dan tetap 3/5 jika ada data (walaupun masih statis).
            targetAchievementLabel.setText("Pencapaian Target: 3/5"); // Tetap 3/5 jika ada data (placeholder)
        }
        // --- AKHIR UBAH INI ---

        // Kondisi jika keseluruhan data kosong (tidak hanya yang difilter)
        if (allPosturData.isEmpty()) { // Menggunakan allPosturData.isEmpty() untuk kondisi awal
             totalCaloriesLabel.setText("Total Kalori Terbakar: 0 Kcal");
             totalDurationLabel.setText("Total Durasi Latihan: 0 Menit");
             totalExercisesLabel.setText("Total Latihan: 0 Hari");
             targetAchievementLabel.setText("Pencapaian Target: 0/0"); // Pastikan 0/0 saat aplikasi baru dimulai
        }
    }

    private void updateAchievedLevel(List<PosturData> last7DaysData) {
        if (allPosturData.isEmpty()) { // Cek allPosturData, bukan hanya last7DaysData
             achievedLevel = "Beginner";
             daysInAchievedLevel = 0; 
             currentLevelLabel.setText("Tingkat Kesulitan Anda: " + achievedLevel + " (Hari " + daysInAchievedLevel + ")");
             return;
        }

        double caloriesForLast7Days = last7DaysData.stream().mapToDouble(PosturData::getCaloriesBurned).sum();
        double durationForLast7Days = last7DaysData.stream().mapToDouble(PosturData::getDurationMinutes).sum();
        
        String calculatedLevel;
        if (caloriesForLast7Days < 1500 && durationForLast7Days < 200) { 
            calculatedLevel = "Beginner";
        } else if (caloriesForLast7Days < 3500 && durationForLast7Days < 500) { 
            calculatedLevel = "Intermediate";
        } else { 
            calculatedLevel = "Advanced";
        }

        if (!calculatedLevel.equals(achievedLevel)) {
            achievedLevel = calculatedLevel;
            daysInAchievedLevel = 1;
        } else {
            int countDaysInLevel = 0;
            for (PosturData data : last7DaysData) {
                if (data.getLevel().equals(achievedLevel)) {
                    countDaysInLevel++;
                }
            }
            daysInAchievedLevel = countDaysInLevel;
        }
        
        currentLevelLabel.setText("Tingkat Kesulitan Anda: " + achievedLevel + " (Hari " + daysInAchievedLevel + ")");
    }

    private String getAxisLabel(String metric) {
        switch (metric) {
            case "Kalori Terbakar": return "Kalori (Kcal)";
            case "Durasi Latihan": return "Durasi (Menit)";
            default: return "Nilai";
        }
    }
}
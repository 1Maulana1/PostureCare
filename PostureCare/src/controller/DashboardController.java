package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import com.thoughtworks.xstream.XStream;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.PosturData;
import model.User;
import util.AppData;

public class DashboardController implements Initializable {
    @FXML private BorderPane mainBorderPane;
    @FXML private Label greetingLabel;
    @FXML private ListView<PosturData> historyListView;
    @FXML private LineChart<String, Number> progressChart;

    @FXML private ImageView artikel1Image;
    @FXML private ImageView artikel2Image;
    @FXML private ImageView artikel3Image;
    @FXML private ImageView artikel4Image;
    @FXML private ImageView artikel5Image;
    @FXML private ImageView artikel6Image;

    @FXML
    private void handleArtikelClick(MouseEvent event){
        if(event.getSource() == artikel1Image){
            openLink("https://www.google.com/url?sa=t&source=web&rct=j&opi=89978449&url=https://www.rsatmajaya.co.id/postur-duduk-yang-baik-yang-kurang-baik-dalam-beraktivitas/&ved=2ahUKEwis79Pbs6WOAxV5oWMGHbgvHHUQFnoECB0QAQ&usg=AOvVaw0lzk0ZSodiiC_xfcZ_VP1m");
        }else if(event.getSource() == artikel2Image){
            openLink("https://www.google.com/url?sa=t&source=web&rct=j&opi=89978449&url=https://www.kavacare.id/postur-tubuh-cara-memperbaiki-dengan-berolahraga/&ved=2ahUKEwis79Pbs6WOAxV5oWMGHbgvHHUQFnoECCsQAQ&usg=AOvVaw1L1BQjH2GHAQCtSVmneQ8W");
        }else if(event.getSource() == artikel3Image){
            openLink("https://www.google.com/url?sa=t&source=web&rct=j&opi=89978449&url=https://puskesmassuranadi-dikes.lombokbaratkab.go.id/artikel/pengaruh-posisi-duduk-yang-baik-terhadap-kesehatan-tubuh/&ved=2ahUKEwis79Pbs6WOAxV5oWMGHbgvHHUQFnoECDIQAQ&usg=AOvVaw04sMLFi-wHfPWI2iVPQO2E");
        }else if(event.getSource() == artikel4Image){
            openLink("https://www.google.com/url?sa=t&source=web&rct=j&opi=89978449&url=https://curble.co.id/postur-tubuh-penting-untuk-menjaga-kesehatan/&ved=2ahUKEwi1y6PTtaWOAxUHxjgGHf6UMoEQFnoECB4QAQ&usg=AOvVaw0IQ48N7aCleKRYaK29NBNX");
        }else if(event.getSource() == artikel5Image){
            openLink("https://www.google.com/url?sa=t&source=web&rct=j&opi=89978449&url=http://ners.unair.ac.id/site/index.php/news-fkp-unair/30-lihat/1337-menjaga-postur-tubuh-anda-dengan-tips-berikut&ved=2ahUKEwii2qePtqWOAxUZwjgGHTjbIa4QFnoECBwQAQ&usg=AOvVaw1xdUnidNE4VKK7bF3nJXVN");
        }else if(event.getSource() == artikel6Image){
            openLink("https://www.google.com/url?sa=t&source=web&rct=j&opi=89978449&url=https://gleneagles.com.my/id/health-digest/bad-posture-steps-to-correct-it&ved=2ahUKEwii2qePtqWOAxUZwjgGHTjbIa4QFnoECCkQAQ&usg=AOvVaw179k7Skwv8lrz4rJdx-5h9");
        }
    }

    private void openLink(String url){
        try {
            java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private User currentUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadExerciseProgress();
    }


    private void loadExerciseProgress() {
        String[] levels = {"beginner", "intermediate", "advance"};
        int maxDays = 7;
    
        progressChart.getData().clear();
    
        for (String level : levels) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(capitalize(level));
    
            for (int day = 1; day <= maxDays; day++) {
                String filename = "hasil" + level + "day" + day + ".xml";
                File file = new File(filename);
    
                System.out.println("Membaca file: " + file.getAbsolutePath());
    
                if (file.exists()) {
                    try {
                        XStream xstream = new XStream();
                        xstream.allowTypesByWildcard(new String[] {
                            "util.**"
                        });
                        xstream.alias("Level", util.Level.class);
    
                        util.Level data = (util.Level) xstream.fromXML(file);
                        String kcalString = data.getKalori().getKcal(); // "93 kcal"
                        int kalori = Integer.parseInt(kcalString.replaceAll("[^\\d]", "")); // 93
    
                        series.getData().add(new XYChart.Data<>("Day " + day, kalori));
                    } catch (Exception e) {
                        System.out.println("Gagal parsing " + filename + ": " + e.getMessage());
                    }
                } else {
                    System.out.println("File tidak ditemukan: " + filename);
                }
            }
    
            progressChart.getData().add(series);
        }
    }
    

private String capitalize(String str) {
    if (str == null || str.isEmpty()) return str;
    return str.substring(0, 1).toUpperCase() + str.substring(1);
}

    public void initData(User user) {
        this.currentUser = user;
        greetingLabel.setText("Halo, " + user.getUsername() + "!");
    }

    // private void populateHistoryList() {
    //     List<PosturData> history = new ArrayList<>(AppData.progressHistory);
    //     Collections.reverse(history);
    //     List<PosturData> recentHistory = history.subList(0, Math.min(5, history.size()));
    //     historyListView.setItems(FXCollections.observableArrayList(recentHistory));
    //     historyListView.setCellFactory(param -> new ListCell<PosturData>() {
    //         @Override
    //         protected void updateItem(PosturData item, boolean empty) {
    //             super.updateItem(item, empty);
    //             if (empty || item == null) setText(null);
    //             else {
    //                 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM");
    //                 setText(item.getLevel() + " Exercise (" + item.getDate().format(formatter) + ")");
    //             }
    //         }
    //     });
    // }

    private void populateDashboardChart() {
        progressChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Kalori Terbakar");
        int dayCounter = 1;
        for (PosturData entry : AppData.progressHistory) {
            String category = "Latihan ke-" + dayCounter++;
            series.getData().add(new XYChart.Data<>(category, entry.getCaloriesBurned()));
        }
        progressChart.getData().add(series);
    }

    @FXML
    private void handleHistoryClick(MouseEvent event) {
        PosturData selected = historyListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String level = selected.getLevel();
            switch (level) {
                case "Beginner": loadPage("/view/BeginnerLevel.fxml"); break;
                case "Intermediate": loadPage("/view/IntermediateLevel.fxml"); break;
                case "Advanced": loadPage("/view/AdvancedLevel.fxml"); break;
            }
        }
    }

    @FXML
    private void handleNotificationsClick() {
        System.out.println("Tombol Notifikasi diklik!");
        try {
            // 1. Siapkan FXML Loader
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Notifications.fxml"));
            Parent root = loader.load();
            
            // 2. Dapatkan controller halaman tujuan
            NotificationController controller = loader.getController();

            // 3. Kirim data pengguna yang sedang login ke sana (PENTING!)
            controller.initData(this.currentUser);

            // 4. Ganti scene ke halaman notifikasi
            Stage stage = (Stage) mainBorderPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   

    
    
    @FXML
    private void handleSettingsClick() { 
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Settings.fxml"));
            Parent settingsRoot = loader.load();

            // Kirim data pengguna yang sedang login ke halaman settings
            SettingsController controller = loader.getController();
            controller.initData(this.currentUser);
            
            // Ganti scene ke halaman settings
            Stage stage = (Stage) mainBorderPane.getScene().getWindow();
            stage.setScene(new Scene(settingsRoot));
            
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    @FXML
    private void handleProfileClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProfileView.fxml"));
            Parent root = loader.load();
            
            ProfileViewController controller = loader.getController();
            controller.initData(this.currentUser); // Kirim data user yang sedang login

            // Ganti scene atau muat di tengah BorderPane
            Stage stage = (Stage) mainBorderPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleArticleClick(MouseEvent event) { System.out.println("Panel Artikel diklik!"); }

    @FXML
    private void handleBeginnerClick(MouseEvent event) {
        System.out.println("Panel Beginner diklik! Mengganti scene ke halaman Beginner...");
        try {
            // 1. Muat file FXML halaman baru yang ingin ditampilkan
            Parent beginnerRoot = FXMLLoader.load(getClass().getResource("/view/Beginner.fxml"));

            // 2. Dapatkan Stage (jendela) aplikasi saat ini
            Stage stage = (Stage) mainBorderPane.getScene().getWindow();

            // 2. Dapatkan Scene (tampilan) baru yang ingin ditampilkan
            Scene scene = new Scene(beginnerRoot);
            scene.getStylesheets().add(getClass().getResource("/css/mystyle.css").toExternalForm());

            // 3. Ganti Scene yang sedang ditampilkan dengan scene baru
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Gagal memuat halaman Beginner.fxml");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleIntermediateClick(MouseEvent event) {
        System.out.println("Panel Intermediate diklik! Mengganti scene ke halaman Intermediate...");
        try {
            // 1. Muat file FXML halaman baru yang ingin ditampilkan
            Parent intermediateRoot = FXMLLoader.load(getClass().getResource("/view/intermediate.fxml"));

            // 2. Dapatkan Stage (jendela) aplikasi saat ini
            Stage stage = (Stage) mainBorderPane.getScene().getWindow();

            // 2. Dapatkan Scene (tampilan) baru yang ingin ditampilkan
            Scene scene = new Scene(intermediateRoot);
            scene.getStylesheets().add(getClass().getResource("/css/mystyle.css").toExternalForm());

            // 3. Ganti Scene yang sedang ditampilkan dengan scene baru
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Gagal memuat halaman Beginner.fxml");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdvancedClick(MouseEvent event) {
        System.out.println("Panel Advanced diklik! Mengganti scene ke halaman Advanced...");
        try {
            // 1. Muat file FXML halaman baru yang ingin ditampilkan
            Parent advancedRoot = FXMLLoader.load(getClass().getResource("/view/advance.fxml"));

            // 2. Dapatkan Stage (jendela) aplikasi saat ini
            Stage stage = (Stage) mainBorderPane.getScene().getWindow();

            // 2. Dapatkan Scene (tampilan) baru yang ingin ditampilkan
            Scene scene = new Scene(advancedRoot);
            scene.getStylesheets().add(getClass().getResource("/css/mystyle.css").toExternalForm());

            // 3. Ganti Scene yang sedang ditampilkan dengan scene baru
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Gagal memuat halaman Beginner.fxml");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleChartClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProgressView.fxml"));
            Parent progressRoot = loader.load();

            // Dapatkan controller halaman progres
            ProgressController controller = loader.getController();
            // Kirim data pengguna yang sedang login ke sana
            controller.initData(this.currentUser);

            // Ganti scene
            Stage stage = (Stage) mainBorderPane.getScene().getWindow();
            stage.setScene(new Scene(progressRoot));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPage(String fxmlPath) {
        try {
            Parent page = FXMLLoader.load(getClass().getResource(fxmlPath));
            mainBorderPane.setCenter(page);
        } catch (IOException e) { e.printStackTrace(); }
    }
}
package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import util.Level;
import util.XMLLoader;
import util.XMLSaver;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class BeginnerDay4ExerciseController implements Initializable {
    private Level level;
    private int currentExercise = 0;
    private Timeline timer;
    private Label timeLabel;
    private Button pauseButton;
    private Button skipButton;
    private Button stopButton;
    private boolean isPaused = false;
    private int remainingSeconds = 60;

    @FXML
    private BorderPane mainPane;

    @FXML
    private ImageView exerciseImage;

    @FXML
    private VBox infoLevel;

    @FXML
    private VBox infoExercise;

    @FXML
    private HBox infoStop;

    @FXML
    private VBox infoTime;

    @FXML
    private VBox infoPause;

    @FXML
    private HBox infoSkip;

    @FXML
    private VBox infoDay;

    @FXML
    private VBox infoKcal;

    @FXML
    private VBox infoCount;

    @FXML
    private HBox infoBox;

    @FXML
    private Button backButton;

    @FXML
    private void handleBackToDashboard() {
        loadPage("/view/Dashboard.fxml");
    }

    private void loadPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupExercise(){
        
        String[] exercises = level.getExercise().getAllExercises();
        String[] images = level.getImagePath().getAllPaths();

        int totalExercises = 0;
        for (int i = 0; i < exercises.length; i++) {
            if (exercises[i] != null && !exercises[i].isEmpty()
            && images[i] != null && !images[i].isEmpty()) {
            totalExercises++;
                } else {
                    break; // asumsikan data berhenti di tengah jika ada null
                }
        }


        if(currentExercise >= totalExercises){
            showFinishScene();
            return;
        }

        //atur gambar
        String imagePath = images[currentExercise];
        if (imagePath != null) {
            exerciseImage.setImage(new Image(getClass().getResourceAsStream(imagePath)));
        }

        //clear infobox
        infoLevel.getChildren().clear();
        infoCount.getChildren().clear();
        infoExercise.getChildren().clear();
        infoStop.getChildren().clear();
        infoTime.getChildren().clear();
        infoPause.getChildren().clear();
        infoSkip.getChildren().clear();
        infoDay.getChildren().clear();
        infoKcal.getChildren().clear();
    

        //tambahkan info
        Label levelLabel = new Label(level.getLevel());
        Label dayLabel = new Label(level.getDay().getHari());
        Label exerciseLabel = new Label(exercises[currentExercise]);
        Label countLabel = new Label("Exercise " + (currentExercise + 1) + " of " + totalExercises);
        Label kcalLabel = new Label(level.getKalori().getKcal());
        timeLabel = new Label("01:00");
        pauseButton = new Button("||");
        skipButton = new Button("Skip ▶");
        stopButton = new Button("Stop ✖");

        pauseButton.setOnAction(e -> {
            if (isPaused) {
                timer.play();
                pauseButton.setText("||");
            } else {
                timer.pause();
                pauseButton.setText("▶");
            }
            isPaused = !isPaused;
        });

        skipButton.setOnAction(e -> {
            if (timer != null) {
                timer.stop();
            }
            currentExercise++;
            setupExercise();
        });
        stopButton.setOnAction(e -> {
            if (timer != null) {
                timer.stop();
            }
            showFinishScene();
        });

        infoLevel.getChildren().addAll(levelLabel);
        infoCount.getChildren().add(countLabel);
        infoExercise.getChildren().addAll(exerciseLabel);
        infoStop.getChildren().addAll(stopButton);
        infoTime.getChildren().addAll(timeLabel);
        infoPause.getChildren().addAll(pauseButton);
        infoSkip.getChildren().addAll(skipButton);
        infoDay.getChildren().addAll(dayLabel);
        infoKcal.getChildren().addAll(kcalLabel);

        //jalankan timer
        startTimer();
    }

    private void startTimer() {
        remainingSeconds = 60;
        timeLabel.setText("01:00");

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            remainingSeconds--;
            int minutes = remainingSeconds / 60;
            int seconds = remainingSeconds % 60;
            timeLabel.setText(String.format("%02d:%02d", minutes, seconds));

            if (remainingSeconds <= 0) {
                timer.stop();
                currentExercise++;
                setupExercise();
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void showFinishScene() {
        String savePath = "hasilbeginnerday4.xml"; // Ubah
        XMLSaver.savePartialLevel(level, currentExercise, savePath);

        Label finishLabel = new Label("Exercise Day 4 Completed!"); //Ubah
        finishLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: green;");
        mainPane.setCenter(finishLabel);
        mainPane.setBottom(null);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb){
        level = XMLLoader.loadLevel(getClass().getResource("/database/beginnerday4.xml").getPath()); //Ubah

        setupExercise();
    }
}

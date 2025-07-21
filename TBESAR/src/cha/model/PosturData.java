package src.cha.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PosturData { // Ganti nama kelas menjadi PosturData
    private LocalDate date;
    private double caloriesBurned;
    private double durationMinutes;
    private String level; 

    public PosturData(LocalDate date, double caloriesBurned, double durationMinutes, String level) {
        this.date = date;
        this.caloriesBurned = caloriesBurned;
        this.durationMinutes = durationMinutes;
        this.level = level; 
    }

    public LocalDate getDate() { return date; }
    public double getCaloriesBurned() { return caloriesBurned; }
    public double getDurationMinutes() { return durationMinutes; }
    public String getLevel() { return level; } 

    // Metode ini hanya untuk menghasilkan data secara manual atau dummy jika diperlukan.
    // Sekarang data akan dimuat dari XML.
    public static List<PosturData> generateDummyData(int days) {
        List<PosturData> data = new ArrayList<>();
        LocalDate currentDate = LocalDate.now().minusDays(days - 1);
        Random rand = new Random();

        // Simulasi level berdasarkan progres (ini hanya untuk dummy data)
        String currentDummyLevel = "Beginner";
        int daysInCurrentDummyLevel = 0;

        for (int i = 0; i < days; i++) {
            double calories = 300 + (rand.nextDouble() * 700);
            double duration = 30 + (rand.nextDouble() * 60);
            
            // Logika sederhana untuk perubahan level dummy setiap 7 hari
            if (daysInCurrentDummyLevel >= 7) {
                if (currentDummyLevel.equals("Beginner")) {
                    currentDummyLevel = "Intermediate";
                } else if (currentDummyLevel.equals("Intermediate")) {
                    currentDummyLevel = "Advanced";
                }
                daysInCurrentDummyLevel = 0; // Reset counter
            }

            data.add(new PosturData(currentDate, calories, duration, currentDummyLevel));
            currentDate = currentDate.plusDays(1);
            daysInCurrentDummyLevel++;
        }
        return data;
    }
}
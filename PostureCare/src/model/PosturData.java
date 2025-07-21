package model;

import java.time.LocalDate;

public class PosturData {
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
}
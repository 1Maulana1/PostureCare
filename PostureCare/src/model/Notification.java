package model;

public class Notification {
    private String message;
    private int interval;
    private String unit; // "Menit" atau "Jam"

    public Notification(String message, int interval, String unit) {
        this.message = message;
        this.interval = interval;
        this.unit = unit;
    }

    // Getters and Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public int getInterval() { return interval; }
    public void setInterval(int interval) { this.interval = interval; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    @Override
    public String toString() {
        return message + " (setiap " + interval + " " + unit + ")";
    }
}
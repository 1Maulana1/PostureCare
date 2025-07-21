package model;

public class User {
    private String email;
    private String password;
    private String username;
    
    // Field baru dari modul profil
    private String fullName;
    private int age;
    private String bio;
    private String imagePath;

    public User(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
        // Inisialisasi nilai default
        this.fullName = username; // Default fullName sama dengan username
        this.age = 0;
        this.bio = "Bio belum diatur.";
        this.imagePath = null;
    }

    // Getters & Setters untuk semua field
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}
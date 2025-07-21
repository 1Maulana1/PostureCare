package util;

public class Level {
    private String level;
    private Exercise exercise;
    private Kalori kalori;
    private Day day;
    private ImagePath imagePath;

    


    //getter dan setter untuk level, exercise, kalori, day, imagepath
    public String getLevel(){
        return level;
    }

    public void setLevel(String level){
        this.level = level;
    }

    public Exercise getExercise(){
        return exercise;
    }

    public void setExercise(Exercise exercise){
        this.exercise = exercise;
    }

    public Kalori getKalori(){
        return kalori;
    }

    public void setKalori(Kalori kalori){
        this.kalori = kalori;
    }

    public Day getDay(){
        return day;
    }

    public void setDay(Day day){
        this.day = day;
    }

    public ImagePath getImagePath(){
        return imagePath;
    }

    public void setImagePath(ImagePath imagePath){
        this.imagePath = imagePath;
    }


}

package controller;

import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import controller.DashboardController;

public class OpenSceneLevel {
    private Pane halaman;

    public Pane getPane(String fileName){
        try{
            //ubah MainBeginner jadi nama class main
            URL fileHalaman = DashboardController.class.getResource("/view/" + fileName + ".fxml");

            if(fileHalaman == null){
                throw new java.io.FileNotFoundException("Halaman tidak ditemukan");
            }

            halaman = new FXMLLoader().load(fileHalaman);
        }catch(Exception e){
            System.out.println("Tidak ditemukan halaman tersebut");
        }
        return halaman;
    }
}

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.PosturData;
import model.UserData;
import model.XmlDataReader;
import util.AppData;
import util.XmlManager;
import java.util.List;

public class Main extends Application {

    @Override
    public void init() throws Exception {
        super.init();
        UserData loadedUserData = XmlManager.loadData();
        if (loadedUserData != null && loadedUserData.getUsers() != null) {
            AppData.userList.addAll(loadedUserData.getUsers());
        }
        System.out.println("Data pengguna berhasil dimuat. Jumlah: " + AppData.userList.size());

        List<PosturData> loadedProgressData = XmlDataReader.readPosturDataFromXml("data.xml");
        if (loadedProgressData != null) {
            AppData.progressHistory.addAll(loadedProgressData);
        }
        System.out.println("Data progres berhasil dimuat. Jumlah: " + AppData.progressHistory.size());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Welcome.fxml"));
        primaryStage.setTitle("PostureCare");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setMinWidth(1024);
        primaryStage.setMinHeight(768);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
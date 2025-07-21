package src.log.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import src.log.model.User;
import src.log.model.UserData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class XmlManager {

    private static final String FILE_PATH = "users_xstream.xml";
    private static XStream xstream;

    static {
        xstream = new XStream();

        xstream.addPermission(AnyTypePermission.ANY); 
        xstream.alias("user", User.class);
        xstream.alias("userData", UserData.class);
        xstream.addImplicitCollection(UserData.class, "users");
    }

    public static void saveData(UserData userData) {
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
            xstream.toXML(userData, fos);
            System.out.println("Data berhasil disimpan ke " + FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static UserData loadData() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                return (UserData) xstream.fromXML(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new UserData();
    }
}
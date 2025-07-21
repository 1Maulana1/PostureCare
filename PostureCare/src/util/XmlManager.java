package util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import model.User;
import model.UserData;
import java.io.*;

public class XmlManager {
    private static final String FILE_PATH = "users_xstream.xml";
    private static XStream xstream;

    static {
        xstream = new XStream();
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.alias("user", User.class);
        xstream.alias("userData", UserData.class);
        xstream.addImplicitCollection(UserData.class, "users");
        xstream.aliasField("username", User.class, "username");
        xstream.aliasField("fullName", User.class, "fullName");
        xstream.aliasField("age", User.class, "age");
        xstream.aliasField("bio", User.class, "bio");
        xstream.aliasField("imagePath", User.class, "imagePath");
    }

    public static void saveData(UserData userData) {
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
            xstream.toXML(userData, fos);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static UserData loadData() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                return (UserData) xstream.fromXML(fis);
            } catch (Exception e) { e.printStackTrace(); }
        }
        return new UserData();
    }
}
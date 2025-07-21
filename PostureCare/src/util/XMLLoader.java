package util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.io.FileInputStream;

public class XMLLoader {
    public static Level loadLevel(String path){
        try{
            XStream xstream = new XStream(new StaxDriver());

            xstream.addPermission(com.thoughtworks.xstream.security.NoTypePermission.NONE);

            xstream.allowTypes(new Class[]{Level.class, Day.class, Kalori.class, Exercise.class, ImagePath.class});
            xstream.alias("util.Level", Level.class);
            return(Level) xstream.fromXML(new FileInputStream(path));
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

package util;

import model.Notification;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NotificationManager {
    private static final String FILE_PATH = "notifications.xml";

    public static List<Notification> loadNotifications() {
        List<Notification> list = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return list;

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("notification");

            for (int i = 0; i < nList.getLength(); i++) {
                Element e = (Element) nList.item(i);
                String message = e.getAttribute("message");
                int interval = Integer.parseInt(e.getAttribute("interval"));
                String unit = e.getAttribute("unit");
                list.add(new Notification(message, interval, unit));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public static void saveNotifications(List<Notification> list) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            Element root = doc.createElement("notifications");
            doc.appendChild(root);

            for (Notification item : list) {
                Element notifElement = doc.createElement("notification");
                notifElement.setAttribute("message", item.getMessage());
                notifElement.setAttribute("interval", String.valueOf(item.getInterval()));
                notifElement.setAttribute("unit", item.getUnit());
                root.appendChild(notifElement);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(FILE_PATH));
            t.transform(source, result);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
package model;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class XmlDataWriter {
    public static void saveData(List<PosturData> progressList, String filePath) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            Element rootElement = doc.createElement("progressData");
            doc.appendChild(rootElement);

            for (PosturData data : progressList) {
                Element entry = doc.createElement("entry");
                rootElement.appendChild(entry);
                Element date = doc.createElement("date");
                date.appendChild(doc.createTextNode(data.getDate().toString()));
                entry.appendChild(date);
                Element calories = doc.createElement("caloriesBurned");
                calories.appendChild(doc.createTextNode(String.valueOf(data.getCaloriesBurned())));
                entry.appendChild(calories);
                Element duration = doc.createElement("durationMinutes");
                duration.appendChild(doc.createTextNode(String.valueOf(data.getDurationMinutes())));
                entry.appendChild(duration);
                Element level = doc.createElement("level");
                level.appendChild(doc.createTextNode(data.getLevel()));
                entry.appendChild(level);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
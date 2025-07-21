package model;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class XmlDataReader {
    public static List<PosturData> readPosturDataFromXml(String filePath) {
        List<PosturData> data = new ArrayList<>();
        try {
            File xmlFile = new File(filePath);
            if (!xmlFile.exists()) return data;
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("entry");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) nNode;
                    LocalDate date = LocalDate.parse(e.getElementsByTagName("date").item(0).getTextContent());
                    double calories = Double.parseDouble(e.getElementsByTagName("caloriesBurned").item(0).getTextContent());
                    double duration = Double.parseDouble(e.getElementsByTagName("durationMinutes").item(0).getTextContent());
                    String level = e.getElementsByTagName("level").item(0).getTextContent();
                    data.add(new PosturData(date, calories, duration, level));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return data;
    }
}
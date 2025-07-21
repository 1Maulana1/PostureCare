package src.cha.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class XmlDataReader {

    public static List<PosturData> readPosturDataFromXml(String filePath) {
        List<PosturData> data = new ArrayList<>();
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("entry"); // Mencari semua tag <entry>

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    // Mengambil nilai dari setiap tag di dalam <entry>
                    LocalDate date = LocalDate.parse(eElement.getElementsByTagName("date").item(0).getTextContent());
                    double caloriesBurned = Double.parseDouble(eElement.getElementsByTagName("caloriesBurned").item(0).getTextContent());
                    double durationMinutes = Double.parseDouble(eElement.getElementsByTagName("durationMinutes").item(0).getTextContent());
                    String level = eElement.getElementsByTagName("level").item(0).getTextContent();

                    // Membuat objek PosturData dan menambahkannya ke daftar
                    data.add(new PosturData(date, caloriesBurned, durationMinutes, level));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error reading XML file: " + e.getMessage());
        }
        return data;
    }
}
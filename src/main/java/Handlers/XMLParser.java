package Handlers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class XMLParser {
    public static Map<String, String> parseXml(String xmlFile) {
        Map<String, String> expectedValues = new HashMap<>();
        try {
            // Завантажуємо XML файл з ресурсів через ClassLoader
            InputStream inputStream = XMLParser.class.getClassLoader().getResourceAsStream(xmlFile);

            if (inputStream == null) {
                System.out.println("❌ Не вдалося знайти файл " + xmlFile + " у ресурсах!");
                return expectedValues; // Повертаємо порожню мапу, якщо файл не знайдено
            }

            // Створення об'єкта для парсингу XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputStream);

            // Отримуємо всі елементи <element> з XML
            NodeList nodeList = doc.getElementsByTagName("element");

            // Проходимо через всі елементи <element>
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // Отримуємо id та value для кожного елемента
                    String elementId = element.getElementsByTagName("name").item(0).getTextContent();
                    String expectedValue = element.getElementsByTagName("value").item(0).getTextContent();

    //                // Логування для перевірки значень
    //                System.out.println("Зчитано з XML - ID: " + elementId + ", Value: " + expectedValue);

                    // Додаємо їх до мапи
                    expectedValues.put(elementId, expectedValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expectedValues;
    }

}

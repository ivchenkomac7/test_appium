package Handlers;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XmlElementLoader {

    public static Map<String, String> loadElementsFromXml(String xmlFileName) throws Exception {
        // Отримуємо шлях до ресурсів
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Path resourcePath = Paths.get(classLoader.getResource("").toURI());

        // Шукаємо XML-файл в усіх підпапках
        try (Stream<Path> paths = Files.walk(resourcePath)) {
            Optional<Path> xmlPath = paths
                    .filter(path -> path.toFile().isFile() && path.getFileName().toString().equals(xmlFileName))
                    .findFirst();

            if (!xmlPath.isPresent()) {
                throw new FileNotFoundException("❌ XML-файл '" + xmlFileName + "' не знайдено в ресурсах.");
            }

            // Завантаження XML
            Path foundPath = xmlPath.get();
            try (InputStream inputStream = Files.newInputStream(foundPath)) {
                return parseXml(inputStream);
            }
        }
    }

    public static Map<String, String> parseXml(InputStream inputStream) {
        Map<String, String> expectedValues = new HashMap<>();
        try {
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



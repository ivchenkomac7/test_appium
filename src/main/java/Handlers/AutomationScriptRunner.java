package Handlers;

import AppiumRuner.AppiumBaseRunner;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

public class AutomationScriptRunner {

    static AppiumDriver driver = AppiumBaseRunner.getDriver();


    // Головний метод для виконання всіх кроків автоматизації
    public static void executeScenario(String scenarioName) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Пошук JSON-файлу у всіх підпапках ресурсів
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Path resourcePath = Paths.get(classLoader.getResource("").toURI());

        try (Stream<Path> paths = Files.walk(resourcePath)) {
            Optional<Path> scenarioPath = paths
                    .filter(path -> path.toFile().isFile() && path.getFileName().toString().equals(scenarioName + ".json"))
                    .findFirst();

            if (!scenarioPath.isPresent()) {
                throw new FileNotFoundException("❌ Сценарій '" + scenarioName + "' не знайдено в ресурсах.");
            }

            try (InputStream inputStream = Files.newInputStream(scenarioPath.get())) {
                JsonNode rootNode = mapper.readTree(inputStream);
                ArrayNode steps = (ArrayNode) rootNode.get("steps");

                for (JsonNode step : steps) {
                    String action = step.get("action").asText();
                    switch (action) {
                        case "click":
                            String elementId = step.get("elementId").asText();
                            WebElement element = driver.findElement(By.id(elementId));
                            element.click();
                            System.out.println("✅ Натиснуто: " + elementId);
                            break;
                        case "wait":
                            int duration = step.get("duration").asInt();
                            Thread.sleep(duration);
                            System.out.println("⏳ Очікування: " + duration + " мс");
                            break;
                        default:
                            System.out.println("⚠️ Невідома дія: " + action);
                    }
                }
            }
        }
    }
}
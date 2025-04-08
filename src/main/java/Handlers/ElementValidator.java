package Handlers;

import AppiumRuner.AppiumBaseRunner;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.Map;

public class ElementValidator {
    static AppiumDriver driver = AppiumBaseRunner.getDriver();

    // Метод для перевірки елементів
    public static void checkElements(String xmlFileName) {
        try {
            // Завантажуємо елементи з XML
            Map<String, String> expectedValues = XmlElementLoader.loadElementsFromXml(xmlFileName);

            // Перевіряємо елементи
            for (Map.Entry<String, String> entry : expectedValues.entrySet()) {
                String elementName = entry.getKey();  // name елемента
                String expectedValue = entry.getValue();  // Очікуване значення

                checkElementValue(elementName, expectedValue);
            }
        } catch (Exception e) {
            System.out.println("Помилка при завантаженні XML файлу: " + e.getMessage());
        }
    }

    // Метод для перевірки конкретного елемента
    private static void checkElementValue(String elementName, String expectedValue) {
        try {
            // Знаходимо елемент за ім'ям
            WebElement element = driver.findElement(By.name(elementName));  // Знайти елемент
            String actualValue = element.getAttribute("value");

            if (actualValue == null) {
                actualValue = "";  // Якщо значення не задано, приймаємо порожній рядок
            }

            if (actualValue.equals(expectedValue)) {
                System.out.println("✅ Елемент \"" + elementName + "\" має правильне значення: \"" + actualValue + "\"");
            } else {
                System.out.println("❌ Елемент \"" + elementName + "\" має НЕправильне значення. Очікувалося: \"" + expectedValue + "\", отримано: \"" + actualValue + "\"");
            }
        } catch (NoSuchElementException e) {
            System.out.println("❌ Елемент \"" + elementName + "\" НЕ знайдено у вікні!");
        } catch (Exception e) {
            System.out.println("⚠️ Невідома помилка при перевірці \"" + elementName + "\": " + e.getMessage());
        }
    }
}


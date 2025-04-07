package Handlers;

import AppiumRuner.AppiumBaseRunner;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class AutomationScriptRunner {

    static AppiumDriver driver = AppiumBaseRunner.getDriver();


    // Головний метод для виконання всіх кроків автоматизації
    public static void execute() throws InterruptedException {

        try {
            // ✅ Натискаємо кнопку "Create"
            WebElement newDocumentButton = driver.findElement(By.name("Create"));
            newDocumentButton.click();

            Thread.sleep(1000);

            // ✅ Натискаємо кнопку "Новий аналіз"
            WebElement newAnalysis = driver.findElement(By.name("New Analysis..."));
            newAnalysis.click();

            // ✅ Натискаємо кнопку "Simple Linear Regression"
            WebElement newSLR = driver.findElement(By.name("Simple linear regression"));
            newSLR.click();

            Thread.sleep(1000);

            // ✅ Натискаємо кнопку "OK"
            WebElement OK = driver.findElement(By.name("OK"));
            OK.click();

            Thread.sleep(1000);

            WebElement showThe = driver.findElement(By.name("Show the"));
            showThe.click();
            Thread.sleep(1000);

//            WebElement confidence = driver.findElement(By.name("95% confidence bands"));
//            confidence.click();
//            Thread.sleep(1000);


            Map<String, String> expectedValues = XMLParser.parseXml("elements.xml");

            for (Map.Entry<String, String> entry : expectedValues.entrySet()) {
                String elementName = entry.getKey();  // ID елемента
                String expectedValue = entry.getValue();  // Очікуване значення

                try {
                    WebElement element = driver.findElement(By.name(elementName));  // Шукаємо елемент
                    String actualValue = element.getAttribute("value");  // Отримуємо його значення

                    if (actualValue == null) {
                        actualValue = "";  // Якщо атрибут не знайдений, встановлюємо порожній рядок
                    }

                    if (actualValue.equals(expectedValue)) {
                        System.out.println("✅ Елемент " + elementName + " має правильне значення: " + actualValue);
                    } else {
                        System.out.println("❌ Елемент " + elementName + " має НЕправильне значення. Очікувалося: " + expectedValue + ", отримано: " + actualValue);
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("❌ Елемент " + elementName + " НЕ знайдено у вікні!");
                } catch (Exception e) {
                    System.out.println("⚠️ Невідома помилка: " + e.getMessage());
                }

                List<WebElement> menuItems = driver.findElements(By.id("95% confidence bands"));
                for (WebElement item : menuItems) {
                    System.out.println("Опція меню: " + item.getText());
                }

            }
            // ✅ Натискаємо кнопку "OK" в SLR вікні
            WebElement okSLR = driver.findElement(By.id("OK"));
            okSLR.click();


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
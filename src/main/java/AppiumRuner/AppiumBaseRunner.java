package AppiumRuner;

import io.appium.java_client.AppiumDriver;

import java.net.MalformedURLException;

public abstract class AppiumBaseRunner {
    protected static AppiumDriver driver;

    // Метод для запуску драйвера (реалізується в дочірніх класах)
    public abstract void startDriver() throws MalformedURLException;

    // Метод для зупинки драйвера
    public void stopDriver() {
        if (driver != null) {
            driver.quit();
            System.out.println("🛑 Драйвер закрито.");
        }
    }

    public static AppiumDriver getDriver() {
        return driver;
    }
}

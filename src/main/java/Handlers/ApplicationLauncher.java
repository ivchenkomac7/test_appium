package Handlers;

import AppiumRuner.AppiumBaseRunner;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.JavascriptExecutor;

import java.util.HashMap;
import java.util.Map;

public class ApplicationLauncher {
    static AppiumDriver driver = AppiumBaseRunner.getDriver();


    private static final String MAC_APP = "Prism";

    public static void launchApplication() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("mac")) {
            launchMacApplication();
        } else if (os.contains("win")) {
            launchWindowsApplication();
        } else {
            System.out.println("❌ Невідома операційна система: " + os);
        }
    }
    private static void launchMacApplication() {
        System.out.println("🚀 Запуск на macOS...");
        try {
            JavascriptExecutor js = driver;
            new ProcessBuilder("open", "-a", MAC_APP).start();
            System.out.println("✅ Програму успішно запущено!");
            Thread.sleep(2000);

            Map<String, Object> activateApp = new HashMap<>();
            activateApp.put("bundleId", "com.GraphPad.Prism");
            js.executeScript("macos: activateApp", activateApp);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("❌ Помилка запуску програми: " + e.getMessage());
        }
    }

    private static void launchWindowsApplication() {
        System.out.println("🚀 Запуск на Windows...");
        try {
            new ProcessBuilder("notepad.exe").start();
            System.out.println("✅ Програму успішно запущено!");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("❌ Помилка запуску програми: " + e.getMessage());
        }
    }
}

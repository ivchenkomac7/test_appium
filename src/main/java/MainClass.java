import AppiumRuner.AppiumBaseRunner;
import AppiumRuner.MacAppiumRunner;
import AppiumRuner.WindowsAppiumRunner;
import Handlers.ApplicationLauncher;
import Handlers.AutomationScriptRunner;
import Handlers.ElementValidator;

public class MainClass {
    public static void main(String[] args) throws Exception {
        String os = System.getProperty("os.name").toLowerCase();
        AppiumBaseRunner runner;

        if (os.contains("mac")) {
            runner = new MacAppiumRunner();
        } else if (os.contains("win")) {
            runner = new WindowsAppiumRunner();
        } else {
            System.out.println("🚫 Непідтримувана операційна система.");
            return;
        }

        try {
            runner.startDriver();

            ApplicationLauncher.launchApplication();
            Thread.sleep(3000);

            AutomationScriptRunner.executeScenario("simple_linear_regression_mac");
            ElementValidator.checkElements("simple_linear_regression_elements_mac.xml");
            // Додай код для роботи з UI елементами тут...

            runner.stopDriver();
        } catch (Exception e) {
            System.out.println("❌ Помилка запуску драйвера: " + e.getMessage());
        }
    }
}

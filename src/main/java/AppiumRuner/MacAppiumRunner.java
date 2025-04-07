package AppiumRuner;

import AppiumRuner.AppiumBaseRunner;
import io.appium.java_client.mac.Mac2Driver;
import io.appium.java_client.mac.options.Mac2Options;

import java.net.MalformedURLException;
import java.net.URL;

public class MacAppiumRunner extends AppiumBaseRunner {

    @Override
    public void startDriver() throws MalformedURLException {
        System.out.println("🖥️ Запуск Appium для Mac...");

        Mac2Options options = new Mac2Options();

        driver = new Mac2Driver(new URL("http://localhost:4723"), options);
        System.out.println("✅ Appium для Mac успішно запущено!");
    }
}

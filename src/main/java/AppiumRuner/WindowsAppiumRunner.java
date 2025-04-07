package AppiumRuner;


import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.options.WindowsOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class WindowsAppiumRunner extends AppiumBaseRunner {
    private static final String WIN_PATH = "C:\\Program Files\\GraphPad\\Prism beta\\prism.exe";

    @Override
    public void startDriver() throws MalformedURLException {
        System.out.println("🖥️ Запуск Appium для Windows...");

        WindowsOptions options = new WindowsOptions();
        options.setApp(WIN_PATH);

        driver = new WindowsDriver(new URL("http://localhost:4723"), options);
        System.out.println("✅ Appium для Windows успішно запущено!");
    }
}

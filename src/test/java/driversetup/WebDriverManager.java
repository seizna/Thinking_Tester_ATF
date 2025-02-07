package driversetup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class WebDriverManager {
    private static WebDriverManager manager;
    private final WebDriver DRIVER;
    private static WebDriverWait wait;

    private WebDriverManager() {
        DRIVER = new ChromeDriver();
        DRIVER.manage().window().maximize();
        DRIVER.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(DRIVER, Duration.ofSeconds(10));
    }

    public static WebDriver getDriver() {
        if (manager == null) {
            manager = new WebDriverManager();
        }
        return manager.DRIVER;
    }

    public static void closeDriver() {
        if (manager != null) {
            manager.DRIVER.quit();
            manager = null;
        }
    }

    public static WebDriverWait getWait() {
        if (wait == null) {
            throw new IllegalStateException("WebDriverWait is not initialized.");
        }
        return wait;
    }
}


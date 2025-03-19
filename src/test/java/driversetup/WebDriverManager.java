package driversetup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebDriverManager {
    private static WebDriverManager instance;
    private final WebDriver DRIVER;
    private static WebDriverWait wait;

    private WebDriverManager() {
        DRIVER = new ChromeDriver();
        DRIVER.manage().window().maximize();
        DRIVER.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(DRIVER, Duration.ofSeconds(5));
    }

    public static WebDriver getDriver() {
        if (instance == null) {
            instance = new WebDriverManager();
        }
        return instance.DRIVER;
    }

    public static void closeDriver() {
        if (instance != null) {
            instance.DRIVER.quit();
            instance = null;
        }
    }

    public static WebDriverWait getWait() {
        if (wait == null) {
            throw new IllegalStateException("WebDriverWait is not initialized.");
        }
        return wait;
    }
}


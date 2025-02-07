package hooks;

import database.HibernateUtil;
import driversetup.WebDriverManager;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;


public class Hooks {

    private final Logger LOGGER = LogManager.getLogger(Hooks.class);

    @Before(order = 1, value = "@UI")
    public void openBrowser() {
        WebDriverManager.getDriver();
        LOGGER.info("Browser in opened.");
    }

    @Before(order = 2, value = "@DB")
    public void setUpHibernateSession() {
        try {
            HibernateUtil.getSessionFactory();
            LOGGER.info("Hibernate session created.");
        } catch (HibernateException e) {
            LOGGER.error("Failed to set up Hibernate session.");
            e.printStackTrace();
        }
    }

    @Before(order = 3, value = "@API")
    public void dummyApiBefore() {
        LOGGER.info("Dummy Before hook for API");
    }


    @After(order = 1, value = "@UI")
    public void closeBrowser() {
        WebDriverManager.closeDriver();
        LOGGER.info("Browser is closed.");
    }

    @After(order = 2, value = "@DB")
    public void shutDownHibernateSession() {
        HibernateUtil.shutdownSession();
        LOGGER.info("Hibernate session closed.");
    }

    @After(order = 3, value = "@API")
    public void dummyApiAfter() {
        LOGGER.info("Dummy After hook for API");
    }


    @AfterStep(value = "@TakeScreenshot")
    public void takeScreenshot() {
        byte[] screenshot = ((TakesScreenshot) WebDriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
        String fileName = UUID.randomUUID() + ".png";
        Path screenshotPath = Path.of("target/allure-results", fileName);
        try {
            if (!Files.exists(screenshotPath)) {
                Files.createDirectory(screenshotPath);
            }
            Files.write(screenshotPath, screenshot);
        } catch (Exception e){
            LOGGER.warn("Something went wrong while saving screenshot {}.", String.valueOf(e));
        }
       Allure.getLifecycle().addAttachment("Screenshot", "image/png", ".png", new ByteArrayInputStream(screenshot));
    }
}

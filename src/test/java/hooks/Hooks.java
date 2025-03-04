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
import driversetup.BrowserActions;
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class Hooks {

    private final Logger LOG = LogManager.getLogger(Hooks.class);
    BrowserActions browserActions = new BrowserActions();

    @Before(order = 1, value = "@UI")
    public void openBrowser() {
       browserActions.openBrowser();
        LOG.info("Browser in opened.");
    }

    @Before(order = 2, value = "@DB")
    public void setUpHibernateSession() {
        try {
            HibernateUtil.getSessionFactory();
            LOG.info("Hibernate session created.");
        } catch (HibernateException ex) {
            LOG.error("Failed to set up Hibernate session.");
            ex.printStackTrace();
        }
    }

    @Before(order = 3, value = "@API")
    public void dummyApiBefore() {
        LOG.info("Dummy Before hook for API");
    }


    @After(order = 1, value = "@UI")
    public void closeBrowser() {
        browserActions.closeBrowser();
        LOG.info("Browser is closed.");
    }

    @After(order = 2, value = "@DB")
    public void shutDownHibernateSession() {
        HibernateUtil.shutdownSession();
        LOG.info("Hibernate session closed.");
    }

    @After(order = 3, value = "@API")
    public void dummyApiAfter() {
        LOG.info("Dummy After hook for API");
    }


    @AfterStep(value = "@TakeScreenshot")
    public void takeScreenshot() {
       byte[] screenshot = ((TakesScreenshot) WebDriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
        String fileName = UUID.randomUUID() + ".png";
        Path screenshotPath = Path.of("target/allure-results", fileName);

        try {
            Files.write(screenshotPath, screenshot);
            if (Allure.getLifecycle().getCurrentTestCase().isPresent()) {
                Allure.getLifecycle().addAttachment("Screenshot", "image/png", ".png", new ByteArrayInputStream(screenshot));
            }
        } catch (Exception ex){
            LOG.warn("Something went wrong while saving screenshot {}.", String.valueOf(ex));
        }
    }
}

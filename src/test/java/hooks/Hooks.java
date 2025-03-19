package hooks;

import database.HibernateUtil;
import driversetup.BrowserActions;
import driversetup.WebDriverManager;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class Hooks {

    private final Logger LOG = LogManager.getLogger(Hooks.class);
    BrowserActions browserActions = new BrowserActions();

    @Before(order = 0)
    public void setScenarioIdentifier(Scenario scenario) {
        String identifier = scenario.getName();
        ThreadContext.put("scenarioName", identifier);
        LOG.info("Running scenario: {}", identifier);
    }

    @Before(order = 1, value = "@UI")
    public void openBrowser() {
       browserActions.openBrowser();
        LOG.info("Browser in opened.");
    }

    @Before(order = 2, value = "@DB")
    public void setUpHibernateSession() {
            HibernateUtil.getSessionFactory();
            LOG.info("Hibernate session created.");
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

    @After(order = 0)
    public void clearScenarioIdentifier() {
        ThreadContext.remove("scenarioName");
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
        } catch (IOException ex) {
            LOG.warn("Failed to save screenshot to 'target/allure-results'. Please ensure the directory exists and has write permissions. Exception details: {}", ex.getMessage());
        }
    }
}

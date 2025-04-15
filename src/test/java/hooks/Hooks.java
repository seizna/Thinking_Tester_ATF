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
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Hooks {

    private static final Logger LOG = LogManager.getLogger(Hooks.class);
    BrowserActions browserActions = new BrowserActions();

    @Before(order = 0)
    public void beforeScenario(Scenario scenario) {
        String fullPath = scenario.getUri().toString();
        String relativePath = fullPath.substring(fullPath.indexOf("features/") + "features/".length());
        String[] pathParts = relativePath.split("/");

        String scenarioType = pathParts[0];
        String featureName = pathParts[1].replace(".feature", "");
        String timestamp = new SimpleDateFormat("dd-MM-yyyy HH.mm.ss.SSS").format(new Date());
        String scenarioName = scenario.getName();

        ThreadContext.put("scenarioType", scenarioType);
        ThreadContext.put("featureName", featureName);
        ThreadContext.put("timestamp", timestamp);
        ThreadContext.put("scenarioName", scenarioName);
        LOG.info("Running '{}' feature via {}. \n Scenario: {}.", featureName, scenarioType, scenarioName);
    }

    @Before(order = 1, value = "@UI")
    public void openBrowser() {
       browserActions.openBrowser();
        LOG.debug("Browser in opened.");
    }

    @Before(order = 2, value = "@DB")
    public void setUpHibernateSession() {
        HibernateUtil.getSessionFactory();
        LOG.debug("Hibernate session created.");
    }

    @Before(order = 3, value = "@API")
    public void dummyApiBefore() {
        LOG.debug("Dummy Before hook for API");
    }

    @After(order = 3, value = "@API")
    public void dummyApiAfter() {
        LOG.debug("Dummy After hook for API");
    }

    @After(order = 2, value = "@DB")
    public void shutDownHibernateSession() {
        HibernateUtil.shutdownSession();
        LOG.debug("Hibernate session closed.");
    }

    @After(order = 1, value = "@UI")
    public void closeBrowser() {
        browserActions.closeBrowser();
        LOG.debug("Browser is closed.");
    }

    @After(order = 0)
    public void afterScenario(Scenario scenario) {
        try {
            String path = String.format("logs/%s/%s/%s/%s.log",
                    ThreadContext.get("scenarioType"),
                    ThreadContext.get("featureName"),
                    ThreadContext.get("timestamp"),
                    ThreadContext.get("scenarioName"));

            Allure.addAttachment("Logs for: " + scenario.getName(), "text/plain", new FileInputStream(path), ".log");
        } catch (IOException ex) {
            LOG.error("Could not create log file: {}", ex.getMessage());
        } finally {
            ThreadContext.clearAll();
        }
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

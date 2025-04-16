package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions", "hooks"},
        plugin = {"json:target/cucumber-reports/cucumber.json",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/allure-results/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        }
//        tags = "@DemoRun"
)

public class TestRunner {
}

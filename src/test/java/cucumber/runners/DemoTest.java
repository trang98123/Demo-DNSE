package cucumber.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@CucumberOptions(glue = "cucumber.stepdefinitions",
        features = "src/test/resources",
        plugin = { "pretty", "html:target/cucumber-reports","json:target/cucumber.json","rerun:rerun/failed_scenarios.txt" },
        tags = {"@demo"})
@Test
public class DemoTest extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

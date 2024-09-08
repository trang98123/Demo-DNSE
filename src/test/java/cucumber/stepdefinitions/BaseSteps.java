package cucumber.stepdefinitions;

import common.AutoWebApp;
import common.WebAppManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class BaseSteps extends AutoSteps {
    public BaseSteps() throws Throwable {

    }

    @Before
    public void beforeScenario(Scenario scen) throws Throwable {
        System.out.println("Thread - " + Thread.currentThread().getId() + " :" + "*** STARTING TEST SCENARIO: " + scen.getName() + " ***");
        AutoWebApp app = (AutoWebApp) WebAppManager.getWebApp(AutoWebApp.class);

    }




    @After(order = 0)
    public void afterScenarios(Scenario scen) throws Throwable {
        //take screenshot
        String filename = scen.getName();
        filename = filename.replaceAll("\\W", "_");

        final byte[] screenshot;
        if (scen.isFailed()) {
            screenshot = app.takeScreenshot(data.generateScreenshotName(filename));
            scen.embed(screenshot, "image/png", filename);
        }

        System.out.println("Thread - " + Thread.currentThread().getId() + " :" + "*** FINISHING TEST SCENARIO: " + scen.getName() + " ***");
        WebAppManager.quitApp();
    }
}

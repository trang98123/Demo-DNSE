package cucumber.stepdefinitions;

import common.*;
import data.Data;


public class AutoSteps {

    protected AutoWebApp app;
    Data data = new Data();

    public AutoSteps() throws Exception {
        try {
            app = (AutoWebApp) WebAppManager.getWebApp(AutoWebApp.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Get Login page
     *
     * @throws Exception
     */
    public HomePagePO getHomePage() throws Exception {
        return (HomePagePO) app.waitForPage(HomePagePO.class);
    }




}

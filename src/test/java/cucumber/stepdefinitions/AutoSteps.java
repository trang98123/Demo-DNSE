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
     * Get Senses Page
     *
     * @throws Exception
     */
    public SensesPagePO getSensesPage() throws Exception {
        return (SensesPagePO) app.waitForPage(SensesPagePO.class);
    }

    /**
     * Get Stock Page
     * @return
     * @throws Exception
     */
    public StockPagePO getStockPage() throws Exception
    {
        return (StockPagePO) app.waitForPage(StockPagePO.class);
    }
}

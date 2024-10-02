package common;

import org.openqa.selenium.By;

public class SensesPagePO extends WebPageBase {
    @Override
    public void init() {

    }

    @Override
    public void checkOnPage() throws Exception {

    }

    public final static By INPUT_STOCK_CODE = By.xpath("//input[@name='autocomplete-search']");
    public final static By STOCK_CODE_TOP = By.xpath("//div[@class='text-left space-x-3 mt-3']//a[3]");
    public final static By SUB_MENU_SENSES = By.xpath("//span[text()='Senses']");
    public final static By MENU_THI_TRUONG = By.xpath("//button[text()='Thị trường']");
    private static By STOCK_CODE_ITEM;

    public SensesPagePO() throws Exception {
        super();
        app = WebAppManager.getWebApp(AutoWebApp.class);
        driver = app.getDriver();
    }

    public void enterToStockCode(String value) throws Exception {
        try {
            app.hoverMouseToElement(MENU_THI_TRUONG);
            app.clickToElementByJavaScriptExecutor(SUB_MENU_SENSES);
            Thread.sleep(12000);
            app.safeElementSendKeys(INPUT_STOCK_CODE, value);
        } catch (Exception e) {
            app.getLogger().logInfo(this.getClass(), String.format("Failed to Enter to text box Stock code!"));
            throw e;
        }
    }

    public void clickToStockCode(String value) throws Exception {
        try {
            STOCK_CODE_ITEM = By.xpath(String.format("//span[text()='%s']", value));
            app.clickToElementIndex(STOCK_CODE_ITEM, value);
        } catch (Exception e) {
            app.getLogger().logInfo(this.getClass(), String.format("Failed to click to Stock code Search!"));
            throw e;
        }
    }


    public void clickStockTopSearch() throws Exception {
        try {
            app.clickToElement(STOCK_CODE_TOP);
        } catch (Exception e) {
            app.getLogger().logInfo(this.getClass(), String.format("Failed to click Stock Top Search!"));
            throw e;
        }
    }


}

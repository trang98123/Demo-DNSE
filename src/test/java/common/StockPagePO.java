package common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StockPagePO extends WebPageBase{
    @Override
    public void init() {

    }
    @Override
    public void checkOnPage() throws Exception {

    }
    public final static By BUTTON_SHARE = By.xpath("//p[text()='Chia sẻ']");
    public final static By LINK_TEXT = By.xpath("//p[@class='text-sm truncate text-gray-300 grow']");
    public final static By VIEW_STOCK_CODE=By.xpath("//div[@class='flex flex-col gap-1 md:gap-2']//h2");
    public final static By ICON_CLOSE=By.xpath("//div[@class='flex items-center mb-6']//button");

    public StockPagePO() throws Exception {
        super();
        app = WebAppManager.getWebApp(AutoWebApp.class);
        driver = app.getDriver();
    }
    public void clickToButtonShare() throws Exception {
        try {
            app.clickToElement(BUTTON_SHARE);
        } catch (Exception e) {
            app.getLogger().logInfo(this.getClass(), String.format("Failed to click to button Share!"));
            throw e;
        }
    }

    public void printLinkText() throws Exception {
        try {
            app.getTextOfElement(LINK_TEXT);
            System.out.println("Link text: " + app.getTextOfElement(LINK_TEXT));
            app.clickToElement(ICON_CLOSE);
        } catch (Exception e) {
            app.getLogger().logInfo(this.getClass(), String.format("Failed to get Link text!"));
            throw e;
        }
    }
    public void printStockCode() throws Exception {
        try {
            app.getTextOfElement(VIEW_STOCK_CODE);
            System.out.println("Stock code: " + app.getTextOfElement(VIEW_STOCK_CODE));
        } catch (Exception e) {
            app.getLogger().logInfo(this.getClass(), String.format("Failed to get Stock code!"));
            throw e;
        }
    }

    public void backToFirstTab() throws Exception
    {
        try
        {   app.navigateBack();
        }
        catch (Exception e)
        {
            app.getLogger().logInfo(this.getClass(), String.format("Failed to back to first tab!"));
            throw e;
        }
    }
    public boolean isElementStockCodeDisplayed() throws Exception {
        try {
            return app.isElementDisplayed(VIEW_STOCK_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

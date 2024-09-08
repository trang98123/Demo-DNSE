package common;

import org.openqa.selenium.By;

public class HomePagePO extends WebPageBase {
    @Override
    public void init() {

    }

    @Override
    public void checkOnPage() throws Exception {

    }

    public final static By FROM_LOCATION = By.xpath("//input[@name='From']");
    public final static By To_LOCATION = By.xpath("//input[@name='To']");
    public final static By INPUT_FROM_LOCATION = By.xpath("//div[text()='Chọn điểm đi']/following-sibling::div/span/input");
    public final static By INPUT_TO_LOCATION = By.xpath("//div[text()='Chọn điểm đến']/following-sibling::div/span/input");
    public final static By SEARCH_FROM = By.xpath("//p[contains(.,'HAN - Sân bay Nội Bài - Hà Nội - Việt Nam')]");
    public final static By SEARCH_TO = By.xpath("//p[contains(.,'SGN - Sân bay Tân Sơn Nhất - Hồ Chí Minh - Việt Nam')]");

    private static By DATE_FROM_PICKER_CALENDAR;
    private static By DATE_TO_PICKER_CALENDAR;
    public final static By DEPARTURE_DATE_FLIGHT = By.xpath("//input[@id='departure_date_flight']");
    public final static By RETURNING_DATE_FLIGHT = By.xpath("//input[@id='returning_date_flight']");
    public final static By FLIGHT_PASSENGER = By.xpath("//input[@id='flight_passenger']");
    public final static By ADULT_NUMBER_INCREASE = By.xpath("(//div[@class='mktnd_btn_flight_adult_plus btn btn-plus btn-plus-0 btn-number'])[2]");
    public final static By ADULT_NUMBER_REDUCE = By.xpath("(//div[@class='mktnd_btn_flight_adult_minus btn btn-minus btn-minus-0 btn-number'])[2]");
    public final static By CHILDREN_NUMBER_REDUCE = By.xpath("(//div[@class='mktnd_btn_children_adult_minus btn btn-minus btn-minus-1 btn-number'])[2]");
    public final static By CHILDREN_NUMBER_INCREASE = By.xpath("//div[2]/div/div[2]/div[2]/div/span[2]/div/i");

    public final static By BUTTON_SEARCH_FLIGHT = By.xpath("//button[contains(text(),'Tìm chuyến bay')]");
    public final static By GET_DEPARTURE_DATE_FLIGHT = By.xpath("//input[@id='departure_date_flight']");
    public final static By GET_PAGE_SOURCE = By.xpath("//pre[text()='han']");
    public final static By TICKET_DISPLAYED=By.xpath("//span[@class='hidden-xs date-text-change-depart']//span[text()='Thứ 6, 27/09/2024']");
    public HomePagePO() throws Exception {
        super();
        app = WebAppManager.getWebApp(AutoWebApp.class);
        driver = app.getDriver();
    }

    public boolean isElementTicketDisplayed() {
        try {
       return     app.isElementDisplayed(TICKET_DISPLAYED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void clickToButtonSearchFlight() throws Exception {
        try {
            app.clickToElement(BUTTON_SEARCH_FLIGHT);
        } catch (Exception e) {
            app.getLogger().logInfo(this.getClass(), String.format("Failed to click to Search flight!"));
        }
    }

    public void clickPassenger() throws Exception {
        try {
            app.clickToElement(FLIGHT_PASSENGER);
            app.hoverMouseToElement(ADULT_NUMBER_INCREASE);
            app.hoverMouseToElement(ADULT_NUMBER_REDUCE);
            app.hoverMouseToElement(CHILDREN_NUMBER_REDUCE);
            app.clickToElementByJavaScriptExecutor(CHILDREN_NUMBER_INCREASE);
        } catch (Exception e) {
            app.getLogger().logInfo(this.getClass(), String.format("Failed to click Passenger!"));
            throw e;
        }
    }

    public void clickReturningDate(String index) throws Exception {
        try {
            app.clickToElement(RETURNING_DATE_FLIGHT);
            DATE_TO_PICKER_CALENDAR=  By.xpath(String.format("(//div[@id='ui-datepicker-div']//span[@class='ui-datepicker-day ' and text()='%s'])[1]",index));
            app.clickToElementIndex(DATE_TO_PICKER_CALENDAR, index);
        } catch (Exception e) {
            app.getLogger().logInfo(this.getClass(), String.format("Failed to click Returning date"));
            throw e;
        }
    }

    public void clickDepartureDate(String index) throws Exception {
        try {
            app.clickToElement(DEPARTURE_DATE_FLIGHT);
            DATE_FROM_PICKER_CALENDAR = By.xpath(String.format("//div[@id='ui-datepicker-div']/div/table/tbody/tr[4]/td[6]/a/span[@class='ui-datepicker-day ' and text()='%s']", index));
            app.clickToElementIndex(DATE_FROM_PICKER_CALENDAR, index);
        } catch (Exception e) {
            app.getLogger().logInfo(this.getClass(), String.format("Failed to click Departure date"));
            throw e;
        }
    }


    public void inputFromLocation(String value) throws Exception {
        try {

            app.clickToElement(FROM_LOCATION);
            app.safeElementSendKeys(INPUT_FROM_LOCATION, value);
            //   Thread.sleep(1);
            //  String  name=    app.getPageSource(driver,INPUT_FROM_LOCATION);
            //   System.out.println(name);
            app.hoverMouseToElement(SEARCH_FROM);
            app.clickToElementByJavaScriptExecutor(SEARCH_FROM);
        } catch (Exception e) {
            app.getLogger().logInfo(this.getClass(), String.format("Failed to input From Location!"));
            throw e;
        }
    }

    public void inputToLocation(String value) throws Exception {
        try {
            app.clickToElement(To_LOCATION);
            app.safeElementSendKeys(INPUT_TO_LOCATION, value);
            //   String  name1=    app.getPageSource(driver,INPUT_FROM_LOCATION);
            // System.out.println(name1);
            app.hoverMouseToElement(SEARCH_TO);
            app.clickToElementByJavaScriptExecutor(SEARCH_TO);
            //  app.accepAlert1(driver);
        } catch (Exception e) {
            app.getLogger().logInfo(this.getClass(), String.format("Failed to input To Location!"));
            throw e;
        }
    }


}

package cucumber.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class SensesPageSteps extends AutoSteps {
    public SensesPageSteps() throws Exception {

    }


    @Given("I go to DNSE website")
    public void iGoToDNSEWebsite() throws Exception {
        app.navigateToLoginPage();
    }

    @And("I enter to text box Stock code with value {string}")
    public void iEnterToTextBoxStockCodeWithValue(String value) throws Exception {
        getSensesPage().enterToStockCode(value);
    }

    @And("I select to {string} stock and view detail page")
    public void iSelectToStockAndViewDetailPage(String value) throws Exception {
        getSensesPage().clickToStockCode(value);
    }

    @And("I select the stock at number of the Top Search")
    public void iSelectTheStockAtNumberOfTheTopSearch() throws Exception {
        getSensesPage().clickStockTopSearch();
    }
}

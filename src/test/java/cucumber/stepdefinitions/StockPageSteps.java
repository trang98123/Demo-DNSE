package cucumber.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class StockPageSteps extends AutoSteps{

    public StockPageSteps() throws Exception {
    }

    @And("I click button Share")
    public void iClickButtonShare() throws Exception{
        getStockPage().clickToButtonShare();
    }

    @Then("Print out the shared link of DSE shares to the screen")
    public void printOutTheSharedLinkOfDSESharesToTheScreen() throws Exception{
         getStockPage().isElementStockCodeDisplayed();
         getStockPage().printLinkText();
    }

    @And("I go back to Senses page")
    public void iGoBackToSensesPage() throws Exception{
      getStockPage().backToFirstTab();
    }

    @Then("View detail page and print out the stock code")
    public void viewDetailPageAndPrintOutTheStockCode() throws Exception{
        getStockPage().printStockCode();
    }
}

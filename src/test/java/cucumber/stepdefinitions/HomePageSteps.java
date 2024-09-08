package cucumber.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class HomePageSteps extends AutoSteps {
    public HomePageSteps() throws Exception {
    }

    @Given("I go to Best_price website")
    public void iGoToBest_priceWebsite() throws Throwable {
        app.navigateToLoginPage();
    }


    @And("Select to departure date")
    public void selectToDepartureDate() throws Throwable {
        getHomePage().clickDepartureDate("27");
    }

    @And("Select to returning date")
    public void selectToReturningDate() throws Throwable {
        getHomePage().clickReturningDate("30");
    }

    @And("Select number of passengers")
    public void selectNumberOfPassengers() throws Throwable {
         getHomePage().clickPassenger();
    }


    @Then("Airline tickets displayed")
    public void airlineTicketsDisplayed() throws Throwable {
        Assert.assertTrue(getHomePage().isElementTicketDisplayed());
    }


    @And("Input to text box From location with value {string}")
    public void inputToTextBoxFromLocationWithValue(String value) throws Throwable{
        getHomePage().inputFromLocation(value);
    }

    @And("Input to text box To location with value {string}")
    public void inputToTextBoxToLocationWithValue(String value) throws Throwable{
        getHomePage().inputToLocation(value);
    }

    @And("Click button Search Flight")
    public void clickButtonSearchFlight() throws Throwable{
        getHomePage().clickToButtonSearchFlight();
    }
}

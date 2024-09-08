@demo
Feature: Book Air Tickets

  Scenario:
    Given I go to Best_price website
    And Input to text box From location with value "han"
    And Input to text box To location with value "Ho c"
    And Select to departure date
    And Select to returning date
    And Select number of passengers
    And Click button Search Flight
    Then Airline tickets displayed
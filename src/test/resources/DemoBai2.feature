@demo
Feature: Search for stock code

  Scenario: Test case 01
    Given I go to DNSE website
    And I enter to text box Stock code with value "DS"
    And I select to "DSE" stock and view detail page
    And I click button Share
    Then Print out the shared link of DSE shares to the screen
    And I go back to Senses page
    And I select the stock at number of the Top Search
    Then View detail page and print out the stock code

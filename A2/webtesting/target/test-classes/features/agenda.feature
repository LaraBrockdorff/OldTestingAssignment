Feature: Login functionality

  I want to be able to Login and buy


  Scenario: Simple Login
    Given I am a user on the Agenda website
    When I log in with valid credentials
    Then I should be logged in

  Scenario: Invalid Login
    Given I am a user on the Agenda website
    When I log in with invalid credentials
    Then I should not be logged in

  Scenario: Product Search
    Given I am a logged in user
    When I search for a product "book"
    And I select the first product in the line
    Then I should see the product details

  Scenario: Add product to cart
    Given I am a logged in user
    And my shopping cart is empty
    When I view the details of a product "book"
    And I choose to buy the product
    Then my shopping cart should contain 1 item


  Scenario Outline: Add multiple products to cart
      Given I am a logged in user
      And my shopping cart is empty
      When I add <num-products> products to my shopping cart
      Then my shopping cart should contain <num-products> items
      Examples:
        | num-products |
        | 3            |
        | 5            |
        | 10           |

  Scenario: Removing a product from cart
        Given I am a logged in user
        And my shopping cart has 2 products
        When I remove the first product in my cart
        Then my shopping cart should contain 1 item
package test.cucumber.stepdefs;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobjects.AgendaPage;

import static org.junit.Assert.*;

public class AgendaStepDefs {

    AgendaPage page;
    WebDriver browser;
    String validEmail = "email@gmail.com";
    String validPassword = "AddPasshere";

    @Given("^I am a user on the Agenda website$")
    public void iAmUsingTheAgendaWebsite() throws Throwable {

        page = new AgendaPage(browser);
        page.get();
    }

    @Given("^I am a logged in user$")
    public void i_am_a_loggedin_user() throws Throwable {

        page = new AgendaPage(browser);
        page.get();
        page.login(validEmail,validPassword);
    }

    @Given("^And my shopping cart is empty$")
    public void my_cart_is_empty() throws Throwable {

        // check if cart is empty
    }

    @When("^I log in with valid credentials$")
    public void i_login_valid() throws Exception {
       page.login(validEmail,validPassword);
    }

    @When("I search for a product {string} ")
    public void i_search_product(String searchWord) throws Exception {
        page.search(searchWord);
    }

    @When("I select the first product in the line")
    public void i_select_the_first_product() {

        //not implemented due to issues when interacting with system. Details in Documentation
    }



    @When("I choose to buy the product")
    public void i_chose_to_buy_product() {

        //not implemented due to issues when interacting with system. Details in Documentation
    }


    @When("^I log in with invalid credentials$")
    public void i_login_invalid() throws Exception {
        page.login("bad@gmail.com","fakePass");
    }

    @When("I add {int} products to my shopping cart")
    public void i_add_num_products_products_to_my_shopping_cart(int itemNo) {
        //not implemented
    }

    @When("I remove the first product in my cart")
    public void i_remove_the_first_product_in_my_cart() {
        //not implemented
    }

    @Then("^I should be logged in$")
    public void should_be_logged_in() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(page.isLoggedIn());
    }

    @Then("^I should not be logged in$")
    public void should_not_be_logged_in() throws Exception {
        assertTrue(page.isLoggedInINVALD());
    }

    @Then("Then my shopping cart should contain {int} item")
    public void i_have_number_item(int quantity) {

        //not implemented due to issues when interacting with system. Details in Documentation
    }

    @Then("I should see the product details")
    public void i_see_product_details() {

        //not implemented
    }

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\larab\\OneDrive\\Documents\\chromedriver_win32\\chromedriver.exe");
        browser = new ChromeDriver();
    }

    @After
    public void teardown() {
        browser.quit();
    }

}

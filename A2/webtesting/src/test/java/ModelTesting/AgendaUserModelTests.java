package ModelTesting;

import ModelTesting.ModelAgendaUserStates.UserStates;

import nz.ac.waikato.modeljunit.*;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobjects.AgendaPage;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class AgendaUserModelTests implements FsmModel {


    WebDriver browser;

    private AgendaPage systemUnderTest = new AgendaPage(browser);

    private UserStates modelTransaction = UserStates.LOGGED_OUT;
    private boolean logged_in = false, logged_out = true, welcome = false, list = false, details = false, cart =false, checked_out = false;

    String validEmail = "laratestpage@gmail.com";
    String validPassword = "Testing123";

    public UserStates getState() {
        return modelTransaction;
    }


    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\larab\\OneDrive\\Documents\\chromedriver_win32\\chromedriver.exe");
        browser = new ChromeDriver();
    }

    public void reset(boolean b) {

        if (b) {
            systemUnderTest = new AgendaPage(browser);
        }
        modelTransaction = UserStates.LOGGED_OUT;
        logged_in = false;
        logged_out = true;
        welcome = false;
        list = false;
        details=false;
        cart =false;
        checked_out=false;
    }

    public boolean loginGuard() {
        return getState().equals(UserStates.LOGGED_OUT);
    }
    public @Action void login() {
        //updating the SuT
        systemUnderTest.login(validEmail, validPassword);

        //updating model
        modelTransaction = UserStates.LOGGED_IN;
        logged_in = true;
        logged_out= false;

        assertEquals(logged_in, systemUnderTest.isLoggedIn());
    }

    public boolean logoutGuard() {
        return getState().equals(UserStates.LOGGED_IN);
    }

    public @Action void logout() {
        //updating the SuT
        systemUnderTest.logout();

        //updating model
        modelTransaction = UserStates.LOGGED_OUT;
        logged_out = true;
        logged_in= false;

        assertEquals(logged_out, systemUnderTest.isLoggedout());

    }

    public boolean searchGuard() {
        return (getState().equals(UserStates.LOGGED_IN)
                ||getState().equals(UserStates.VIEWING_WELCOME)
                ||getState().equals(UserStates.VIEWING_LIST)
                ||getState().equals(UserStates.VIEWING_DETAILS)
                ||getState().equals(UserStates.VIEWING_CART));    }

    public @Action void search() {
        //updating the SuT
        systemUnderTest.search("item");

        //updating model
        modelTransaction = UserStates.VIEWING_LIST;
        list = true;

        assertEquals(list, systemUnderTest.isViewingItems());
    }

    public boolean selectGuard() {
        return getState().equals(UserStates.VIEWING_LIST);
    }

    public @Action void select() {
        //updating the SuT
        systemUnderTest.select();

        //updating model
        modelTransaction = UserStates.VIEWING_DETAILS;
        details = true;
        list=false;

        assertEquals(details, systemUnderTest.isSelected());
    }
    public boolean addGuard() {
        return getState().equals(UserStates.VIEWING_DETAILS);
    }

    public @Action void add() {
        //updating the SuT
        systemUnderTest.add();

        //updating model
        modelTransaction = UserStates.VIEWING_CART;
        cart = true;

        assertEquals(cart, systemUnderTest.isInCart());
    }
    public boolean removeGuard() {
        return getState().equals(UserStates.VIEWING_CART);
    }

    public @Action void removeTransaction() {
        //updating the SuT
        systemUnderTest.remove();

        //updating model
        modelTransaction = UserStates.VIEWING_CART;
        cart = true;

        assertEquals(cart, systemUnderTest.isInCart());
    }
    public boolean checkoutGuard() {
        return getState().equals(UserStates.VIEWING_CART);
    }

    public @Action void checkout() {
        //updating the SuT
        systemUnderTest.checkout();

        //updating model
        modelTransaction = UserStates.CHECKEDOUT;
        checked_out = true;

        assertEquals(checked_out, systemUnderTest.isCheckedout());
    }



    //Test runner
    @Test
    public void AgendaUserModelRunner() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\larab\\OneDrive\\Documents\\chromedriver_win32\\chromedriver.exe");

        final GreedyTester tester = new GreedyTester(new AgendaUserModelTests()); //Creates a test generator that can generate random walks. A greedy random walk gives preference to transitions that have never been taken before. Once all transitions out of a state have been taken, it behaves the same as a random walk.
        tester.setRandom(new Random()); //Allows for a random path each time the model is run.
        tester.buildGraph(); //Builds a model of our FSM to ensure that the coverage metrics are correct.
        tester.addListener(new StopOnFailureListener()); //This listener forces the test class to stop running as soon as a failure is encountered in the model.
        tester.addListener("verbose"); //This gives you printed statements of the transitions being performed along with the source and destination states.
        tester.addCoverageMetric(new TransitionPairCoverage()); //Records the transition pair coverage i.e. the number of paired transitions traversed during the execution of the test.
        tester.addCoverageMetric(new StateCoverage()); //Records the state coverage i.e. the number of states which have been visited during the execution of the test.
        tester.addCoverageMetric(new ActionCoverage()); //Records the number of @Action methods which have ben executed during the execution of the test.
        tester.generate(500); //Generates 500 transitions
        tester.printCoverage(); //Prints the coverage metrics specified above.
    }
}

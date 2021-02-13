package test.edu.uom.cps3222;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobjects.AgendaPage;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


public class AgendaTests {

    WebDriver driver;

    String validEmail = "laratestpage@gmail.com";
    String validPassword = "Testing123";

    String invalidEmail = "test@gmail.com";
    String invalidPassword = "12345678";


    public void sleep(int seconds) {
        try {
            Thread.sleep(seconds*100);
        } catch (Exception e) {}
    }

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\larab\\OneDrive\\Documents\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() {

        driver.quit();
    }

    @Test
    public void LoginSuccess() {

        AgendaPage page = new AgendaPage(driver);
        page.get();
        page.login(validEmail, validPassword);
        assertTrue(page.isLoggedIn());
    }

    @Test
    public void search() {

        AgendaPage page = new AgendaPage(driver);
        page.get();
        page.login(validEmail,validPassword);
        page.search("book");
        assertTrue(page.isLoggedIn());
    }

    @Test
    public void testSearchForMaltaInGoogle() {

        //Visit google
        driver.get("http://www.google.com");

        //Search for Malta
        driver.findElement(By.name("q")).sendKeys("Malta");
        driver.findElement(By.name("q")).submit();
        sleep(3);

        //Find stats component and assert number of links
        WebElement statsElement = driver.findElement(By.id("resultStats"));
        String statsText = statsElement.getText();

        assertTrue(statsText.indexOf("1,000,000") >= 0);
    }


//    @Test
//    public void testAddition() {
//
//        //Setup
//        AgendaPageObject calc = new AgendaPageObject(driver);
//        calc.get();
//
//        //Exercise
//        calc.enterKeys("5+2=");
//
//        //Verify
//        assertEquals("7", calc.getDisplayText());
//
//    }
//
//
//    @Test
//    public void testMultiplication() {
//        //Setup
//        AgendaPageObject calc = new AgendaPageObject(driver);
//        calc.get();
//
//        //Exercise
//        calc.enterKeys("158x22=");
//
//        //Verify
//        assertEquals("3476", calc.getDisplayText());
//    }
//
//    @Test
//    public void testDivideByZero() {
//        //Setup
//        AgendaPageObject calc = new AgendaPageObject(driver);
//        calc.get();
//
//        //Exercise
//        calc.enterKeys("5/0=");
//
//        //Verify
//        assertEquals("Infinity", calc.getDisplayText());
//    }




}

package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AgendaPage {

    WebDriver driver;



    public void sleep(int seconds) {
        try {
            Thread.sleep(seconds*1000);
        } catch (Exception e) {}
    }

    public AgendaPage(WebDriver driver) {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\larab\\OneDrive\\Documents\\chromedriver_win32\\chromedriver.exe");

        this.driver = driver;
    }

    public void get() {
        driver.get("https://ps.agendatextbooks.com/en/login.php");
    }

    public void login(String email, String password) {

       // sleep(2); //for demo purposes
        driver.findElement(By.name("email")).sendKeys(email);
        //sleep(1); //for demo purposes
        driver.findElement(By.name("password")).sendKeys(password+"\n");
       // sleep(1);

    }

    public boolean isLoggedIn() {
        String message= driver.findElement(By.name("og:description")).getAttribute("content");
        if(message.contains("Welcome")){
            return true;
        }
        return false;
    }

    public boolean isLoggedInINVALD() {

        if(driver.findElements( By.name("email") ).size() != 0){ //still on login page
            return true;
        }
        return false;
    }

    public void search(String item) {

        // sleep(2); //for demo purposes
        //driver.findElement(By.id("search-mobile")).sendKeys(item+"\n");
        //sleep(1); //for demo purposes
        //driver.findElement(By.id("searchsubmit")).submit();


    }


    public void logout() {
        //not implemented
        //should perform task of loggin out
    }

    public boolean isLoggedout() {
        return  true;
        //not implemeted. Should be resposible for logging out
    }

    public boolean isViewingItems() {
        return true;
        //not implemented but responsible for checking if the items are being shown
    }

    public boolean isSelected() {
        return true;
        //not implemented but responsile for seeing if the item was selcted ans so the details are being viewed
    }

    public void select() {
        //would select the item from the list and view the details
    }

    public void add() {
        //would add selcted item
    }

    public boolean isInCart() {
        return true;
        //would checkif the user is in the cart
    }

    public void remove() {
//would be responsible for removing an item from the cart.
    }

    public void checkout() {
        //wpuld be responsible for checking out cart
    }

    public boolean isCheckedout() {
        return true;
        //would check if user checked out
    }
}

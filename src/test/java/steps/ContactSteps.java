package steps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ContactSteps {
    WebDriver driver;

    @Given("User is on the SLIB home page")
    public void user_is_on_the_slib_home_page() {
        

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.get("https://www.slib.com");
        System.out.println("Navigating to SLIB home page");
    }

    @When("User clicks on the {string} link")
    public void user_clicks_on_the_link(String linkText) {
        WebElement contactLink = driver.findElement(By.xpath("/html/body/div[1]/header/div[2]/div/ul[1]/li[5]/a")); 
        contactLink.click();
        System.out.println("Clicking on the 'Contact' link");
    }

    @Then("User should be on the contact page")
    public void user_should_be_on_the_contact_page() {
        String expectedUrl = "https://www.slib.com/contact-us/"; // Vérifiez l'URL correcte
        String currentUrl = driver.getCurrentUrl();
        assert currentUrl.equals(expectedUrl) : "Expected URL: " + expectedUrl + ", but was: " + currentUrl;

        System.out.println("User is on the contact page");
        driver.quit();
    }
}

package q.example.q;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class test2 {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:/Users/fatim/Desktop/test/chromedriver-win64/chromedriver.exe");
        
        WebDriver driver = new ChromeDriver();

        try {
            driver.manage().window().maximize();
            
            // الانتقال إلى موقع saucedemo
            driver.get("https://www.saucedemo.com/");
            
            WebElement usernameField = driver.findElement(By.id("user-name"));
            WebElement passwordField = driver.findElement(By.id("password"));
            
            usernameField.sendKeys("standard_user");
            passwordField.sendKeys("secret_sauce"); 
            
            WebElement loginButton = driver.findElement(By.id("login-button"));
            loginButton.click();
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement firstProductAddButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".inventory_item button")));
            firstProductAddButton.click();
            
            WebElement cartButton = driver.findElement(By.className("shopping_cart_link"));
            cartButton.click();
            
            WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout")));
            checkoutButton.click();
            
            WebElement checkoutTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));
            String expectedTitle = "Checkout: Your Information";
            String actualTitle = checkoutTitle.getText();
            
            if (!expectedTitle.equals(actualTitle)) {
                System.out.println("Test Failed: Checkout page title did not match!");
            } else {
                System.out.println("Test Passed: Checkout page title matched!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // driver.quit();
        }
    }
}

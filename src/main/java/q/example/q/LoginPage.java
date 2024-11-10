package q.example.q;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public class LoginPage {
   
    private WebDriver driver;
    private WebDriverWait wait;

    private By usernameField = By.name("uname");
    private By passwordField = By.name("psw");
    private By loginButton = By.xpath("//*[@id=\"root\"]/div/div/div/form/button");
    private By errorDiv = By.id("dd");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public String getErrorMessage() {
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(errorDiv));
        return error.getText();
    }

    public boolean isRedirectedToFeed() {
        wait.until(ExpectedConditions.urlToBe("http://localhost:3000/feed"));
        return driver.getCurrentUrl().contains("/feed");
    
} 
}

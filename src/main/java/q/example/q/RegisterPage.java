package q.example.q;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage {
    private WebDriver driver;
    
    private By usernameField = By.name("uname");
    private By emailField = By.name("email");
    private By passwordField = By.name("psw");
    private By registerButton = By.cssSelector(".btn");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickRegisterButton() {
        driver.findElement(registerButton).click();
    }

    public boolean isRedirectedToFeed() {
        return driver.getCurrentUrl().equals("http://localhost:3000/feed");
    }
}

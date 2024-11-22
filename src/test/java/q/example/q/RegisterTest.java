package q.example.q;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterTest {

    private WebDriver driver;
    private RegisterPage registerPage;
    private static final String FILE_PATH = "src/main/java/q/example/q/registerData.csv";

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/fatim/Desktop/chromedriver-win64/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:3000/"); 
        registerPage = new RegisterPage(driver);
    }

    @Test
    void testUsernameAlreadyTaken() throws IOException, CsvException {
        List<String[]> registerData = DataProvider.getLoginData(FILE_PATH);
        
        String username = registerData.get(0)[0]; 
        String email = registerData.get(0)[1];
        String password = registerData.get(0)[2];

        driver.findElement(By.cssSelector(".register")).click();

        registerPage.enterUsername(username);
        registerPage.enterEmail(email);
        registerPage.enterPassword(password);
        registerPage.clickRegisterButton();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Username is already taken!')]")));

        String errorMessage = driver.findElement(By.xpath("//*[contains(text(),'Username is already taken!')]")).getText();
        assertEquals("Error: Username is already taken!", errorMessage, "The error message did not match!");
    }

    @Test
    void testRegisterNewUserWithUniqueUsername() throws IOException, CsvException {
        List<String[]> registerData = DataProvider.getLoginData(FILE_PATH);
        
        String username = registerData.get(0)[0]; 
        String email = registerData.get(0)[1];
        String password = registerData.get(0)[2];

        Random random = new Random();
        int randomNumber = random.nextInt(1000);
        String uniqueUsername = username + randomNumber; 
        driver.findElement(By.cssSelector(".register")).click();

        registerPage.enterUsername(uniqueUsername);
        String uniqueEmail = email.substring(0, email.indexOf('@')) + randomNumber + email.substring(email.indexOf('@'));

        registerPage.enterEmail(uniqueEmail);
        registerPage.enterPassword(password);
        registerPage.clickRegisterButton();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe("http://localhost:3000/feed"));

        assertTrue(registerPage.isRedirectedToFeed(), "Did not redirect to the feed page after registration!");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

package q.example.q;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QApplicationTests {

    private WebDriver driver;
    private LoginPage loginPage;
    private static final String FILE_PATH = "src/main/java/q/example/q/loginData.csv"; 

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver-win64/chromedriver-win64/chromedriver.exe/");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:3000/");
        loginPage = new LoginPage(driver);
    }

    @Test
    void testInvalidLoginShowsErrorMessage() throws IOException, CsvException {
        List<String[]> loginData = DataProvider.getLoginData(FILE_PATH);
        
        String invalidUsername = loginData.get(1)[0];
        String invalidPassword = loginData.get(1)[1];

        loginPage.enterUsername(invalidUsername);
        loginPage.enterPassword(invalidPassword);
        loginPage.clickLoginButton();

        String expectedErrorMessage = "Error: Username or Password is incorrect";
        assertEquals(expectedErrorMessage, loginPage.getErrorMessage(), "Error message did not match!");
    }

    @Test
    void testSuccessfulLoginRedirectsToFeed() throws IOException, CsvException {
        List<String[]> loginData = DataProvider.getLoginData(FILE_PATH);

        String validUsername = loginData.get(0)[0];
        String validPassword = loginData.get(0)[1];

        loginPage.enterUsername(validUsername);
        loginPage.enterPassword(validPassword);
        loginPage.clickLoginButton();

        assertTrue(loginPage.isRedirectedToFeed(), "Did not redirect to the feed page!");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

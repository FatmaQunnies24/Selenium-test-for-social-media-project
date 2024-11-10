package q.example.q;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QApplicationTests {

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/fatim/Desktop/year4-1/test/test/chromedriver-win64/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:3000/");
        loginPage = new LoginPage(driver);
    }

    @Test
    void testInvalidLoginShowsErrorMessage() {
        loginPage.enterUsername("FatmaQnnd");
        loginPage.enterPassword("123mai321");
        loginPage.clickLoginButton();

        String expectedErrorMessage = "Error: Username or Password is incorrect";
        assertEquals(expectedErrorMessage, loginPage.getErrorMessage(), "Error message did not match!");
    }

    @Test
    void testSuccessfulLoginRedirectsToFeed() {
        loginPage.enterUsername("FatmaQnn");
        loginPage.enterPassword("123mai321");
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

package q.example.q;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LikePageTest {

    private WebDriver driver;
    private LoginPage loginPage;

    private void setUp(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:/Users/fatim/Desktop/chromedriver-win64/chromedriver.exe");
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            System.setProperty("webdriver.edge.driver", "C:/Users/fatim/Desktop/msedgedriver.exe");
            driver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driver.manage().window().maximize();
        driver.get("http://localhost:3000/");
        loginPage = new LoginPage(driver);
    }

    private static By likesButton = By.xpath("//*[@id='root']/div[2]/div[1]/nav/div/ul/li[5]/a");
    private static By likesContainer = By.xpath("//*[@id='root']/div[2]/div[2]");

    private void navigateToLikesPage() {
        loginPage.enterUsername("fatma2");
        loginPage.enterPassword("123mai321");
        loginPage.clickLoginButton();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement likesPageButton = wait.until(ExpectedConditions.elementToBeClickable(likesButton));
        likesPageButton.click();
        wait.until(ExpectedConditions.urlToBe("http://localhost:3000/Likes"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "edge"})
    void testIfDislikeButtonIsPresent(String browser) throws InterruptedException {
        setUp(browser);

        navigateToLikesPage();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated(likesContainer));

        List<WebElement> likesList = container.findElements(By.xpath("//*[@id=\"root\"]/div[2]/div[2]"));
        WebElement dislikeButton = container.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div[2]"));

        assertTrue(likesList.size() > 0 && dislikeButton != null, "No Dislike button found or likes list is empty!");

        tearDown();
    }

    private void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

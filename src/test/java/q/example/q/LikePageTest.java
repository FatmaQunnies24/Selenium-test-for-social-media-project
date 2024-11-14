package q.example.q;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LikePageTest {

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

    private static By likesButton = By.xpath("//*[@id=\"root\"]/div[2]/div[1]/nav/div/ul/li[5]/a");

    private void navigateToLikesPage() {
        loginPage.enterUsername("fatma2");
        loginPage.enterPassword("123mai321");
        loginPage.clickLoginButton();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement likesPageButton = wait.until(ExpectedConditions.elementToBeClickable(likesButton));
        likesPageButton.click();
        wait.until(ExpectedConditions.urlToBe("http://localhost:3000/Likes"));
    }
// @RepeatedTest(10)
    @Test
    void testIfDislikeButtonIsPresent() throws InterruptedException {
        navigateToLikesPage();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement likesContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div[2]/div[1]/nav/div/ul/li[5]/a")));
        List<WebElement> likesList = likesContainer.findElements(By.xpath("//*[@id=\"root\"]/div[2]/div[2]"));
        Thread.sleep(5000); 
        WebElement dislikeButtons = likesContainer.findElement(By.xpath("//button[text()='Dislike']"));
        Thread.sleep(5000);
        assertTrue(likesList.size() > 0 &&dislikeButtons != null , "No Dislike button found on the page!");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

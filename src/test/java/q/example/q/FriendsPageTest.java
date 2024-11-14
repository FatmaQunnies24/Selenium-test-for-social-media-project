package q.example.q;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FriendsPageTest {

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

    private static By loginButton = By.xpath("//*[@id=\"root\"]/div[2]/div[1]/nav/div/ul/li[3]/a");

    private void navigateToFriendsPage() {
        loginPage.enterUsername("fatma2");
        loginPage.enterPassword("123mai321");
        loginPage.clickLoginButton();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement friendsPageButton = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        friendsPageButton.click();
        

        WebElement reqButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"Friends\"]/div/div[1]/button[2]")));
        reqButton.click();
        
        WebElement friButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"Friends\"]/div/div[1]/button[1]")));
        friButton.click();
        
        wait.until(ExpectedConditions.urlToBe("http://localhost:3000/Friends"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Friends")));
    }

    @Test
    void testIfFriendsArePresent() {
        navigateToFriendsPage();

        WebElement friendsContainer = driver.findElement(By.id("Friends"));
        List<WebElement> friendsList = friendsContainer.findElements(By.className("friend-item"));
        assertTrue(friendsList.size() > 0, "No friends found in the friends list!");
    }

    @Test
    void testFriendsCount() throws InterruptedException {
        navigateToFriendsPage();
Thread.sleep(3000);
        WebElement friendsContainer = driver.findElement(By.id("Friends"));
        WebElement friendsCountElement = friendsContainer.findElement(By.xpath(".//p[contains(text(), 'Number of friends:')]"));
        
        String friendsCountText = friendsCountElement.getText();
        
        List<WebElement> friendsList = friendsContainer.findElements(By.className("friend-item"));
        assertEquals(friendsList.size(), 7, "Friends count does not match the number of friend items.");
    }

    @Test
    void testRemoveFriend() {
        navigateToFriendsPage();

        WebElement friendsContainer = driver.findElement(By.id("Friends"));
        List<WebElement> friendsList = friendsContainer.findElements(By.className("friend-item"));
        int initialFriendCount = friendsList.size();

        if (initialFriendCount > 0) {
            WebElement firstFriendRemoveButton = friendsList.get(0).findElement(By.className("add-button"));
            firstFriendRemoveButton.click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.numberOfElementsToBe(By.className("friend-item"), initialFriendCount - 1));
            
            List<WebElement> updatedFriendsList = friendsContainer.findElements(By.className("friend-item"));
            assertEquals(initialFriendCount - 1, updatedFriendsList.size(), "Friend was not removed from the list.");
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

package q.example.q;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.time.Duration;

class ReelPageTests {

    private WebDriver driver;
    private LoginPage loginPage;
    private ReelPage reelPage;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "c:/Users/lenovo/OneDrive/Desktop/chromedriver-win64/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:3000/");
        loginPage = new LoginPage(driver);
        reelPage = new ReelPage(driver);
    }
@Tag("non-parallel")
    @Test
    void testIfReelsArePresent() {
        loginPage.enterUsername("fatma2");
        loginPage.enterPassword("123mai321");
        loginPage.clickLoginButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        WebElement reelButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div[2]/div[1]/nav/div/ul/li[2]/a")));
        reelButton.click();
            // wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
            // ReelPage.clickreelButton();
            // wait.until(ExpectedConditions.urlToBe("http://localhost:3000/Reel"));

                wait.until(ExpectedConditions.urlToBe("http://localhost:3000/Reel"));
            WebElement reelContainer = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Real")));
            assertTrue(reelContainer.findElements(By.className("post")).size() > 0, "No reels found!");
    }
    
    @Test
    void testCreateReel() {
        // تسجيل الدخول
        loginPage.enterUsername("fatma2");
        loginPage.enterPassword("123mai321");
        loginPage.clickLoginButton();
    
        // الانتظار للتأكد من تحميل الصفحة
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
    
        // النقر على زر "Reel"
        WebElement reelButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//*[@id=\"root\"]/div[2]/div[1]/nav/div/ul/li[2]/a")));
        reelButton.click();
    
        // الانتظار للتأكد من تغيير الرابط إلى صفحة "Reel"
        wait.until(ExpectedConditions.urlToBe("http://localhost:3000/Reel"));
    
        // إدخال محتوى الريل
        WebElement reelContentTextArea = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".postContent")));
        reelContentTextArea.sendKeys("This is a new reel for testing!");
    
        // رفع الفيديو
        WebElement uploadFileInput = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("input[type='file']")));
        uploadFileInput.sendKeys("c:/Users/lenovo/OneDrive/Desktop/WhatsApp Video 2024-11-15 at 20.09.18_9ff2d918.mp4");
    
        // النقر على زر إنشاء الريل
        WebElement createReelButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(),'Create Reel')]")));
        createReelButton.click();
    
        // التحقق من ظهور رسالة نجاح
        WebElement successToast = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[contains(text(), 'Reel created successfully')]")));
        assertTrue(successToast.isDisplayed(), "The success message was not displayed!");
    }
    
//@RepeatedTest(10)

@Tag("non-parallel")
@Test
void testLikeAndFavoriteReel() throws InterruptedException {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
    wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

    loginPage.enterUsername("fatma2");
    loginPage.enterPassword("123mai321");
    loginPage.clickLoginButton();
    WebElement reelButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div[2]/div[1]/nav/div/ul/li[2]/a")));
    reelButton.click();
    Thread.sleep(3000);  
    wait.until(ExpectedConditions.urlToBe("http://localhost:3000/Reel"));

    boolean isReelPresent = !driver.findElements(By.id("Real")).isEmpty(); 
      assumeTrue(isReelPresent, "Reel section not found, skipping test.");
    int initialLikeCount = Integer.parseInt(
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#Real > div:nth-child(2) > div.numberLikeComment > div:nth-child(1)")))
        .getText().trim().split(" ")[0]
    );
    WebElement reelContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Real")));
    Thread.sleep(3000); 
System.out.println(initialLikeCount);
    WebElement likeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='Real']//button[contains(@class, 'like-button')]")));
    Thread.sleep(2000);  
    boolean isLiked = likeButton.getText().contains("Dislike");

    if (isLiked) {
        System.out.println(initialLikeCount);
        
        likeButton.click();
        initialLikeCount--;
        Thread.sleep(3000); 
        WebElement updatedLikeCountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"Real\"]/div[2]/div[3]/div[1]")));
        String updatedLikeCountText = updatedLikeCountElement.getText();
        int updatedLikeCount = Integer.parseInt(updatedLikeCountText.split(" ")[0]);

        assertEquals(initialLikeCount, updatedLikeCount, "Like count did not decrease.");
    }
    System.out.println(initialLikeCount);

    likeButton.click();
    Thread.sleep(3000);  System.out.println(initialLikeCount);

    WebElement favoriteButton = driver.findElement(By.xpath("//*[@id=\"Real\"]/div[2]/div[4]/div[1]/div/div/button[2]"));
    favoriteButton.click();
    Thread.sleep(3000); 
    WebElement updatedLikeCountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"Real\"]/div[2]/div[3]/div[1]")));
    String updatedLikeCountText = updatedLikeCountElement.getText();
    int updatedLikeCount = Integer.parseInt(updatedLikeCountText.split(" ")[0]);
    System.out.println(initialLikeCount);

    assertEquals(initialLikeCount + 1, updatedLikeCount, "Like count did not increase."+updatedLikeCount+""+initialLikeCount);
}






@Tag("non-parallel")
@Test
//@RepeatedTest(10)
void testAddCommentToReel() throws InterruptedException {
    // Setup WebDriverWait for dynamic waits
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
    wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
  
    // Log in
    loginPage.enterUsername("fatma2");
    loginPage.enterPassword("123mai321");
    loginPage.clickLoginButton();

    // Wait before navigating to the reel page
    Thread.sleep(1000);  // Wait for 1 second

    WebElement reelButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div[2]/div[1]/nav/div/ul/li[2]/a")));
    reelButton.click();
    wait.until(ExpectedConditions.urlToBe("http://localhost:3000/Reel"));

    // Wait before continuing to check the feed
    Thread.sleep(1000);  // Wait for 1 second
    boolean isReelPresent = !driver.findElements(By.id("Real")).isEmpty(); // أو XPath/ CSS صحيح للتحقق من وجود العنصر
    assumeTrue(isReelPresent, "Reel section not found, skipping test.");
    // Wait for the feed container and the first post
    WebElement feedContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Real"))); // أو XPath/ CSS الصحيح
    WebElement post = feedContainer.findElement(By.xpath("//*[@id=\"Real\"]/div[2]"));

    // Get the height of the feed container
    int feedContainerHeight = feedContainer.getSize().getHeight();

    // Scroll to 50% of the feed container height
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", feedContainer); // Scroll to the feed container
    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, arguments[0].getBoundingClientRect().height / 2);", feedContainer); // Scroll by 50% of the feed container height

    // Wait before moving to the comment input
    Thread.sleep(1000);  // Wait for 1 second

    // Wait for the comment input field to be visible
    WebElement commentInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".addComment input")));
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", commentInput);  // Scroll to the comment input

    // Wait before reading the initial comment count
    Thread.sleep(1000);  // Wait for 1 second

    // Wait for the initial comment count and ensure it's visible before reading
    WebElement commentsCountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='numberLikeComment']/div[2]")));
    String initialCommentsText = commentsCountElement.getText();
    int initialCommentCount = Integer.parseInt(initialCommentsText.split(" ")[0]);

    // Add a comment
    commentInput.sendKeys("This is a test comment.");
    WebElement sendButton = driver.findElement(By.cssSelector(".send-comment"));
    sendButton.click();

    // Wait before checking the updated comment count
    Thread.sleep(2000);  // Wait for 2 seconds after sending the comment

    // Wait for the comment count to be updated (improved dynamic wait)
    wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='numberLikeComment']/div[2]"))));

    // Wait before verifying the updated comment count
    Thread.sleep(1000);  // Wait for 1 second

    // Wait a bit for the page to reflect the updated comment count
    WebElement updatedCommentsCountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='numberLikeComment']/div[2]")));
    String updatedCommentsText = updatedCommentsCountElement.getText();
    int updatedCommentCount = Integer.parseInt(updatedCommentsText.split(" ")[0]);

    // Assert that the comment count increased by 1
    assertEquals(initialCommentCount + 1, updatedCommentCount, "Comment count did not increase.");
}





    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
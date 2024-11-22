package q.example.q;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.TimeoutException;
class FeedPageTests{

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/fatim/Desktop/chromedriver-win64/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:3000/");
        loginPage = new LoginPage(driver);
    }

 


    @Test
    void testIfPostsArePresent() {
        loginPage.enterUsername("FatmaQnn");
        loginPage.enterPassword("123mai321");
        loginPage.clickLoginButton();
    
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        try {
            WebElement feedContainer = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Feed")));
    
            assertTrue(feedContainer.findElements(By.className("post")).size() > 0, "No posts found!");
        } catch (TimeoutException e) {
            fail("The feed container was not found in time.");
        }}




    @Test
    void testCreatePost() {
        loginPage.enterUsername("FatmaQnn");
            loginPage.enterPassword("123mai321");
            loginPage.clickLoginButton();
     
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement postContentTextArea = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".postContent")));
        
        postContentTextArea.sendKeys("This is a new post for testing!");

        WebElement createPostButton = driver.findElement(By.xpath("//button[contains(text(),'Create Post')]"));
        createPostButton.click();

        WebElement feedContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Feed")));
        assertTrue(feedContainer.findElements(By.className("post")).size() > 0, "Post was not created!");
    }







 //@RepeatedTest(10)  
    @Test
void testLikeAndFavorite() {
    loginPage.enterUsername("FatmaQnn");
    loginPage.enterPassword("123mai321");
    loginPage.clickLoginButton();
    
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100)); 
    WebElement feedContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Feed")));
    try {
        Thread.sleep(3000); 
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.scrollBy(0, window.innerHeight*3);");
    
    try {
        Thread.sleep(3000); 
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    WebElement post = feedContainer.findElement(By.xpath("//*[@id=\"Feed\"]/div[2]"));
    wait.until(ExpectedConditions.visibilityOf(post));
    
    try {
        Thread.sleep(3000); 
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    WebElement likeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='Feed']//button[contains(@class, 'like-button')]")));
    boolean isLiked = likeButton.getText().contains("Dislike");
    
    if (isLiked) {
        WebElement initialLikeCountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"Feed\"]/div[2]/div[3]/div[1]")));
        String initialLikeText = initialLikeCountElement.getText().trim();
        String[] words = initialLikeText.split(" ");
        int initialLikeCount = Integer.parseInt(words[0]);
        
        likeButton.click();
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement updatedLikeCountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"Feed\"]/div[2]/div[3]/div[1]")));
        String updatedLikeCountText = updatedLikeCountElement.getText();
        int updatedLikeCount = Integer.parseInt(updatedLikeCountText.split(" ")[0]);
        
        assertEquals(initialLikeCount - 1, updatedLikeCount, "Like count did not decrease.");
    } 
    
    WebElement initialLikeCountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"Feed\"]/div[2]/div[3]/div[1]")));
    String initialLikeText = initialLikeCountElement.getText().trim();
    int initialLikeCount = Integer.parseInt(initialLikeText.split(" ")[0]);
    
    likeButton.click();
    
    try {
        Thread.sleep(3000); 
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"Feed\"]/div[2]/div[4]/div[1]/div/div/button[2]")));
    WebElement favoriteButton = driver.findElement(By.xpath("//*[@id=\"Feed\"]/div[2]/div[4]/div[1]/div/div/button[2]"));
    favoriteButton.click();
    
    try {
        Thread.sleep(3000); 
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    
    wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id=\"Feed\"]/div[2]/div[3]/div[1]"), String.valueOf(initialLikeCount + 1)));
    
    WebElement likeCountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"Feed\"]/div[2]/div[3]/div[1]")));
    String likeCountText = likeCountElement.getText();
    int likeCount = Integer.parseInt(likeCountText.split(" ")[0]);
    
    assertEquals(initialLikeCount + 1, likeCount, "Like count did not increase.");
}



 //@RepeatedTest(10)
@Test
void testAddComment() {
    loginPage.enterUsername("FatmaQnn");
    loginPage.enterPassword("123mai321");
    loginPage.clickLoginButton();
    
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    WebElement feedContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Feed")));
    
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

    WebElement post = feedContainer.findElement(By.xpath("//*[@id='Feed']/div[1]"));
    
    WebElement commentInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".addComment input")));
    try {
        Thread.sleep(3000); 
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    WebElement commentsCountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='numberLikeComment']/div[2]")));
    
    String initialCommentsText = commentsCountElement.getText();
    int initialCommentCount = Integer.parseInt(initialCommentsText.split(" ")[0]); 
    
    commentInput.sendKeys("This is a test comment.");
    
    WebElement sendButton = driver.findElement(By.cssSelector(".send-comment"));
    sendButton.click();
    
    WebElement updatedCommentsCountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='numberLikeComment']/div[2]")));
    try {
                Thread.sleep(3000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    String updatedCommentsText = updatedCommentsCountElement.getText();
    int updatedCommentCount = Integer.parseInt(updatedCommentsText.split(" ")[0]);
    
    assertEquals(initialCommentCount + 1, updatedCommentCount, "Comment count did not increase.");
}

//@RepeatedTest(3)
@Test
void testSharePostWithComment() {
    loginPage.enterUsername("FatmaQnn");
    loginPage.enterPassword("123mai321");
    loginPage.clickLoginButton();
    
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    WebElement feedContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Feed")));
    
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

    WebElement post = feedContainer.findElement(By.xpath("//*[@id='Feed']/div[1]"));
    
    WebElement shareButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"Feed\"]/div[2]/div[4]/div[3]/button")));
    
    shareButton.click();
    
    WebElement sharePopper = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("share-popper")));
    
    WebElement commentTextArea = sharePopper.findElement(By.cssSelector(".share-popper-content textarea"));
    commentTextArea.sendKeys("hi hi"); 
    WebElement postShareButton = sharePopper.findElement(By.xpath("//button[contains(text(), 'Post Share')]"));
    postShareButton.click();
    
    WebElement toastify = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("Toastify")));
    
    assertNotNull(toastify, "Toast notification did not appear.");}




    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

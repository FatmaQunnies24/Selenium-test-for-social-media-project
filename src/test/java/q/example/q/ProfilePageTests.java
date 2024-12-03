package q.example.q;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

class ProfilePageTests {

    private WebDriver driver;
    private static By loginButton = By.xpath("//*[@id=\"root\"]/div[2]/div[1]/nav/div/ul/li[6]/a");
    private LoginPage loginPage; 
    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "c:/Users/lenovo/OneDrive/Desktop/chromedriver-win64/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:3000/");

       
        loginPage = new LoginPage(driver); 
        loginPage.enterUsername("fatma2");
        loginPage.enterPassword("123mai321");
        loginPage.clickLoginButton();
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement friendsPageButton = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        friendsPageButton.click();
        
        wait.until(ExpectedConditions.urlToBe("http://localhost:3000/profile"));
    }

    @Test
    void testProfilePageElements() {
        WebElement centeredImage = driver.findElement(By.className("centered-image"));
        assertNotNull(centeredImage, "Centered image is not present");

        WebElement backgroundImage = driver.findElement(By.className("background"));
        assertNotNull(backgroundImage, "Background image is not present");

        WebElement username = driver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div[2]/div[2]/div[1]/h1"));
        assertNotNull(username, "Username is not present");
        assertEquals("fatma2", username.getText(), "Username is not correct");

        WebElement settingsIcon = driver.findElement(By.className("settings-icon"));
        assertNotNull(settingsIcon, "Settings icon is not present");

        settingsIcon.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe("http://localhost:3000/Setting"));
        
        assertEquals("http://localhost:3000/Setting", driver.getCurrentUrl(), "URL after clicking settings is incorrect");
    }
    
    @Test
    void testScrollAndCheckPosts() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, document.body.scrollHeight)");
    
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("post")));
    
        List<WebElement> posts = driver.findElements(By.className("post"));
        assertTrue(posts.size() > 0, "No posts found after scrolling");
    }
    

  
    @Test
    void testSharesButtonAndCheckPosts() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    
        WebElement shareButton = driver.findElement(By.xpath("//button[normalize-space()='Shares']"));
        shareButton.click();
    
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("shares")));
    
        List<WebElement> posts = driver.findElements(By.xpath("//div[@class='shares']//div[@class='post']"));
        assertTrue(posts.size() > 0, "No posts found in the 'shares' div");
    
        WebElement firstPost = posts.get(0);
        WebElement imageShare = firstPost.findElement(By.className("imageShare"));
        WebElement content = firstPost.findElement(By.className("content"));
    
        assertTrue(imageShare.isDisplayed(), "Image is not displayed");
        assertTrue(content.isDisplayed(), "Content is not displayed");
        assertTrue(content.getText().contains("hi hi"), "Content does not match the expected text");
    }
    
    @Test
    void testInfoButtonAndCheckUserInfo() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    
        WebElement shareButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div[2]/div[3]/button[3]"));
        shareButton.click();
    
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("infoo")));
        shareButton.click();
    
        WebElement infoDiv = driver.findElement(By.className("infoo"));
    
        WebElement username = infoDiv.findElement(By.xpath(".//p[strong[text()='Username:']]"));
        WebElement fullName = infoDiv.findElement(By.xpath(".//p[strong[text()='Full Name:']]"));
        WebElement bio = infoDiv.findElement(By.xpath(".//p[strong[text()='Bio:']]"));
        WebElement email = infoDiv.findElement(By.xpath(".//p[strong[text()='Email:']]"));
        WebElement location = infoDiv.findElement(By.xpath(".//p[strong[text()='Location:']]"));
        WebElement birthdate = infoDiv.findElement(By.xpath(".//p[strong[text()='Birthdate:']]"));
        WebElement gender = infoDiv.findElement(By.xpath(".//p[strong[text()='Gender:']]"));
        WebElement mobile = infoDiv.findElement(By.xpath(".//p[strong[text()='Mobile:']]"));
        WebElement age = infoDiv.findElement(By.xpath(".//p[strong[text()='Age:']]"));
    
        assertEquals("fatma2", username.getText().split(":")[1].trim(), "Username does not match");
        assertEquals("Fatma Nasser", fullName.getText().split(":")[1].trim(), "Full Name does not match");
        assertEquals("powerful", bio.getText().split(":")[1].trim(), "Bio does not match");
        assertEquals("sssss@gmail.com", email.getText().split(":")[1].trim(), "Email does not match");
        assertEquals("Bethlehem", location.getText().split(":")[1].trim(), "Location does not match");
        assertEquals("2024-05-09", birthdate.getText().split(":")[1].trim(), "Birthdate does not match");
        assertEquals("FEMALE", gender.getText().split(":")[1].trim(), "Gender does not match");
        assertEquals("02585256852", mobile.getText().split(":")[1].trim(), "Mobile does not match");
        assertEquals("0", age.getText().split(":")[1].trim(), "Age does not match");
    
     
    }
    

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

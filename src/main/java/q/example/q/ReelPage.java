package q.example.q;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReelPage {
    private static WebDriver driver;
    
        private By postContentTextarea = By.className("postContent");
        private By createReelButton = By.xpath("//button[contains(text(),'Create Reel')]");
        private By uploadVideoButton = By.id("upload-button");
        private By existingReels = By.className("post");
        private static By loginButton = By.xpath("//*[@id=\"root\"]/div[2]/div[1]/nav/div/ul/li[2]/a");
        
            public ReelPage(WebDriver driver) {
                this.driver = driver;
            }
    
            public static void clickreelButton() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    WebElement reelButton = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
    reelButton.click();
}

    public boolean isReelsExist() {
        return driver.findElements(existingReels).size() > 0;
    }

    public void writeReelContent(String content) {
        driver.findElement(postContentTextarea).sendKeys(content);
    }

    public void clickCreateReelButton() {
        driver.findElement(createReelButton).click();
    }

    public void uploadVideo(String videoPath) {
        WebElement uploadButton = driver.findElement(uploadVideoButton);
        uploadButton.sendKeys(videoPath); 
    }

    public void createNewReel(String content, String videoPath) {
        writeReelContent(content);
        uploadVideo(videoPath);
        clickCreateReelButton();
    }

}

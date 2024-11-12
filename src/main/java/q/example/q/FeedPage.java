package q.example.q;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FeedPage {
    private WebDriver driver;

    private By postContentTextarea = By.className("postContent");
    private By createPostButton = By.xpath("//button[text()='Create Post']");
    private By existingPosts = By.className("post");

    public FeedPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isPostsExist() {
        return driver.findElements(existingPosts).size() > 0;
    }

    public void writePostContent(String content) {
        driver.findElement(postContentTextarea).sendKeys(content);
    }

    public void clickCreatePostButton() {
        driver.findElement(createPostButton).click();
    }

    public void createNewPost(String content) {
        writePostContent(content);
        clickCreatePostButton();
    }
}

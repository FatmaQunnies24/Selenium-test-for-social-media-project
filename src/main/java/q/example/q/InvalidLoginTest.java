package q.example.q;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
// import org.junit.Assert;
public class InvalidLoginTest {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:/Users/fatim/Desktop/test/chromedriver-win64/chromedriver.exe");
        
        WebDriver driver = new ChromeDriver();

        try {
            // تكبير النافذة
            driver.manage().window().maximize();
            
            // الانتقال إلى موقع saucedemo
            driver.get("https://www.saucedemo.com/");
            
            // العثور على حقول اسم المستخدم وكلمة المرور
            WebElement usernameField = driver.findElement(By.id("user-name"));
            WebElement passwordField = driver.findElement(By.id("password"));
            
            // إدخال معلومات غير صحيحة
            usernameField.sendKeys("wrong_user");
            passwordField.sendKeys("wrong_password");
            
            // الضغط على زر تسجيل الدخول
            WebElement loginButton = driver.findElement(By.id("login-button"));
            loginButton.click();
            
            // انتظار ظهور رسالة الخطأ
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
            
            // التحقق من ظهور رسالة الخطأ
            String expectedErrorMessage = "Epic sadface: Username and password do not match any user in this service";
            // Assert.assertEquals(expectedErrorMessage, errorMessage.getText());
            String actualErrorMessage = errorMessage.getText();
if (!expectedErrorMessage.equals(actualErrorMessage)) {
    System.out.println("Test Failed: Error message did not match!");
} else {
    System.out.println("Test Passed: Error message matched!");
}

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // إغلاق المتصفح بعد الانتهاء
            driver.quit();
        }
    }
}

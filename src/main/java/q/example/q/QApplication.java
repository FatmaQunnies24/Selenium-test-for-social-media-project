package q.example.q;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class QApplication {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "C:/Users/fatim/Desktop/test/chromedriver-win64/chromedriver.exe");

        // إنشاء مثيل لـ ChromeDriver
        WebDriver driver = new ChromeDriver();

        try {
            // افتح المتصفح وقم بتكبير النافذة
            driver.manage().window().maximize();

            // الانتقال إلى موقع Google
            driver.get("https://www.google.com");

            // العثور على شريط البحث
            WebElement searchBox = driver.findElement(By.name("q"));

            // كتابة كلمة "Selenium" في شريط البحث
            searchBox.sendKeys("Selenium");

            // تنفيذ البحث بالضغط على Enter
            searchBox.submit();

            // انتظار ظهور نتائج البحث لمدة 3 ثوانٍ (يفضل استخدام WebDriverWait)
            Thread.sleep(3000);  // استخدام sleep كمثال، ولكن الأفضل استخدام WebDriverWait

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // إغلاق المتصفح بعد الانتهاء
            driver.quit();
        }
    }
}

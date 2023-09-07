package test.java.testDir;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebdriverFactory {

    public static WebDriver getWebDriver() {
        return new ChromeDriver();
    }
}

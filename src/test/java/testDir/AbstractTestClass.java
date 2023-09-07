package test.java.testDir;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;

import java.time.Duration;

import static org.openqa.selenium.By.xpath;
import static test.java.testDir.GetXpath.getButtonID;
import static test.java.testDir.GetXpath.getUtilsID;


public class AbstractTestClass {

    protected WebDriver driver;

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
//        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
    }

    @AfterSuite
    public void TearDown() {
        if (driver != null) driver.quit();
    }

    public void openSuite(String url) {
        driver.get(url);
    }


    public WebElement getSeEl(String xpath) {
        checkVisibleElement(xpath);
        return driver.findElement(xpath(xpath));

    }

    public WebElement getSeElInBlock(String xpathBlock, String xpathElement) {
        checkVisibleElement(xpathBlock);
        return driver.findElement(xpath(xpathBlock)).findElement(xpath(xpathElement));
    }

    public void clickToButton(String nameButton) {
        getSeEl(getButtonID(nameButton)).click();
    }

    public void clickToButtonInBlock(String nameBlock, String nameButton) {
        getSeElInBlock(getUtilsID(nameBlock), getButtonID(nameButton)).click();
    }

    public void clickToTextInBlock(String nameBlock, String textButton) {
        getSeEl(getUtilsID(nameBlock)).findElement(xpath(".//*[text()='" + textButton + "']")).click();
    }


    /**
     * Метод ожидания отображения элемента в течении 60 сек
     * @param xpath = xpath значение которое ищется в DOM.
     */
    @SneakyThrows
    private void checkVisibleElement(String xpath) {
        int count = 0;
        while (count < 60) {
            if (driver.getPageSource().contains(
                    xpath.replaceAll(".*@(.*)]", "$1"))) break;
            else {
                Thread.sleep(1000);
                count++;
            }
        }
    }
}

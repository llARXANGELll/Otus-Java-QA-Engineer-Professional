package test.java.testDir.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static java.lang.System.lineSeparator;
import static java.lang.System.out;
import static org.openqa.selenium.By.xpath;
import static test.java.testDir.utils.GetXpath.getButtonID;
import static test.java.testDir.utils.GetXpath.getUtilsID;


public class AbstractTestClass {

    protected WebDriver driver;
    protected SoftAssert softAssert = new SoftAssert();

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
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

    public List<WebElement> getElCo(String xpath) {
        checkVisibleElement(xpath);
        return driver.findElements(xpath(xpath));

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

    @SneakyThrows
    public void hoverToListElementForIndex(String xpathList, int index) {
        Actions actions = new Actions(driver);
        actions.moveToElement(getElCo(getUtilsID(xpathList)).get(index - 1)).perform();
        Thread.sleep(2000);
    }

    public void checkContainsAttributeForListElementsInIndex(String xpathList, int index,
                                                             String nameClassAttribute, String expectedAttribute) {
        var actualAttribute = getElCo(getUtilsID(xpathList)).get(index - 1).getAttribute(nameClassAttribute);
        softAssert.assertEquals(actualAttribute.contains(expectedAttribute), true,
                lineSeparator() + "Список актуальных атрибутов:" + lineSeparator() + actualAttribute + lineSeparator()
                        + lineSeparator() + "Ожидаемый атрибут:" + lineSeparator() + expectedAttribute + lineSeparator());
        softAssert.assertAll();
    }


    /**
     * Метод ожидания отображения элемента в течении 5 сек
     *
     * @param xpath = xpath значение которое ищется в DOM.
     */
    @SneakyThrows
    private void checkVisibleElement(String xpath) {
        int actualSec = 0, expectedSec = 20;
        while (actualSec < expectedSec) {
            out.println("Ожидание элемента " + actualSec + " сек из 5 секунд по xpath: " + xpath);
            if (driver.getPageSource().contains(
                    xpath.replaceAll(".*\"(.*)\".*", "$1"))) break;
            else if (actualSec == expectedSec - 1)
                throw new TimeoutException("Время ожидание элемента по xpath" + xpath + " в течении " + expectedSec + " вышло");
            else {
                Thread.sleep(1000);
                actualSec++;
            }
        }
    }
}

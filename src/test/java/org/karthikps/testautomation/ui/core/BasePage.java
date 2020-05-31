package org.karthikps.testautomation.ui.core;

import com.paulhammant.ngwebdriver.ByAngularRepeater;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kohsuke.rngom.parse.host.Base;
import org.openqa.selenium.Alert;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasePage<T>{

    private final Logger logger = LogManager.getLogger(BasePage.class);
    private final int timeoutInSeconds = 10;
    protected WebDriver webDriver;

    public BasePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    protected enum SelectBy {
        INDEX, VALUE, TEXT
    }

    /**
     * FluentWait till element is clickable
     * @param element
     */
    private void fluentWait(WebElement element) {
        try {
            Wait wait = new FluentWait<>(webDriver)
                    .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                    .pollingEvery(Duration.ofMillis(100));
            wait.until(ExpectedConditions.elementToBeClickable(element));
        }
        catch (ElementNotVisibleException e) {
            logger.error(e.getStackTrace());
            throw (e);
        }
    }

    /**
     * Click on the element
     * @param element
     */
    protected void click(WebElement element) {
        fluentWait(element);
        element.click();
    }

    /**
     * Enter string into text field
     * @param element
     * @param value
     */
    protected void enterStringToField(WebElement element, String value) {
        fluentWait(element);
        element.sendKeys(value);
    }

    /**
     * Open a browser instance with the provided URL
     * @param url
     */
    protected void openBrowser(String url) {
        webDriver.navigate().to(url);
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    /**
     * Select an element from select box
     * @param element
     * @param selectBy
     * @param t
     */
    protected void selectElement(WebElement element, SelectBy selectBy, T t) {
        Select select = new Select(element);
        switch (selectBy) {
            case INDEX:
                select.selectByIndex((Integer) t);
                break;
            case VALUE:
                select.selectByValue((String) t);
                break;
            case TEXT:
                select.selectByVisibleText((String) t);
                break;
            default:
                logger.info("Provided option not found");
        }
    }

    protected void selectFromListUsingRepeater(ByAngularRepeater repeater, SelectBy selectBy, T t) {
        switch (selectBy) {
            case INDEX:
                repeater.findElements(webDriver).get((Integer) t).click();
                break;
            case VALUE:
                for (WebElement webElement:
                repeater.findElements(webDriver)) {
                    if(webElement.getText().contains((String) t)) {
                        webElement.click();
                    }
                }
        }
    }

    /**
     * WaitForElement
     *
     * @param element element
     */
    protected void waitForElement(WebElement element) {
        new WebDriverWait(webDriver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * WaitForAlert
     */
    protected void waitForAlert() {
        new WebDriverWait(webDriver, Duration.ofSeconds(30)).until(ExpectedConditions.alertIsPresent());
    }

    /**
     * WaitForElements
     *
     * @param elements elements
     */
    protected void waitForElements(List<WebElement> elements) {
        new WebDriverWait(webDriver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    /**
     * WaitForElementToInvisible
     *
     * @param elements elements
     */
    protected void waitForElementToDisappear(WebElement elements) {
        new WebDriverWait(webDriver, Duration.ofSeconds(30)).until(ExpectedConditions.invisibilityOf(elements));
    }

    /**
     * WaitForElementsToInvisible
     *
     * @param elements elements
     */
    protected void waitForElementsToDisappear(List<WebElement> elements) {
        new WebDriverWait(webDriver, Duration.ofSeconds(30)).until(ExpectedConditions.invisibilityOfAllElements(elements));
    }

    /**
     * Accept alert
     */
    protected void acceptAlert() {
        Alert alert = webDriver.switchTo().alert();
        alert.accept();
    }

    /**
     * Dismiss alert
     */
    protected void dismissAlert() {
        Alert alert = webDriver.switchTo().alert();
        alert.dismiss();
    }

    /**
     * Get Alert Text
     *
     * @return alertText
     */
    protected String getAlertText() {
        Alert alert = webDriver.switchTo().alert();
        return alert.getText();
    }

    /**
     * Double Click
     *
     * @param element element
     */
    protected void doubleClick(WebElement element) {
        new Actions(webDriver).doubleClick(element).build().perform();
    }

    /**
     * Drag and drop
     *
     * @param element1 element1
     * @param element2 element2
     */
    protected void dragAndDrop(WebElement element1, WebElement element2) {
        new Actions(webDriver).dragAndDrop(element1, element2).build().perform();
    }
}

package com.volvo.project.components;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;


/**
 * This class contains methods representing actions which can be performed by a user on the webpage.
 * These methods are used in classes representing Page Objects.
 *
 * @author a049473
 */
public class PageObject {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String VALUE_ATTRIBUTE = "value";
    private static final int CHECKBOX_SELECTION_RETRY_COUNT = 5;

    protected final WebDriver driver;

    public PageObject(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    protected WebElement waitForElementToBeVisible(WebElement element) {
        return waitForElementToBeVisible(element, Duration.ofSeconds(10));
    }

    protected WebElement waitForElementToBeVisible(WebElement element, Duration waitingTimeDuration) {
        WebDriverWait wait = new WebDriverWait(driver, waitingTimeDuration);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitForElementToBeClickable(WebElement element) {
        return waitForElementToBeClickable(element, Duration.ofSeconds(10));
    }

    protected WebElement waitForElementToBeClickable(WebElement element, Duration waitingTimeDuration) {
        WebDriverWait wait = new WebDriverWait(driver, waitingTimeDuration);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected WebElement waitForElementLocatedByToBeVisible(By by) {
        return waitForElementLocatedByToBeVisible(by, Duration.ofSeconds(10));
    }

    protected WebElement waitForElementLocatedByToBeVisible(By by, Duration waitingTimeDuration) {
        WebDriverWait wait = new WebDriverWait(driver, waitingTimeDuration);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    /**
     * Waits 10 seconds until element located by a locator is invisible.
     */
    protected void waitForElementNotPresent(By by) {
        waitForElementNotPresent(by, Duration.ofSeconds(10));
    }

    private void waitForElementNotPresent(By by, Duration waitingTimeDuration) {
        WebDriverWait wait = new WebDriverWait(driver, waitingTimeDuration);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    protected void typeInto(WebElement field, String value) {
        waitForElementToBeClickable(field).sendKeys(value);
    }

    protected void clickPageUp(WebElement field) {
        waitForElementToBeVisible(field).sendKeys(Keys.PAGE_UP);
    }

    protected void clickPageDown(WebElement field) {
        waitForElementToBeVisible(field).sendKeys(Keys.PAGE_DOWN);
    }

    protected void clickCtrlQ(WebElement field) {
        waitForElementToBeVisible(field).sendKeys(Keys.chord(Keys.CONTROL, "q"));
    }

    protected void clickEnter(WebElement field) {
        waitForElementToBeVisible(field).sendKeys(Keys.ENTER);
    }

    protected void clickSpace(WebElement field) {
        waitForElementToBeVisible(field).sendKeys(Keys.SPACE);
    }

    protected void clickTab(WebElement field) {
        waitForElementToBeVisible(field).sendKeys(Keys.TAB);
    }

    protected void clearField(WebElement field) {
        waitForElementToBeClickable(field).clear();
    }

    protected void typeInto(WebElement field, int value) {
        waitForElementToBeClickable(field).sendKeys(Integer.toString(value));
    }

    protected void clickButton(WebElement button) {
        waitForElementToBeClickable(button).click();
    }

    protected void dismissAlert() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().dismiss();
    }

    protected String closeAlertAndGetItsText(boolean shouldAcceptAlert) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        if (shouldAcceptAlert) {
            alert.accept();
        } else {
            alert.dismiss();
        }
        return alertText;
    }

    protected void clickButtonLocatedBy(By button) {
        waitForElementLocatedByToBeVisible(button).click();
    }

    protected void moveToElement(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(waitForElementToBeVisible(element));
        actions.perform();
    }

    protected void doubleClickElement(WebElement element) {
        Actions actions = new Actions(driver);
        actions.doubleClick(waitForElementToBeVisible(element));
        actions.perform();
    }

    protected List<String> extractTextFromWebElementList(List<WebElement> webElements) {
        return webElements
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    protected String getWebElementText(WebElement webElement) {
        return waitForElementToBeVisible(webElement).getText();
    }

    protected boolean verifyWebElementText(String actualText, WebElement webElement) {
        return waitForElementToBeVisible(webElement).getText().equals(actualText);
    }

    protected String getWebElementTextWaitFor2Sec(By field) {
        return waitForElementLocatedByToBeVisible(field, Duration.ofSeconds(2)).getText();
    }

    protected String getWebElementTextAndRemoveFormatting(WebElement element) {
        return waitForElementToBeVisible(element).getText().replaceAll("[^\\d\\-]", "");
    }

    protected String getElementText(By field) {
        return waitForElementToBeVisible(driver.findElement(field)).getText();
    }

    protected String getElementTextAndRemoveFormatting(By elementLocator) {
        return waitForElementToBeVisible(driver.findElement(elementLocator)).getText().replaceAll("[^\\d\\-]", "");
    }

    protected String getElementTextWait30Seconds(By elementLocator) {
        return waitForElementLocatedByToBeVisible(elementLocator, Duration.ofSeconds(30)).getText();
    }

    protected String getValueAttribute(WebElement webElement) {
        return waitForElementToBeVisible(webElement).getAttribute(VALUE_ATTRIBUTE).trim();
    }

    protected String getValueAttributeOfHiddenField(WebElement webElement) {
        return waitForElementToBeVisible(webElement).getAttribute(VALUE_ATTRIBUTE);
    }

    protected String getValueAttributeLocatedBy(By by) {
        return waitForElementLocatedByToBeVisible(by).getAttribute(VALUE_ATTRIBUTE);
    }

    protected String getValueAttributeLocatedByAndRemoveFormatting(By by) {
        return waitForElementLocatedByToBeVisible(by).getAttribute(VALUE_ATTRIBUTE).replace(" ", "").replace(".", "").replace(",", "");
    }

    protected boolean isRadioChecked(WebElement webElement) {
        if (waitForElementToBeVisible(webElement).getAttribute("checked") == null) {
            return false;
        }
        return waitForElementToBeVisible(webElement).getAttribute("checked").equals("true");
    }

    protected boolean isRadioActive(WebElement webElement) {
        return waitForElementToBeVisible(webElement).getAttribute("disabled").equals("true");
    }

    protected boolean isInputActive(WebElement webElement) {
        if (waitForElementToBeVisible(webElement).getAttribute("disabled") == null) {
            return true;
        } else {
            return false;
        }
    }

    protected String getPageSource() {
        return driver.getPageSource();
    }

    /**
     * Switches to specified frame on the page.
     */
    protected void switchToFrame(By frame) {
        driver.switchTo().defaultContent();
        driver.switchTo().frame(driver.findElement(frame));
    }

    protected void switchToWindow(String windowHandle) {
        driver.switchTo().window(windowHandle);
    }

    protected String getCurrentWindowHandle() {
        return driver.getWindowHandle();
    }

    /**
     * Clicks specified WebElement - before clicking waits for 10 seconds for WebElement's presence.
     */
    protected void clickElement(WebElement element) {
        waitForElementToBeVisible(element).click();
    }

    /**
     * Clicks specified WebElement - before clicking waits for 30 seconds for WebElement's presence.
     */
    protected void clickElementWait30Seconds(WebElement element) {
        waitForElementToBeVisible(element, Duration.ofSeconds(30)).click();
    }

    /**
     * Clicks element specified by xpath node name and contained text.
     */
    protected void clickElementLocatedByText(String xpathNode, String text) {
        String xpathLocator = String.format("//%s[text()='%s']", xpathNode, text);
        waitForElementLocatedByToBeVisible(By.xpath(xpathLocator)).click();
    }

    protected void doubleClickElementLocatedByText(String xpathNode, String text) {
        String xpathLocator = String.format("//%s[text()='%s']", xpathNode, text);
        doubleClickElement(driver.findElement(By.xpath(xpathLocator)));
    }

    /**
     * @param path
     * @return canonical path to a file
     * If the file does not exist, null is returned
     */
    protected String getFilePath(String path) {
        try {
            return new java.io.File(".").getCanonicalPath() + path;
        } catch (IOException e) {
            return null;
        }
    }

    // ------------------------Presence verification-------------------------

    /**
     * @param by value to determine location strategy
     * @return true if element specified by a locator is NOT present on the page
     */
    protected boolean isElementNotPresent(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * @param element WebElement to check presence
     * @return true if WebElement is NOT present on the page
     */
    protected boolean isElementNotPresent(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (NoSuchElementException e) {
            return true;
        }
        return false;
    }

    /**
     * @param by value to determine location strategy
     * @return true if element specified by a locator is present on the page
     */
    protected boolean isElementPresent(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    /**
     * @param element WebElement to check presence
     * @return true if WebElement is present on the page
     */
    protected boolean isElementPresent(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    /**
     * @param button value to determine location strategy
     * @return true if specified button is present AND NOT active
     */
    protected boolean isButtonPresentAndInactive(By button) {
        waitForElementLocatedByToBeVisible(button).isDisplayed();
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(button)));
        } catch (TimeoutException t) {
            return false;
        }
    }

    /**
     * @param button value to determine location strategy
     * @return true if specified button is present AND active
     */
    protected boolean isButtonPresentAndActive(By button) {
        try {
            waitForActiveButton(button);
        } catch (TimeoutException t) {
            return false;
        }
        return true;
    }

    // ------------------------Drop-down list handling------------------------

    /**
     * @param dropdownList descr
     * @return false when specified dropdown list is NOT active
     */
    protected boolean isDropdownListActive(By dropdownList) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(dropdownList));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    /**
     * @param dropdownList descr
     * @return false when specified dropdown list is active
     */
    protected boolean isDropdownListNotActive(By dropdownList) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            return wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(dropdownList)));
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Sets value defined by an Enum value on specified select list.
     *
     * @param dropdownElement
     * @param value
     */
    @SuppressWarnings("rawtypes")
    protected void setValueOnDropdownField(WebElement dropdownElement, Enum value) {
        new Select(dropdownElement).selectByVisibleText(value.toString());
    }

    /**
     * Sets value defined by a String value on specified select list.
     *
     * @param dropdownElement
     * @param value
     */
    protected void setValueOnDropdownField(WebElement dropdownElement, String value) {
        new Select(dropdownElement).selectByVisibleText(value);
    }

    /**
     * Sets value on dropdown list based on index.
     *
     * @param dropdownElement
     * @param i
     */
    protected void setValueOnDropdownFieldByIndex(WebElement dropdownElement, int i) {
        new Select(dropdownElement).selectByIndex(i);
    }

    /**
     * @param dropdown
     * @return current value set on specified select list
     */
    protected String getDropdownCurrentValue(WebElement dropdown) {
        return new Select(dropdown).getFirstSelectedOption().getText();
    }

    /**
     * @param dropdown
     * @return true if there is only one element available on dropdown
     */
    protected boolean isOnlyOneElementAvailableOnDropdown(WebElement dropdown) {
        return (new Select(dropdown).getOptions().size() == 0);
    }

    /**
     * Sets random value on specifies select list.
     *
     * @param dropdownElement
     * @param blankFirstValue
     * @return text from selected value
     */
    protected String setRandomValueOnDropdownField(WebElement dropdownElement, boolean blankFirstValue) {
        Select select = new Select(dropdownElement);
        List<WebElement> listedOptions = select.getOptions();
        int index = (int) (Math.random() * listedOptions.size());
        index = adjustIndex(blankFirstValue, index);
        listedOptions.get(index).click();
        return listedOptions.get(index).getText();
    }

    /**
     * @param blankFirstValue descr
     * @param index           descr
     * @return index of first valid element on the list
     */
    private int adjustIndex(boolean blankFirstValue, int index) {
        int i = index;
        if (blankFirstValue && i == 0) {
            i += 1;
        }
        return i;
    }

    // ------------------------Select-box handling--------------------------------------

    /**
     * @param selectbox
     * @return all options from a select list defined by a locator
     */
    protected String getAllSelectboxOptions(By selectbox) {
        List<WebElement> selectOptions = driver.findElements(selectbox);
        return selectOptions
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.joining(" "));
    }

    // ------------------------Checkbox handling----------------------------------------

    /**
     * Sets expected value on a checkbox defined by a locator.
     *
     * @param checkbox
     * @param select
     */
    protected void setCheckbox(By checkbox, boolean select) {
        int retry = CHECKBOX_SELECTION_RETRY_COUNT;
        if (select) {
            if (!isCheckboxSelected(checkbox)) {
                while (retry > 0) {
                    waitForElementLocatedByToBeVisible(checkbox).click();
                    try {
                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                        wait.until(ExpectedConditions.elementSelectionStateToBe(checkbox, true));
                        break;
                    } catch (TimeoutException e) {
                        retry--;
                    }
                }
            }
        } else {
            if (isCheckboxSelected(checkbox)) {
                while (retry > 0) {
                    waitForElementLocatedByToBeVisible(checkbox).click();
                    try {
                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                        wait.until(ExpectedConditions.elementSelectionStateToBe(checkbox, false));
                        break;
                    } catch (TimeoutException e) {
                        retry--;
                    }
                }
            }
        }
    }

    /**
     * @param checkbox
     * @return true if the specified checkbox is selected
     */
    protected boolean isCheckboxSelected(final By checkbox) {
        return waitForElementLocatedByToBeVisible(checkbox).isSelected();
    }

    /**
     * @param checkbox
     * @return true if the specified checkbox is NOT selected
     */
    protected boolean isCheckboxUnselected(final By checkbox) {
        return !(waitForElementLocatedByToBeVisible(checkbox).isSelected());
    }

    // ------------------------waitFor[...] methods------------------------------------

    /**
     * @param radio
     * @return true if specified radio is active
     */
    protected boolean isRadioActive(By radio) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(radio));
        } catch (TimeoutException t) {
            return false;
        }
        return true;
    }

    /**
     * @param radio
     * @return true if specified radio is selected
     */
    protected boolean isRadioSelected(By radio) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return wait.until(ExpectedConditions.elementSelectionStateToBe(radio, true));
        } catch (TimeoutException t) {
            return false;
        }
    }

    /**
     * @param radio
     * @return true if specified radio is not selected
     */
    protected boolean isRadioNotSelected(By radio) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return wait.until(ExpectedConditions.elementSelectionStateToBe(radio, false));
        } catch (TimeoutException t) {
            return false;
        }
    }

    /**
     * @param radio
     * @return true if specified radio is not active
     */
    protected boolean isRadioNotActive(By radio) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(radio)));
        } catch (TimeoutException t) {
            return false;
        }
    }

    /**
     * @param radio
     * @return clickable element
     */
    protected WebElement waitForClickableRadio(By radio) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.elementToBeClickable(radio));
    }

    /**
     * @param element
     * @return boolean value
     */
    protected boolean checkElementHasText(WebElement element, String text) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Waits for active button.
     *
     * @param by value to determine location strategy
     */
    protected void waitForActiveButton(By by) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(by));
    }


    /**
     * Waits for not active button.
     *
     * @param by value to determine location strategy
     */
    protected void waitForNotClickableElement(By by) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(by)));
    }

    /**
     * Waits for not active element.
     *
     * @param by value to determine location strategy
     */
    protected void waitForNotActiveElement(By by) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.not(ExpectedConditions.elementToBeSelected(by)));
    }

    //-----------------------Handling elements using JS---------------------

    public void clickUsingJS(By elementLocator) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", driver.findElement(elementLocator));
    }

    public void clickUsingJS(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public void scrollElementIntoView(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void scrollElementIntoView(By elementLocator) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(elementLocator));
    }

    protected void scrollDownUsingJS() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,250);", "");
    }

    protected void scrollUpUsingJS() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,-470);", "");
    }

    protected void hideWebElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.visibility='hidden'", element);
    }

    protected void showWebElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.visibility='visible'", element);
    }
}
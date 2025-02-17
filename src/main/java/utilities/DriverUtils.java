package utilities;

import logger.LOG;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import reporter.ExtentTestManager;

import java.time.Duration;

public class DriverUtils {
    public static final int shortTimeOut = 5;
    public static final int second10TimeOut = 10;
    public static int mediaCounter = 0;
    protected static WebDriver driver;

    public DriverUtils(WebDriver driver) {
        this.driver = driver;
    }


    public boolean selectByIndex(WebElement element, int index) {
        boolean flag = false;
        try {
            Select s = new Select(element);
            s.selectByIndex(index);
            flag = true;
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (flag) {
                System.out.println("Option selected by Index");
            } else {
                System.out.println("Option not selected by Index");
            }
        }
    }

    public boolean selectByValue(WebElement element, String value) {
        boolean flag = false;
        try {
            Select s = new Select(element);
            s.selectByValue(value);
            flag = true;
            return true;
        } catch (Exception e) {

            return false;
        } finally {
            if (flag) {
                System.out.println("Option selected by Value");
            } else {
                System.out.println("Option not selected by Value");
            }
        }
    }

    public boolean selectByVisibleText(String visibletext, WebElement ele) {
        boolean flag = false;
        try {
            Select s = new Select(ele);
            s.selectByVisibleText(visibletext);
            flag = true;
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (flag) {
                LOG.Reporter("Option selected by VisibleText");
            } else {
                LOG.Reporter("Option not selected by VisibleText");
            }
        }
    }

    public void highlightOnly(WebElement element, String color) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].setAttribute('style','background:" + color + "; border: 2px solid red;')", element);
        } catch (Exception ex) {
            throw new RuntimeException("Unable to highlight" + ex.getMessage());
        }
    }

    public WebElement waitforElementVisible(WebElement element, int timeout) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.visibilityOf(element));
        } catch (Exception ex) {
            LOG.Reporter(ex.getMessage());
            throw new RuntimeException("Unable to find element" + ex.getMessage());
        }
    }

    public boolean Alert(WebDriver driver) {
        boolean presentFlag = false;
        Alert alert = null;

        try {
            alert = driver.switchTo().alert();
            alert.accept();
            presentFlag = true;
        } catch (NoAlertPresentException ex) {
            ex.printStackTrace();
            LOG.INFO(ex.getMessage());
        } finally {
            if (!presentFlag) {
                LOG.Reporter("The Alert is handled successfully");
            } else {
                LOG.Reporter("There was no alert to handle");
            }
        }

        return presentFlag;
    }

    public void scrollByVisibilityOfElement(WebDriver driver, WebElement ele) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", ele);

    }

    public WebElement waitforElementClickable(WebElement element, int timeout) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception ex) {
            LOG.INFO(ex.getMessage());
            throw new RuntimeException("Unable to click element" + ex.getMessage());
        }


    }

    public boolean iselementVisible(WebElement element, int timeout) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (Exception ex) {
            LOG.INFO(ex.getMessage());
            return false;
        }

    }

    public boolean iselementEnable(WebElement element, int timeout) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.visibilityOf(element)).isEnabled();
        } catch (Exception ex) {
            LOG.INFO(ex.getMessage());
            return false;
        }

    }

    public Boolean waitForTextToBePresent(WebElement element, String text, int timeout) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (Exception ex) {
            LOG.INFO(ex.getMessage());
            throw new RuntimeException(" exception occured during wait" + ex.getMessage());
        }
    }

    public void clickByAction(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).click().build().perform();
    }

    public void typeByAction(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).sendKeys().build().perform();
    }

    public void sendkeysForAutoSuggestions(WebElement element, String text) {
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            String a = String.valueOf(chars[i]);
            element.sendKeys(a);
        }
    }

    public void clickByJSE(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", element);
    }

    public void waitForVisibility(WebElement e) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    public void clear(WebElement element) {
        waitForVisibility(element);
        element.clear();
    }

    public WebElement iselementClickable(WebElement element, int timeout) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception ex) {
            LOG.INFO(ex.getMessage());
            throw new RuntimeException("element not clickable" + ex.getMessage());
        }
    }

    public boolean iselementVisible(WebElement element) {

        return iselementVisible(element, shortTimeOut);
    }

    public boolean iselemenDisappeared(WebElement element, int timeout) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.invisibilityOf(element));
        } catch (Exception ex) {
            LOG.INFO(ex.getMessage());
            return false;
        }

    }

    public static String getBase64SnapShot(WebDriver webdriver) {
        try {
            LOG.INFO(webdriver.getCurrentUrl());
            TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
            return ((TakesScreenshot) webdriver).getScreenshotAs(OutputType.BASE64);

        } catch (Exception ex) {
            LOG.INFO(ex.getMessage());
            throw new RuntimeException("Unable to capture screenshot");
        }
    }

    public static void attachScreenshot() {
        try {
            ExtentTestManager.getTest().addScreenCaptureFromBase64String(getBase64SnapShot(driver)).getModel()
                    .getMedia().get(mediaCounter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaCounter++;
    }

}

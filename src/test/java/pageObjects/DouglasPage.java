package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class DouglasPage extends BasePage {

    // Define locators using @FindBy
    @FindBy(xpath = "//li[@aria-label='PERFUMEryr']")
    private WebElement cookieAcceptButton;

    @FindBy(xpath = "//li[@aria-label='PARFUM']")
    private WebElement parfumLink;

    @FindBy(xpath = "//input[@name='facet-search']")
    private WebElement filterSearch;

    @FindBy(xpath= "//input[@type='checkbox']")
    private WebElement firstCheckbox;

    @FindBy(xpath= "//*[@data-testid='product-eyecatcher-discountFlag' and contains(text(),'Sale')]")
    private List<WebElement> saleCount;

    @FindBy(xpath= "//*[@data-testid='product-eyecatcher-computedNewFlag' and contains(text(),'NEU')]")
    private List<WebElement> neuCount;

    @FindBy(xpath= "//*[@data-testid='eyecatcher__container'][.//*[contains(@data-testid, 'discountFlag') and contains(text(),'Sale')] and .//*[contains(@data-testid, 'NewFlag') and contains(text(),'NEU')] ]")
    private List<WebElement> saleAndNeuCount;


    public DouglasPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Method to accept cookies
    public void acceptCookies() {
        try {
            Thread.sleep(10000);
            JavascriptExecutor js = (JavascriptExecutor) driver;

            WebElement moreInfoButton = null;

            // Try to locate the button inside the shadow DOM
            try {
                moreInfoButton = (WebElement) js.executeScript(
                        "return document.querySelector('#usercentrics-root').shadowRoot.querySelector('button[data-testid=\"uc-accept-all-button\"]');"
                );
            } catch (Exception e) {
                System.out.println("Failed to locate the 'Accept All' button inside shadow DOM: " + e.getMessage());
            }

            // Try to scroll the button into view
            if (moreInfoButton != null) {
                try {
                    js.executeScript("arguments[0].scrollIntoView(true);", moreInfoButton);
                } catch (Exception e) {
                    System.out.println("Failed to scroll button into view: " + e.getMessage());
                }

                // Try to click the button
                try {
                    js.executeScript("arguments[0].click();", moreInfoButton);
                } catch (Exception e) {
                    System.out.println("Failed to click 'Accept All' button: " + e.getMessage());
                }
            } else {
                System.out.println("Accept button not found, skipping click.");
            }

            Thread.sleep(5000);

            // Try clicking the cookie accept button if it exists
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.elementToBeClickable(cookieAcceptButton)).click();
            } catch (TimeoutException e) {
                System.out.println("No cookie popup displayed.");
            } catch (Exception e) {
                System.out.println("Unexpected error while clicking the cookie accept button: " + e.getMessage());
            }

        } catch (InterruptedException e) {
            System.out.println("Thread sleep interrupted: " + e.getMessage());
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }


    // Method to navigate to Parfum category
    public void goToParfumSection() throws InterruptedException {
        acceptCookies();
        parfumLink.click();
        Thread.sleep(10000);

    }
    public void applyFilter(String filterName,String searchValue) {
        try {
            System.out.println("Selecting Filter: " + filterName + " | Searching for: " + searchValue);

            // Click on the filter dropdown dynamically
            WebElement filterElement = driver.findElement(By.xpath("//div[@class='facet']/div[contains(text(),'" + filterName + "')]"));
            Assert.assertTrue(iselementVisible(filterElement ,second10TimeOut));
            filterElement.click();

            // Wait for the search input and enter the provided search term
            Assert.assertTrue(iselementVisible(filterSearch,shortTimeOut));
            filterSearch.click();
            filterSearch.sendKeys(searchValue);

            // Wait for and click the first available checkbox
            Assert.assertTrue(iselementVisible(firstCheckbox,shortTimeOut));
            firstCheckbox.click();

            System.out.println("✅ Successfully applied filter: " + filterName + " with search: " + searchValue);
        } catch (Exception e) {
            System.out.println("❌ Failed to interact with filter: " + filterName + " | Error: " + e.getMessage());
        }
    }

    public int getProductCount(List<WebElement> productElements, String category) {
        int count = productElements.size();
        System.out.println("✅ " + category + " Products Count: " + count);
        return count;
    }

    // Method to return the saleCount elements
    public List<WebElement> getSaleCountElements() {
        return saleCount;
    }

    // Method to count sale products
    public void takeSalesCount() {
        getProductCount(saleCount, "Sale");
    }

}

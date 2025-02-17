package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utilities.DriverUtils;

public class BasePage extends DriverUtils {
/*
    @FindBy(xpath = "//*[@class=\"mat-list-item-content\" and contains(text(),'Home')]")
    protected WebElement homeScreenDisplayed;*/

    public BasePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

}

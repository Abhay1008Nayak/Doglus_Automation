package tests;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.LoginPage;
import pageObjects.DouglasPage;

import java.util.List;

import static reporter.ExtentTestManager.startTest;

public class VehicleTrackTestV1 extends SuiteSetup {


    LoginPage page;
    DouglasPage douglasPage;


    @Test(priority = 1)
    public void testToVerifyAllTabs() {
        startTest("Validate All Tabs",
                "Track landing page should have 4 tabs i.e. Moving, Idling, Stopped & Not Reachable.");
        page = new LoginPage(getDriver());
        page.navigateToOrganizationSignIn();

    }

    @Test(priority = 2)
    public void testFilterParfumProducts() throws InterruptedException {
        startTest("Validate Product Filtering",
                "This test filters parfum products using the given keyword.");

        page = new LoginPage(getDriver());

        // Initialize the DouglasPage object
        douglasPage = new DouglasPage(getDriver());

        // Navigate to Parfum category
        douglasPage.goToParfumSection();

    }


    @Test(priority = 3, dataProvider = "filterSearchData")
    @Parameters({"filterName", "searchValue"})  // Accept parameters dynamically
    public void filterProducts(@Optional("Produktart") String filterName, @Optional("Shampoo") String searchValue) {
        startTest("Validate Product Filtering",
                "This test filters parfum products using the given keyword.");

        page = new LoginPage(getDriver());
        // Initialize the DouglasPage object
        douglasPage = new DouglasPage(getDriver());
        douglasPage.applyFilter(filterName,searchValue);

    }

    @DataProvider(name = "filterSearchData")
    public Object[][] getFiltersAndSearchValues() {
        return new Object[][] {
                { "Preis"},
                { "Produktart", "Shampoo" },
                { "Marke", "Bon Parfum" },
                { "Für Wen", "Lotion" },
                { "Duftnote", "Makeup" },
                {"Verantwortung"},
                {"Zusatzstoffe"},
                {"Aktionen"}
        };
    }

    @Test(priority = 2)
    public void testFilterSaleCount() throws InterruptedException {
        startTest("Validate Product Filtering",
                "This test filters parfum products using the given keyword.");

        page = new LoginPage(getDriver());

        // Initialize the DouglasPage object
        douglasPage = new DouglasPage(getDriver());

        // Navigate to Parfum category
        douglasPage.goToParfumSection();
        douglasPage = new DouglasPage(getDriver());

        // Now, count sale products
        douglasPage.takeSalesCount();

    }
}



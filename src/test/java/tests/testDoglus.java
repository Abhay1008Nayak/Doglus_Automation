package tests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.LoginPage;
import pageObjects.DouglasPage;

import static reporter.ExtentTestManager.startTest;

public class testDoglus extends SuiteSetup {


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


//    @Test(priority = 3, dataProvider = "filterSearchData")
//    @Parameters({"filterName"})  // Accept parameters dynamically
//    public void filterProducts(@Optional("Aktionen") String filterName) {
//        startTest("Validate Product Filtering",
//                "This test filters parfum products using the given keyword.");
//
//        page = new LoginPage(getDriver());
//        // Initialize the DouglasPage object
//        douglasPage = new DouglasPage(getDriver());
//        douglasPage.applyFilter(filterName);
//
//    }

    @DataProvider(name = "filterSearchData")
    @Parameters()


    public Object[][] getFiltersAndSearchValues() {
        return new Object[][] {
                { "Preis" },
                { "Produktart" },
                { "Marke" },
                { "Für Wen" },
                { "Duftnote" },  // Fixed the missing closing quote
                { "Verantwortung" },  // Fixed the missing closing quote
                { "Zusatzstoffe" },  // Fixed the missing closing quote
                { "Aktionen" }  // Fixed the missing closing quote
        };
    }

    @Test(priority = 2)
    public void testFilterSaleCount() throws InterruptedException {
        startTest("Validate Product Filtering",
                "This test filters parfum products using the given keyword.");

        page = new LoginPage(getDriver());

        // Initialize the DouglasPage object
        douglasPage = new DouglasPage(getDriver());

        // Now, count sale products
        douglasPage.applyFilter_2();

    }
    @Test(priority = 3)
    public void testFilterProducts() {
        String filterName = "Aktionen"; // Run only for "Aktionen"
        String item_name = "Sale";

        startTest("Validate Product Filtering",
                "This test filters parfum products using the given keyword.");

        page = new LoginPage(getDriver());

        // Initialize the DouglasPage object
        douglasPage = new DouglasPage(getDriver());
        douglasPage.applyFilter(filterName,item_name);

        // Initialize the DouglasPage object
        douglasPage = new DouglasPage(getDriver());
        douglasPage.takeSalesCount();

        System.out.println("✅ Successfully applied filter: " + filterName);
    }
}



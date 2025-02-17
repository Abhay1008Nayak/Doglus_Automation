package browserFactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import logger.LOG;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.HashMap;

public class DriverInit {

    private static WebDriver driver;
    public static String browserType;
    public static  WebDriver getDriver(String browserType){
        if(DriverInit.browserType==null) {
            DriverInit.browserType = browserType;
        }
        if(driver == null) {
            switch (browserType) {
                case "chrome":
                    System.setProperty("webdriver.chrome.driver", "C:\\Users\\91951\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

                    ChromeOptions options = new ChromeOptions();
                    HashMap<String, Object> chromePref = new HashMap<String, Object>();
                    chromePref.put("profile.default_content_settings.popups", 0);
                    chromePref.put("download.default_directory", System.getProperty("user.dir")+ File.separator+"src"+File.separator+"test"+File.separator+"resources");
                    chromePref.put("browser.set_download_behavior", "{ behavior: 'allow' , downloadPath: '"+ System.getProperty("user.dir")+ File.separator+"src"+File.separator+"test"+File.separator+"resources"+"'}");
                    // options.addArguments("--headless");
                    options.addArguments("--disable-notifications");
                    options.addArguments("window-size=1200x600");
                    options.addArguments("--disable-popup-blocking");
                    options.addArguments("--remote-allow-origins=*");
                    options.addArguments("--disable-web-security");
                    options.addArguments("--allow-running-insecure-content");
                    options.setExperimentalOption("prefs", chromePref);
                    driver = new ChromeDriver(options);
                    driver.manage().window().maximize();
                    LOG.INFO("Launching browser");
                    break;
                case "firefox":
                    break;
                case "edge":
                    break;
            }
        }
        return driver;
    }

    public static void tearDown() {
        driver.quit();
        driver=null;
        LOG.INFO("Quit Driver");
    }
}

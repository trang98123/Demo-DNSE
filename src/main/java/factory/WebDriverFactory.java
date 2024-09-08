package com.smartosc.automation.factory;

import com.smartosc.automation.common.TestContext;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Class to handle for generating webdriver
 */
public class WebDriverFactory {

    // Generate Webdriver
    public static WebDriver generateWebDriver(TestContext testContext) {

        switch (testContext._browserName) {
            case CHROME:
                WebDriverManager.chromedriver().version("110").setup();

                ChromeOptions chromeOptions = new ChromeOptions();
                //chromeOptions.addArguments("--headless");  //We need this line to build the test on jenkins
                chromeOptions.addArguments("window-size=1920,1080");  // this line for the test does not fail in headless option
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                return new ChromeDriver(chromeOptions);

            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();

                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--headless");  //We need this line to build the test on jenkins
                firefoxOptions.addArguments("window-size=1920,1080");
                firefoxOptions.addArguments("--no-sandbox");
                firefoxOptions.addArguments("--disable-dev-shm-usage");
                return new FirefoxDriver(firefoxOptions);

            default:
                WebDriverManager.chromedriver().setup();

                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless");  //We need this line to build the test on jenkins
                options.addArguments("window-size=1920,1080");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                return new ChromeDriver(options);
        }
    }
}

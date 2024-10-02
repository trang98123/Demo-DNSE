package common;

import com.google.common.base.Function;
import factory.WebDriverFactory;
import logging.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public abstract class WebApp {
    private Logger logger;
    private WebDriver driver;
    private TestContext testContext;
    /**
     * Constructor
     *
     * @throws Exception
     */
    public WebApp() throws Exception {
        try {
            logger = new Logger();
            testContext = new TestContext();
            driver = WebDriverFactory.generateWebDriver(testContext);

        } catch (Exception ex) {
            throw ex;
        }
    }
    /**
     * Get testContext
     *
     * @return
     */
    public TestContext getTestContext() {
        return testContext;
    }
    /**
     * Set testContext
     *
     * @param testContext
     */
    public void setTestContext(TestContext testContext) {
        this.testContext = testContext;
    }
    /**
     * Get logger
     *
     * @return
     */
    public Logger getLogger() {
        return logger;
    }
    public void clickToElementIndex(By selector,String index) throws Exception {
        WebElement el = this.safeElementWait(selector);
        el.click();
    }

    /**
     * Set logger
     *
     * @param logger
     */
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * Get webdriver
     *
     * @return
     */
    public WebDriver getDriver() {
        return driver;
    }
    public void accepAlert1(WebDriver driver) {
        alert = waitForAlertPresence(driver);
        alert.accept();
        sleepInSecond(2);
    }
    public void sleepInSecond(long timeInSecond) {
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public Alert waitForAlertPresence(WebDriver driver) {
        explicitWait = new WebDriverWait(driver, shortTimeout);
        return explicitWait.until(ExpectedConditions.alertIsPresent());
    }
    /**
     * Click by javascript
     *
     * @param selector
     * @throws Exception
     */
    public void clickToElementByJavaScriptExecutor(By selector) throws Exception {
        WebElement el = this.safeElementWait(selector);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", el);
    }



    public WebElement safeElementWait(By selector) throws Exception {
        WebElement element = null;
        try {
            driver.manage().timeouts().implicitlyWait(SeleniumConfiguration.getImplicitTimeout(), TimeUnit.MILLISECONDS);
            FluentWait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(SeleniumConfiguration.getFluentWaitTimeout(), TimeUnit.MILLISECONDS)
                    .pollingEvery(SeleniumConfiguration.getFluentWaitPolling(), TimeUnit.MILLISECONDS)
                    .ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class);
            element = wait.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    WebElement el = driver.findElement(selector);
                    return (el.isDisplayed()) ? el : null;
                }
            });
        } catch (Exception e) {
            throw new Exception("Timed out waiting for web element <" + selector + "> after " + SeleniumConfiguration.getFluentWaitTimeout());

        } finally {
            driver.manage().timeouts().implicitlyWait(SeleniumConfiguration.getImplicitTimeout(), TimeUnit.MILLISECONDS);
        }
        return element;
    }
    /**
     * Quit app
     */
    public void quit() throws Exception {
        driver.quit();
    }


    /**
     * Wait for a page
     *
     * @param pageclass
     * @param <E>
     * @return
     * @throws Exception
     */
    public <E extends WebPageBase> WebPageBase waitForPage(Class<E> pageclass) throws Exception {
        // Log @TODO
        WebPageBase page = null;
        try {
            //use fluent wait to perform the polling
            driver.manage().timeouts().implicitlyWait(SeleniumConfiguration.getImplicitTimeout(), TimeUnit.MILLISECONDS);
            page = new FluentWait<>(pageclass)
                    .withTimeout(SeleniumConfiguration.getFluentWaitTimeout(), TimeUnit.MILLISECONDS)
                    .pollingEvery(SeleniumConfiguration.getFluentWaitPolling(), TimeUnit.MILLISECONDS)
                    .until(new Function<Class<E>, WebPageBase>() {
                        public WebPageBase apply(Class<E> pageclass) {
                            try {
                                // Log @TODO
                                WebPageBase page = pageclass.getConstructor().newInstance();
                                page.init();
                                page.checkOnPage();
                                return page;

                            } catch (Exception e) {
                                return null;
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Timed out waiting for page: " + pageclass.getName());
        } finally {
            driver.manage().timeouts().implicitlyWait(SeleniumConfiguration.getImplicitTimeout(), TimeUnit.MILLISECONDS);
        }

        return page;
    }

    public void navigateToLoginPage() throws Exception {
        try {
            driver.get("https://www.dnse.com.vn/");
            driver.manage().window().maximize();

        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * Capture Full Page Screenshot
     *
     * @return
     * @throws Exception
     */
    public byte[] takeScreenshot(String filename) throws Exception {
        //project directory
        String workingDirectory = System.getProperty("user.dir");
        String dir = workingDirectory + File.separator + "target/screenshots";

        //create a directory in the path
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdir();
        }

        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
        ImageIO.write(screenshot.getImage(), "jpg", new File("target/screenshots/" + filename));

        final byte[] scst = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        return scst;
    }

    /**
     * Safe element click to element
     *
     * @param selector
     * @throws Exception
     */
    public void clickToElement(By selector) throws Exception {
        WebElement el = this.safeElementWait(selector);
        el.click();
    }
    public boolean isElementDisplayed(By selector) throws Exception {
        WebElement el = driver.findElement(selector);
        try {
            el.isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        } catch (StaleElementReferenceException f) {
            return false;
        }
    }
    /**
     * Safe element click to element
     *
     * @param selector
     * @throws Exception
     */

    /**
     * Safe element send keys
     *
     * @param selector
     * @param input
     * @throws Exception
     */
    public void safeElementSendKeys(By selector, String input) throws Exception {
        WebElement el = this.safeElementWait(selector);
        el.clear();
        el.sendKeys(input);
    }




    /**
     * Navigate back
     */
    public void navigateBack() throws Exception {
        try {
            driver.navigate().back();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }




    /**
     * Get text of the element
     *
     * @param selector
     * @throws Exception
     */
    public String getTextOfElement(By selector) throws Exception {
        WebElement el = this.safeElementWait(selector);
        return el.getText();
    }


    /**
     * Hover mouse to element
     *
     * @param selector
     * @throws Exception
     */
    public void hoverMouseToElement(By selector) throws Exception {
        WebElement el = this.safeElementWait(selector);
        Actions action = new Actions(driver);
        action.moveToElement(el).perform();
    }    private Alert alert;
    private WebDriverWait explicitWait;
    private long shortTimeout = GlobalConstants.SHORT_TIMEOUT;
}

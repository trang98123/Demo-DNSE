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

    public WebElement waitForElementClickable(By selector) throws Exception {
        WebElement element = null;
        try {
            driver.manage().timeouts().implicitlyWait(common.SeleniumConfiguration.getImplicitTimeout(), TimeUnit.MILLISECONDS);
            FluentWait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(common.SeleniumConfiguration.getFluentWaitTimeout(), TimeUnit.MILLISECONDS)
                    .pollingEvery(common.SeleniumConfiguration.getFluentWaitPolling(), TimeUnit.MILLISECONDS)
                    .ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class);
            element = wait.until(ExpectedConditions.elementToBeClickable(selector));
        } catch (Exception e) {
            throw new Exception("Timed out waiting for web element <" + selector + "> after " + common.SeleniumConfiguration.getFluentWaitTimeout());

        } finally {
            driver.manage().timeouts().implicitlyWait(common.SeleniumConfiguration.getImplicitTimeout(), TimeUnit.MILLISECONDS);
        }

        return element;
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

    public WebElement getElement(WebDriver driver, String locator) {
        return driver.findElement(By.xpath(locator));
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

    public void senkeyToElement(WebDriver driver, String locator, String value) {
        getElement(driver, locator).clear();
        getElement(driver, locator).sendKeys(value);
    }

    /**
     * Get cookie
     */
    public void getCookie() throws Exception {
        // create file named Cookies to store Login Information
        File file = new File("Cookies.data");
        try {
            // Delete old file if exists
            file.delete();
            file.createNewFile();
            FileWriter fileWrite = new FileWriter(file);
            BufferedWriter Bwrite = new BufferedWriter(fileWrite);

            // loop for getting the cookie information
            for (Cookie ck : driver.manage().getCookies()) {
                Bwrite.write((ck.getName() + ";" + ck.getValue() + ";" + ck.getDomain() + ";" + ck.getPath() + ";" + ck.getExpiry() + ";" + ck.isSecure()));
                Bwrite.newLine();
            }
            Bwrite.close();
            fileWrite.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Set cookie
     */
    public void setCookie() throws Exception {
        try {

            File file = new File("Cookies.data");
            FileReader fileReader = new FileReader(file);
            BufferedReader Buffreader = new BufferedReader(fileReader);
            String strline;
            while ((strline = Buffreader.readLine()) != null) {
                String[] tk = strline.split(";");
                String name = tk[0];
                String value = tk[1];
                String domain = tk[2];
                String path = tk[3];

                Date expiry = null;
                if (!tk[4].equals("null")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                    expiry = sdf.parse(tk[4]);
                }

                Boolean isSecure = Boolean.parseBoolean(tk[5]);

                Cookie ck = new Cookie(name, value, domain, path, expiry, isSecure);
                driver.manage().addCookie(ck);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * refresh the page
     */
    public void refreshThePage() throws Exception {
        try {
            driver.navigate().refresh();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
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
     * Navigate forward
     */
    public void navigateForward() throws Exception {
        try {
            driver.navigate().forward();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }


    /**
     * Navigate to a page
     *
     * @param pageUrl
     * @throws Exception
     */
    public void navigateToPage(String pageUrl) throws Exception {
        try {
            driver.get(pageUrl);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * Select drop down item by visible text
     *
     * @param selector
     * @param text
     * @throws Exception
     */
    public void selectDropdownItemByVisibleText(By selector, String text) throws Exception {
        try {
            WebElement el = this.safeElementWait(selector);
            Select dropDown = new Select(el);
            dropDown.selectByVisibleText(text);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * Select drop down item by value
     *
     * @param selector
     * @param value
     * @throws Exception
     */
    public void selectDropdownItemByValue(By selector, String value) throws Exception {
        try {
            WebElement el = this.safeElementWait(selector);
            Select dropDown = new Select(el);
            dropDown.selectByValue(value);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * Select drop down item by index
     *
     * @param selector
     * @param index
     * @throws Exception
     */
    public void selectDropdownItemByIndex(By selector, int index) throws Exception {
        try {
            WebElement el = this.safeElementWait(selector);
            Select dropDown = new Select(el);
            dropDown.selectByIndex(index);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * Get first selected option
     *
     * @param selector
     * @throws Exception
     */
    public WebElement getFirstSelectedOption(By selector) throws Exception {
        try {
            WebElement el = this.safeElementWait(selector);
            Select dropDown = new Select(el);
            return dropDown.getFirstSelectedOption();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * get id of element
     */
    public String getElementId(By selector) throws Exception {
        try {
            WebElement el = this.safeElementWait(selector);
            String id = el.getAttribute("id");
            return id;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * Click on the radio button
     *
     * @param selector
     * @throws Exception
     */
    public void clickRadioButton(By selector) throws Exception {
        try {
            WebElement el = this.safeElementWait(selector);
            if (el.isSelected() == false)
                el.click();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * Unlick on the radio button
     *
     * @param selector
     * @throws Exception
     */
    public void unclickRadioButton(By selector) throws Exception {
        try {
            WebElement el = this.safeElementWait(selector);
            if (el.isSelected() == true)
                el.click();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }


    /**
     * Waiting element in safe
     *
     * @param selector
     * @return
     */
    public WebElement waitTillElementIsEnabled(By selector) throws Exception {
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
                    return (el.isEnabled()) ? el : null;
                }
            });
        } catch (Exception e) {
            throw new Exception("Timed out waiting for web element <" + selector + "> to be enabled after " + SeleniumConfiguration.getFluentWaitTimeout());

        } finally {
            driver.manage().timeouts().implicitlyWait(SeleniumConfiguration.getImplicitTimeout(), TimeUnit.MILLISECONDS);
        }

        return element;

    }


    /**
     * Safe element send keys by keyboard
     *
     * @param selector
     * @param value
     * @throws Exception
     */
    public void sendKeysByKeyBoard(By selector, String value) throws Exception {
        WebElement el = this.safeElementWait(selector);
        el.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        for (int i = 0; i <= value.length() - 1; i++) {
            String key = String.valueOf(value.charAt(i));
            el.sendKeys(Keys.chord(key));
        }

    }

    /**
     * Safe element send keys by keyboard
     *
     * @param el
     * @param value
     * @throws Exception
     */
    public void sendKeysByKeyBoard(WebElement el, String value) throws Exception {

        el.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        for (int i = 0; i <= value.length() - 1; i++) {
            String key = String.valueOf(value.charAt(i));
            el.sendKeys(Keys.chord(key));
        }

    }

    /**
     * Press enter by keyboard
     *
     * @param selector
     * @throws Exception
     */
    public void pressEnterByKeyBoard(By selector) throws Exception {
        WebElement el = this.safeElementWait(selector);
        el.sendKeys(Keys.chord(Keys.ENTER));
    }

    /**
     * Double click to element
     *
     * @param selector
     * @throws Exception
     */
    public void doubleClickToElement(By selector) throws Exception {
        WebElement el = this.safeElementWait(selector);
        Actions action = new Actions(driver);
        action.moveToElement(el).doubleClick().perform();
    }


    /**
     * Click by javascript
     *
     * @param element
     * @throws Exception
     */
    public void clickToElementByJavaScriptExecutor(WebElement element) throws Exception {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    /**
     * Scroll to the top of the page
     *
     * @throws Exception
     */
    public void scrollToTheTopOfThePage() throws Exception {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight, 0)");

    }

    /**
     * Scroll element into view
     *
     * @param selector
     * @throws Exception
     */
    public void scrollElementIntoView(By selector) throws Exception {
        WebElement element = safeElementWait(selector);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(500);
    }

    /**
     * Scroll to the end of the page
     *
     * @throws Exception
     */
    public void scrollToTheEndOfThePage() throws Exception {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,document.body.scrollHeight)");

    }


    /**
     * Click by javascript
     *
     * @param text
     * @throws Exception
     */
    public void selectByLinkText(String text) throws Exception {
        By LINKTEXT = By.linkText(text);
        WebElement el = this.safeElementWait(LINKTEXT);
        safeElementWait(LINKTEXT);
        el.click();
    }

    /**
     * Execute javascript
     *
     * @param script
     * @throws Exception
     */
    public void executeJavascript(String script) throws Exception {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript(script);
    }

    /**
     * Execute javascript
     *
     * @param script
     * @throws Exception
     */
    public String getTextByJavascriptExecutor(String script) throws Exception {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        return (String) executor.executeScript(script);
    }

    /**
     * Get list of elements
     *
     * @param selector
     * @param i
     * @throws Exception
     */
    public void clickToElementInTheList(By selector, int i) throws Exception {
        List<WebElement> elements = driver.findElements(selector);
        elements.get(i).click();
    }

    /**
     * Get list of elements
     *
     * @param selector
     * @param i
     * @throws Exception
     */
    public WebElement getElementI(By selector, int i) throws Exception {
        List<WebElement> elements = driver.findElements(selector);
        return elements.get(i);
    }


    /**
     * Verify if element is enabled
     *
     * @param selector
     * @throws Exception
     */
    public boolean elementIsEnabled(By selector) throws Exception {
        WebElement el = this.safeElementWait(selector);
        return el.isEnabled();
    }


    /**
     * Switch to iframe by locator
     *
     * @param selector
     * @throws Exception
     */
    public void switchToIframe(By selector) throws Exception {
        WebElement el = this.safeElementWait(selector);
        driver.switchTo().frame(el);
    }

    /**
     * Switch to iframe i
     *
     * @throws Exception
     */
    public void switchToIframeByIndex(int i) throws Exception {
        driver.switchTo().frame(i);
    }


    /**
     * Switch to default frame
     *
     * @throws Exception
     */
    public void switchToDefaultContent() throws Exception {
        driver.switchTo().defaultContent();
    }

    /**
     * Switch to default frame
     *
     * @throws Exception
     */
    public String getClassofElement(By selector) throws Exception {
        WebElement el = this.safeElementWait(selector);
        return el.getAttribute("class");
    }

    /**
     * Check if the element is enabled, this function only works for <input> element
     *
     * @param selector
     * @throws Exception
     */
    public boolean isInputElementEnabled(By selector) throws Exception {
        WebElement el = driver.findElement(selector);
        try {
            el.isEnabled();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        } catch (StaleElementReferenceException f) {
            return false;
        }
    }

    /**
     * Check if the element is enabled, this function only works for <input> element
     *
     * @param selector
     * @throws Exception
     */
    public boolean isElementEnabled(By selector) throws Exception {
        WebElement el = driver.findElement(selector);
        Boolean enabled = null;
        try {
            Boolean containDisableAttribute = (Boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].hasAttribute(\"disabled\");", el);
            if(containDisableAttribute==true){
                enabled=false;
            }else{
                enabled= true;
            }
        } catch (NoSuchElementException e) {

        } catch (StaleElementReferenceException f) {

        }
        return enabled;
    }


    /**
     * Check if the element is displayed
     *
     * @param selector
     * @throws Exception
     */
    public void waitUntilElementPresented(By selector) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, SeleniumConfiguration.getImplicitTimeout() );
            wait.until(ExpectedConditions.presenceOfElementLocated(selector));

        } catch (NoSuchElementException e) {
        }
    }

    /**
     * Check if the element is existed
     *
     * @param selector
     * @throws Exception
     */
    public boolean isElementExisted(By selector) throws Exception {
        // return driver.findElements(selector).size() > 0;
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        try {
            driver.findElement(selector);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.MILLISECONDS);
        }
    }


    /**
     * Check if the element is selected
     *
     * @param selector
     * @throws Exception
     */
    public boolean isElementSelected(By selector) throws Exception {
        WebElement el = this.safeElementWait(selector);
        return el.isSelected();
    }


    /**
     * Check if the element is enabled
     *
     * @param el
     * @throws Exception
     */
    public boolean isElementEnabled(WebElement el) throws Exception {
        return el.isEnabled();
    }

    /**
     * Accept alert
     *
     * @throws Exception
     */
    public void acceptAlert() throws Exception {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException Ex) {
        }

    }

    /**
     * Cancel alert
     *
     * @throws Exception
     */
    public void cancelAlert() throws Exception {
        try {
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
        } catch (NoAlertPresentException Ex) {
        }
    }

    /**
     * Get text alert
     *
     * @throws Exception
     */
    public String getTextAlert() throws Exception {
        Alert alert = driver.switchTo().alert();
        return alert.getText();
    }

    /**
     * Send key to alert
     *
     * @param text
     * @throws Exception
     */
    public void enterTextToAlert(String text) throws Exception {
        Alert alert = driver.switchTo().alert();
        alert.sendKeys(text);
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
     * Get text of a list of elements
     *
     * @param selector
     * @throws Exception
     */
    public List<String> getTextOfElements(By selector) throws Exception {
        List<WebElement> elements = driver.findElements(selector);
        List<String> texts = new ArrayList<String>();
        for (WebElement el : elements) {
            String text = el.getAttribute("value");
            texts.add(text);
        }
        return texts;
    }

    /**
     * Get text of a list of elements
     *
     * @param selector
     * @throws Exception
     */
    public List<String> getTextOfElements2(By selector) throws Exception {
        List<WebElement> elements = driver.findElements(selector);
        List<String> texts = new ArrayList<String>();
        for (WebElement el : elements) {
            String text = null;
            int attempts = 0;
            while(attempts < 2 && text == null) { // loop to avoid StaleElementReferenceException
                try {
                    text = el.getText();
                } catch(Exception e) {
                }
                attempts++;
            }
            texts.add(text);
        }
        return texts;
    }

    /**
     * Get element i in a list for elements
     *
     * @param selector
     * @throws Exception
     */
    public WebElement getElementIInTheList(By selector, int i) throws Exception {
        List<WebElement> elements = driver.findElements(selector);
        return elements.get(i - 1);
    }

    /**
     * Get text by get value of attribute
     *
     * @param selector
     * @throws Exception
     */
    public String getTextOfElementByAttributeValue(By selector) throws Exception {
        WebElement el = this.safeElementWait(selector);
        return el.getAttribute("value");
    }

    /**
     * Get attribute value of an HTML tag
     *
     * @param selector
     * @throws Exception
     */
    public String getAttributeValue(By selector, String attribute) throws Exception {
        WebElement el = this.safeElementWait(selector);
        return el.getAttribute(attribute);
    }

    /**
     * Click on all elements in a list of elements
     *
     * @param selector
     * @throws Exception
     */
    public void clickOnAllElements(By selector) throws Exception {
        List<WebElement> elements = driver.findElements(selector);
        for (WebElement el : elements) {
            clickToElementByJavaScriptExecutor(el);

        }
    }

    /**
     * Return the number of elements in the list
     *
     * @param selector
     * @throws Exception
     */
    public int countElements(By selector) throws Exception {
        List<WebElement> elements = driver.findElements(selector);
        return elements.size();
    }


    /**
     * Check the checkbox
     *
     * @param selector
     * @throws Exception
     */
    public void checkTheCheckbox(By selector) throws Exception {
        WebElement el = this.safeElementWait(selector);
        if (el.isSelected() == false)
            el.click();
    }

    /**
     * uncheck the checkbox
     *
     * @param selector
     * @throws Exception
     */
    public void uncheckTheCheckbox(By selector) throws Exception {
        WebElement el = this.safeElementWait(selector);
        if (el.isSelected() == true)
            el.click();
    }

    /**
     * Open a new tab
     *
     * @throws Exception
     */
    public void openNewTab() throws Exception {
        ((JavascriptExecutor) driver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
    }

    /**
     * Go back to the first tap
     *
     * @throws Exception
     */
    public void backToFirstTap() throws Exception {
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
    }


    /**
     * Switch window by id
     *
     * @param id
     * @throws Exception
     */
    public void switchWindowByID(String id) throws Exception {
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            driver.switchTo().window(window);
            if (window.equalsIgnoreCase(id))
                break;
        }
    }

    /**
     * Switch window by title
     *
     * @param title
     * @throws Exception
     */
    public void switchWindowByTitle(String title) throws Exception {
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            driver.switchTo().window(window);
            String currentWindowTitle = driver.getTitle();
            if (currentWindowTitle.equalsIgnoreCase(title))
                break;
        }
    }

    /**
     * Close all window except for parent window
     *
     * @param parentWindow
     * @throws Exception
     */
    public void closeAllWindowExceptParent(String parentWindow) throws Exception {
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            if (!window.equals(parentWindow)) {
                driver.switchTo().window(window);
                driver.close();
            }

        }
    }


    /**
     * Taking a screenshot of a particular element of the page
     *
     * @param selector
     * @throws Exception
     */
    public void takeScreenhotForSpecificElement(By selector) throws Exception {
        WebElement el = this.safeElementWait(selector);
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver, el);

        ImageIO.write(screenshot.getImage(), "jpg", new File("c:\\ElementScreenshot.jpg"));
    }

    /**
     * Compare 2 images
     *
     * @param selector
     * @throws Exception
     */
    public boolean compareImage(By selector) throws Exception {
        // Find the element and take a screenshot
        WebElement el = this.safeElementWait(selector);
        Screenshot screenshot = new AShot().takeScreenshot(driver, el);

        // read the image to compare
        BufferedImage expectedImage = ImageIO.read(new File("path to actual image.png"));
        BufferedImage actualImage = screenshot.getImage();

        // Create ImageDiffer object and call method makeDiff()
        ImageDiffer imgDiff = new ImageDiffer();
        ImageDiff diff = imgDiff.makeDiff(actualImage, expectedImage);

        return diff.hasDiff();
    }

    /**
     * Get current URL
     *
     * @throws Exception
     */
    public String getCurrentURL() throws Exception {
        return driver.getCurrentUrl();
    }

    /**
     * Wait for Jquery
     */
    public void waitForThePageCompletelyLoaded() throws Exception {
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                JavascriptExecutor js = (JavascriptExecutor) d;
                return (Boolean) js.executeScript("return !!window.jQuery && window.jQuery.active == 0");
            }
        });

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

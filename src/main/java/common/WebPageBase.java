package common;

import org.openqa.selenium.WebDriver;


    public abstract class WebPageBase {

        protected WebApp app;

        protected WebDriver driver;

        public abstract void init();

        public abstract void checkOnPage() throws Exception;
    }


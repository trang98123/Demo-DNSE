package common;

import common.SystemConfiguration;

import java.io.InputStream;
import java.util.Properties;

public class SeleniumConfiguration {
    //Selenium timings properties file keys
    private static final String TIMINGS_CONFIG_FILE = "/config/seleniumconfig.properties";

    /**
     * Get Implicit timeout
     * @return
     * @throws Exception
     */
    public static int getImplicitTimeout() throws Exception {
        String env = SystemConfiguration.getTestEnvironment();
        return Integer.parseInt(readPropertyValue(TIMINGS_CONFIG_FILE, "implicitwait.timeout." + env.toLowerCase()));
    }

    /**
     * Get FluentWait timeout
     * @return
     * @throws Exception
     */
    public static int getFluentWaitTimeout() throws Exception {
        String env = SystemConfiguration.getTestEnvironment();
        return Integer.parseInt(readPropertyValue(TIMINGS_CONFIG_FILE, "fluentwait.timeout." + env.toLowerCase()));
    }

    /**
     * Get FluentWait Polling time
     * @return
     * @throws Exception
     */
    public static int getFluentWaitPolling() throws Exception {
        String env = SystemConfiguration.getTestEnvironment();
        return Integer.parseInt(readPropertyValue(TIMINGS_CONFIG_FILE, "fluentwait.polling." + env.toLowerCase()));
    }

    /**
     * Read value of a property
     * @param file
     * @param key
     * @return
     * @throws Exception
     */
    private static String readPropertyValue(String file, String key) throws Exception {
        try {
            InputStream input = SeleniumConfiguration.class.getResourceAsStream(file);
            Properties prop = new Properties();

            // Load
            prop.load(input);
            return prop.getProperty(key);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

}

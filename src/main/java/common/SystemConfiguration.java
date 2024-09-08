package common;

public class SystemConfiguration {

    /**
     * Get Test Environment
     * @return
     * @throws Exception
     */
    public static String getTestEnvironment() throws Exception {
        return getConfiguration("TEST_ENVIRONMENT");
    }
    /**
     * Get configuration value
     * @param key
     * @return
     * @throws Exception
     */
    private static String getConfiguration(String key) throws Exception {
        String val = System.getProperty(key);

        if(val == null) {
            val = System.getenv(key);
        }

        if(val == null) {
            throw new Exception ("The configuration key: " + key + " cannot be found!!!");
        }

        return val;
    }

    /**
     * Get Browser name
     * @return
     * @throws Exception
     */
    public static String getBrowserName() throws Exception {
        return getConfiguration("BROWSER_NAME");
    }
}

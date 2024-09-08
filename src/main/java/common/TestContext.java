package common;


import entity.BrowserNames;
import entity.TestEnvironments;

/**
 * Class to hold the test information on individual thread
 */
public class TestContext {

    public BrowserNames _browserName;
    public TestEnvironments _testEnv;

    /**
     * Constructor
     * @throws Exception
     */
    public TestContext() throws Exception {
        try {
            _browserName = BrowserNames.valueOf(SystemConfiguration.getBrowserName()) ;
            _testEnv = TestEnvironments.valueOf(SystemConfiguration.getTestEnvironment());

        } catch (Exception ex) {
            throw ex;
        }
    }
}

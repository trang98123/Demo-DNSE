package common;

import java.io.File;

public class GlobalConstants {
    public static final String DEV_APP_URL = "http://demo.nopcommerce.com";
    public static final String STAGING_APP_URL = "https://demo.staging.nopcommerce.com";
    public static final String TESTING_APP_URL = "https://demo.testing.nopcommerce.com";

    public static final long SHORT_TIMEOUT = 5;
    public static final long LONG_TIMEOUT = 25;

//duong dan cua thu muc

    public static final String PROJECT_PATH = System.getProperty("user.dir");
    public static final String UPLOAD_FOLDER_PATH = PROJECT_PATH + File.separator + "uploadFiles" + File.separator;
    public static final String DOWNLOAD_FOLDER_PATH = PROJECT_PATH + File.separator + "downloadFiles";

    //database
    public static final String DEV_DB_URL = "";
    public static final String DEV_DB_USER = "";
    public static final String DEV_DB_PASS = "";

    public static final String TEST_DB_URL = "";
    public static final String TEST_DB_USER = "";
    public static final String TEST_DB_PASS = "";
}

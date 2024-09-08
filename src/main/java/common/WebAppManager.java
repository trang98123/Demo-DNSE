package common;

public class WebAppManager {
    private static ThreadLocal<WebApp> instances = new ThreadLocal<WebApp>();

    //Get WebApp instance
    public static WebApp getWebApp(Class clazz) throws Exception {
        //
        try {
            WebApp webapp = instances.get();
            if (webapp == null) {
                instances.set((WebApp) clazz.getConstructor().newInstance());
            }
            return instances.get();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //Quit WebApp
    public static synchronized void quitApp() throws Exception {
        System.out.println("Quitting application");
        try {
            WebApp app = instances.get();
            if (app != null) {
                instances.remove();
             //   app.quit();
            }
        } catch (Exception e) {
            throw e;
        }
    }


}

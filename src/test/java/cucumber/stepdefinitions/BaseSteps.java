package com.smartosc.automation.cucumber.stepdefinitions;

import com.smartosc.automation.common.AutoWebApp;
import com.smartosc.automation.common.WebAppManager;
import com.smartosc.automation.data.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class BaseSteps extends AutoSteps {
    public BaseSteps() throws Throwable {

    }

    @Before
    public void beforeScenario(Scenario scen) throws Throwable {
        System.out.println("Thread - " + Thread.currentThread().getId() + " :" + "*** STARTING TEST SCENARIO: " + scen.getName() + " ***");
        AutoWebApp app = (AutoWebApp) WebAppManager.getWebApp(AutoWebApp.class);
        scenario.setCustomerInfos(CustomerInfos.CUSTOMER, null);
        scenario.setProductInfos(ProductInfos.PRODUCT, null);
        scenario.setProductInfos(ProductInfos.PRODUCT_ID, null);
    }

    @Before("@workflow_test")
    public void beforeWorkflowScenario(Scenario scen) throws Throwable {
        String workflow_name = scen.getName() + " " + data.generateCurrentTime("ddMMyyHHmm");
        scenario.setWorkflowInfos(WorkflowInfos.WORKFLOW_NAME, workflow_name);
    }

    @After(value = "@workflow_test", order = 1)
    public void afterWorkflowScenarios(Scenario scen) throws Throwable {
        //take screenshot
        String filename = scen.getName();
        filename = filename.replaceAll("\\W", "_");

        final byte[] screenshot;
        if (scen.isFailed()) {
            screenshot = app.takeScreenshot(data.generateScreenshotName(filename));
            scen.embed(screenshot, "image/png", filename);
        }

        //delete blog post if blog name is not null
        String blog_title = (String) scenario.getCommonInfos(CommonInfos.BLOG_TITLE);
        if (blog_title != null) {
            getSideBarNavigator().goToBlogPage();
            getBlogPage().deleteBlog(blog_title);
            scenario.setCommonInfos(CommonInfos.BLOG_TITLE, null);
            System.out.println(String.format("Blog: %S is deleted", blog_title));
        }

        //delete banner if banner name is not null
        String banner_name = (String) scenario.getCommonInfos(CommonInfos.BANNER);
        if (banner_name != null) {
            getSideBarNavigator().goToBannersPage();
            getBannersListingPage().deleteBanner(banner_name);
            scenario.setCommonInfos(CommonInfos.BANNER, null);
            System.out.println(String.format("Banner: %S is deleted", banner_name));

        }

        //delete web page if web page name is not null
        String webpage_name = (String) scenario.getCommonInfos(CommonInfos.WEB_PAGE_NAME);
        if (webpage_name != null) {
            getSideBarNavigator().goToWebPagesPage();
            getWebPageListingPage().deleteWebPage(webpage_name);
            scenario.setCommonInfos(CommonInfos.WEB_PAGE_NAME, null);
            System.out.println(String.format("Webpage: %S is deleted", webpage_name));

        }

        //delete product if product name is not null
        Product product = (Product) scenario.getProductInfos(ProductInfos.PRODUCT);
        String product_id = (String) scenario.getProductInfos(ProductInfos.PRODUCT_ID);
        if (product != null && product_id != null && !scen.isFailed()) {
            getSideBarNavigator().goToViewProductsPage();
            getProductListingPage().deleteProduct(product_id);
            scenario.setProductInfos(ProductInfos.PRODUCT, null);
            scenario.setProductInfos(ProductInfos.PRODUCT_ID, null);
            System.out.println(String.format("Product: %S is deleted", product.getProductName()));
        }

        //delete customer if customer name is not null
        Customer customer = (Customer) scenario.getCustomerInfos(CustomerInfos.CUSTOMER);
        if (customer != null && !scen.isFailed()) {
            getSideBarNavigator().goToViewCustomersPage();
            getCustomerListingPage().deleteACustomer(customer.getEmail());
            scenario.setCustomerInfos(CustomerInfos.CUSTOMER, null);
            System.out.println(String.format("Customer: %S is deleted", customer.getEmail()));

        }

        //delete the workflow if workflow_name is not null and test passed
        String workflow_name = (String) scenario.getWorkflowInfos(WorkflowInfos.WORKFLOW_NAME);
        if (workflow_name != null && !scen.isFailed()) {
            getSideBarNavigator().goToAtom8App();
            getWorkflowListingPage().deleteaWorkflow(workflow_name);
            scenario.setWorkflowInfos(WorkflowInfos.WORKFLOW_NAME, null);
            System.out.println(String.format("Workflow: %S is deleted", workflow_name));

        } else if (workflow_name != null && scen.isFailed()) { //inactivate the workflow if workflow_name is not null and test failed
            getSideBarNavigator().goToAtom8App();
            getWorkflowListingPage().inactiveWorkflowByName(workflow_name);
            System.out.println("Workflow name: " + workflow_name);
            scenario.setWorkflowInfos(WorkflowInfos.WORKFLOW_NAME, null);
            System.out.println(String.format("Workflow: %S is inactived", workflow_name));
        }

        //print product name & customer email
        try {
            if (product.getProductName() != null) {
                System.out.println("Product name: " + product.getProductName());
            }

            if (customer.getEmail() != null) {
                System.out.println("Customer: " + customer.getEmail());
            }
        } catch (NullPointerException e) {

        }
    }

    @After(order = 0)
    public void afterScenarios(Scenario scen) throws Throwable {
        //take screenshot
        String filename = scen.getName();
        filename = filename.replaceAll("\\W", "_");

        final byte[] screenshot;
        if (scen.isFailed()) {
            screenshot = app.takeScreenshot(data.generateScreenshotName(filename));
            scen.embed(screenshot, "image/png", filename);
        }

        System.out.println("Thread - " + Thread.currentThread().getId() + " :" + "*** FINISHING TEST SCENARIO: " + scen.getName() + " ***");
        WebAppManager.quitApp();
    }
}

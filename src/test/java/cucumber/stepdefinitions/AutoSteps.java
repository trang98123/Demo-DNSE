package com.smartosc.automation.cucumber.stepdefinitions;

import com.smartosc.automation.common.*;
import com.smartosc.automation.data.Data;
import com.smartosc.automation.data.ScenarioContext;

public class AutoSteps {

    protected AutoWebApp app;
    public ScenarioContext scenario = new ScenarioContext();
    Data data = new Data();

    public AutoSteps() throws Exception {
        try {
            app = (AutoWebApp) WebAppManager.getWebApp(AutoWebApp.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Get Login page
     *
     * @throws Exception
     */
    public LoginPagePO getLoginPage() throws Exception {
        return (LoginPagePO) app.waitForPage(LoginPagePO.class);
    }


    /**
     * Get add new customer page
     *
     * @throws Exception
     */
    public AddNewCustomerPagePO getAddCustomerPage() throws Exception {
        return (AddNewCustomerPagePO) app.waitForPage(AddNewCustomerPagePO.class);
    }

    /**
     * Get Integration page
     *
     * @throws Exception
     */
    public SettingPagePO getSettingTab() throws Exception {
        return (SettingPagePO) app.waitForPage(SettingPagePO.class);
    }

    /**
     * Get add new order page
     *
     * @throws Exception
     * @returne
     */
    public AddNewOrderPagePO getAddOrderPage() throws Exception {
        return (AddNewOrderPagePO) app.waitForPage(AddNewOrderPagePO.class);
    }

    /**
     * Get add new product page
     *
     * @return
     * @throws Exception
     */
    public AddNewProductPagePO getAddProductPage() throws Exception {
        return (AddNewProductPagePO) app.waitForPage(AddNewProductPagePO.class);
    }

    /**
     * Get cancel plan successful page
     *
     * @return
     * @throws Exception
     */
    public CancelSuccessfulPagePO getCancelSuccessfulPage() throws Exception {
        return (CancelSuccessfulPagePO) app.waitForPage(CancelSuccessfulPagePO.class);
    }

    /**
     * Get create workflow page
     *
     * @return
     * @throws Exception
     */
    public CreateWorkflowPagePO getCreateWorkflowPage() throws Exception {
        return (CreateWorkflowPagePO) app.waitForPage(CreateWorkflowPagePO.class);
    }

    /**
     * Get price list page
     *
     * @return
     * @throws Exception
     */
    public PriceListsPagePO getPriceListPage() throws Exception {
        return (PriceListsPagePO) app.waitForPage(PriceListsPagePO.class);
    }

    /**
     * Get customer detail page
     *
     * @return
     * @throws Exception
     */
    public CustomerDetailPagePO getCustomerDetailPage() throws Exception {
        return (CustomerDetailPagePO) app.waitForPage(CustomerDetailPagePO.class);
    }

    /**
     * Get customer listing page
     *
     * @return
     * @throws Exception
     */
    public CustomerListingPagePO getCustomerListingPage() throws Exception {
        return (CustomerListingPagePO) app.waitForPage(CustomerListingPagePO.class);
    }

    /**
     * Get Menu of Atom8 app
     *
     * @return
     * @throws Exception
     */
    public MenuPO getAtom8Menu() throws Exception {
        return (MenuPO) app.waitForPage(MenuPO.class);
    }

    /**
     * Get my subscription page
     *
     * @return
     * @throws Exception
     */
    public MySubscriptionPagePO getMySubscriptionPage() throws Exception {
        return (MySubscriptionPagePO) app.waitForPage(MySubscriptionPagePO.class);
    }

    /**
     * Get order listing page
     *
     * @return
     * @throws Exception
     */
    public OrderListingPagePO getOrderListingPage() throws Exception {
        return (OrderListingPagePO) app.waitForPage(OrderListingPagePO.class);
    }

    /**
     * Get My Theme page
     *
     * @return
     * @throws Exception
     */
    public MyThemePagePO getMyThemePage() throws Exception {
        return (MyThemePagePO) app.waitForPage(MyThemePagePO.class);
    }

    /**
     * Get Blog page
     *
     * @return
     * @throws Exception
     */
    public BlogPagePO getBlogPage() throws Exception {
        return (BlogPagePO) app.waitForPage(BlogPagePO.class);
    }

    /**
     * Get Create web page page
     *
     * @return
     * @throws Exception
     */
    public CreateWebPagePO getCreateWebPage() throws Exception {
        return (CreateWebPagePO) app.waitForPage(CreateWebPagePO.class);
    }

    /**
     * Get Blog page
     *
     * @return
     * @throws Exception
     */
    public WebPageDetailsPagePO getWebPageDetailsPage() throws Exception {
        return (WebPageDetailsPagePO) app.waitForPage(WebPageDetailsPagePO.class);
    }

    /**
     * Get Web page listing page
     *
     * @return
     * @throws Exception
     */
    public WebPagesPO getWebPageListingPage() throws Exception {
        return (WebPagesPO) app.waitForPage(WebPagesPO.class);
    }

    /**
     * Get Create Banners page
     *
     * @return
     * @throws Exception
     */
    public CreateBannerPagePO getCreateBannerPage() throws Exception {
        return (CreateBannerPagePO) app.waitForPage(CreateBannerPagePO.class);
    }

    /**
     * Get Banners Listing page
     *
     * @return
     * @throws Exception
     */
    public BannersListingPagePO getBannersListingPage() throws Exception {
        return (BannersListingPagePO) app.waitForPage(BannersListingPagePO.class);
    }

    /**
     * Get refund order page
     *
     * @return
     * @throws Exception
     */
    public RefundOrderPagePO getRefundOrderPage() throws Exception {
        return (RefundOrderPagePO) app.waitForPage(RefundOrderPagePO.class);
    }

    /**
     * Get confirm refund order page
     *
     * @return
     * @throws Exception
     */
    public ConfirmRefundPagePO getConfirmRefundOrderPage() throws Exception {
        return (ConfirmRefundPagePO) app.waitForPage(ConfirmRefundPagePO.class);
    }


    /**
     * Get payment page
     *
     * @return
     * @throws Exception
     */
    public PaymentPagePO getPaymentPage() throws Exception {
        return (PaymentPagePO) app.waitForPage(PaymentPagePO.class);
    }

    /**
     * Get pricing page
     *
     * @return
     * @throws Exception
     */
    public PricingPagePO getPricingPage() throws Exception {
        return (PricingPagePO) app.waitForPage(PricingPagePO.class);
    }

    /**
     * Get product detail page
     *
     * @return
     * @throws Exception
     */
    public ProductDetailPagePO getProductDetailPage() throws Exception {
        return (ProductDetailPagePO) app.waitForPage(ProductDetailPagePO.class);
    }

    /**
     * Get product listing page
     *
     * @return
     * @throws Exception
     */
    public ProductListingPagePO getProductListingPage() throws Exception {
        return (ProductListingPagePO) app.waitForPage(ProductListingPagePO.class);
    }

    /**
     * Get Product categories page
     *
     * @return
     * @throws Exception
     */
    public CategoryPagePO getProductCategoriesPage() throws Exception {
        return (CategoryPagePO) app.waitForPage(CategoryPagePO.class);
    }

    /**
     * Get SideBar Navigator
     *
     * @return
     * @throws Exception
     */
    public SideBarNavigator getSideBarNavigator() throws Exception {
        return (SideBarNavigator) app.waitForPage(SideBarNavigator.class);
    }

    /**
     * Get homepage
     *
     * @return
     * @throws Exception
     */
    public HomepagePO getHomepage() throws Exception {
        return (HomepagePO) app.waitForPage(HomepagePO.class);
    }

    /**
     * Get Thank page
     *
     * @return
     * @throws Exception
     */
    public ThankPagePO getThankPage() throws Exception {
        return (ThankPagePO) app.waitForPage(ThankPagePO.class);
    }

    /**
     * Get workflow listing page
     *
     * @return
     * @throws Exception
     */
    public WorkflowListingPagePO getWorkflowListingPage() throws Exception {
        return (WorkflowListingPagePO) app.waitForPage(WorkflowListingPagePO.class);
    }

}

package tests.unit_api_tests;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.api.APIServices;
import utils.reports.ExceptionListener;
import utils.reports.ReportUtils;
import utils.runner.GmailUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.api.APIData.COURSE_ID_GOLD;
import static utils.api.APIData.username;
import static utils.reports.LoggerUtils.logInfo;
import static utils.runner.ProjectProperties.*;

@Listeners(ExceptionListener.class)
public class APIUnitTest {
    private static String adminToken;
    private static String inviteCode;
    private static String password;
    private static String customerId;
    private Playwright playwright;
    private APIRequestContext apiRequestContext;

    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        apiRequestContext = playwright
                .request()
                .newContext(
                        new APIRequest.NewContextOptions()
                                .setBaseURL(API_BASE_URL)
                                .setExtraHTTPHeaders(headers)
                );
    }

    @BeforeMethod
    void beforeMethod(Method method) {
        logInfo("Run " + ReportUtils.getTestMethodName(method));
    }

    @AfterMethod
    void afterMethod(Method method, ITestResult testResult) {
        ReportUtils.logTestStatistic(method, testResult);
        logInfo(ReportUtils.getEndLine());
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(ITestContext iTestContext) {
        iTestContext.setAttribute("adminToken", adminToken);
        iTestContext.setAttribute("inviteCode", inviteCode);
        iTestContext.setAttribute("password", password);
        iTestContext.setAttribute("customerId", customerId);

        apiRequestContext.dispose();
        playwright.close();
    }

    @Test
    public void testCRUDCallVerification() {
        APIResponse response = apiRequestContext.get("");

        assertThat(response).isOK();
    }

    @Test(dependsOnMethods = {"testCRUDCallVerification"})
    public void testSignInAdminAPI() {
        APIResponse response = APIServices
                .signInAdmin(apiRequestContext, ADMIN_USERNAME, ADMIN_PASSWORD);

        JSONObject object = new JSONObject(response.text());
        adminToken = object.getString("token");

        Assert.assertEquals(201, response.status());
        Assert.assertNotNull(adminToken);

        logInfo("API: Admin was Sign In");
    }

    @Test(dependsOnMethods = {"testSignInAdminAPI"})
    public void testInviteCustomerAPI() {
        inviteCode = null;

        APIResponse response = APIServices
                .inviteCustomer(apiRequestContext, username, COURSE_ID_GOLD, adminToken);

        JSONObject object = new JSONObject(response.text());
        inviteCode = object.getString("code");

        Assert.assertEquals(201, response.status());
        Assert.assertNotNull(inviteCode);

        logInfo("API: Invite was sent to customer");
        logInfo("API: Invite code was saved");
    }

    @Test(dependsOnMethods = {"testInviteCustomerAPI"})
    public void testSignUpPromoAPI() throws Exception {
        Thread.sleep(5000);
        String password = GmailUtils
                .extractPasswordFromEmail(GmailUtils.getGmailService(), username);

        APIResponse response = APIServices
                .signUpPromo(apiRequestContext, username, password, inviteCode);

        JSONObject object = new JSONObject(response.text());
        customerId = object.getJSONObject("customer").getString("id");

        Assert.assertEquals(201, response.status());
        Assert.assertNotNull(customerId);

        logInfo("API: Customer was successfully Signed Up");
        logInfo("API: Customer ID saved");
    }

    @Test(dependsOnMethods = {"testSignInAdminAPI", "testSignUpPromoAPI"})
    public void testDeleteUserAPI() {
        APIResponse response = APIServices
                .deleteCustomer(apiRequestContext, customerId, adminToken);

        Assert.assertEquals(204, response.status());

        logInfo("API: Customer was successfully Deleted");
    }
}

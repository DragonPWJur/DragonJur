package tests;

import com.microsoft.playwright.*;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.*;

import java.lang.reflect.Method;
import java.nio.file.Paths;

import static utils.LoggerUtils.*;

@Listeners(utils.ExceptionListener.class)
public abstract class BaseTest {

    private final Playwright playwright = Playwright.create();
    private final Browser browser = BrowserManager.createBrowser(playwright);
    private BrowserContext context;
    private Page page;

    protected final String baseUrl = PropertyType.Browser.BASE_URL;

    @BeforeSuite
    protected void launchBrowser(ITestContext testContext) {
        log(ReportUtils.getReportHeader(testContext));

        if (browser.isConnected()) {
            log("Browser " + browser.browserType().name().toUpperCase() + " launched\n");
        } else {
            logError("!!! BROWSER " + browser.browserType().name().toUpperCase() + " IS NOT CONNECTED !!!\n");
            System.exit(1);
        }
    }

    @BeforeMethod
    protected void createContextAndPage(Method method) {
        log("Run " + ReportUtils.getTestMethodName(method));

        context = createContext(browser);
        TracingUtils.startTracing(context);

        page = context.newPage();
        log("Context and Page created");

        page.navigate(baseUrl);
        log("Base URL opened");

        login();
    }

    @AfterMethod
    protected void closeContext(Method method, ITestResult testResult) {
        log(ReportUtils.getTestStatistics(method, testResult));

        page.close();
        log("Page closed");

        TracingUtils.stopTracing(page, context, method, testResult);

        context.close();
        log("Context closed" + ReportUtils.END_LINE);
    }

    @AfterSuite
    protected void closeBrowser() {
        browser.close();
        log("Browser closed");

        playwright.close();
        log("Playwright closed");
    }

    private BrowserContext createContext(Browser pwBrowser) {
        return pwBrowser.newContext(new Browser.NewContextOptions()
                .setViewportSize(PropertyType.Browser.SCREEN_SIZE_WIDTH, PropertyType.Browser.SCREEN_SIZE_HEIGHT)
                .setRecordVideoDir(Paths.get(PropertyType.Tracing.VIDEO_PATH))
                .setRecordVideoSize(PropertyType.Tracing.VIDEO_WIDTH, PropertyType.Tracing.VIDEO_HEIGHT)
        );
    }

    private void login() {
        waitForPageLoad(TestData.SIGN_IN_END_POINT);

        page.fill("form input:only-child", PropertyType.Auth.USERNAME);
        page.fill("input[type='password']", PropertyType.Auth.PASSWORD);
        page.click("button[type='submit']");

        waitForPageLoad(TestData.HOME_END_POINT);

        if(page.url().equals(baseUrl + TestData.HOME_END_POINT)) {
            log("Login successful");
        } else {
            logError("!!! UNSUCCESSFUL LOGIN !!!");
        }
    }

    protected Page getPage() {
        return page;
    }

    protected Playwright getPlaywright() {
        return playwright;
    }

    protected void waitForPageLoad(String endPoint) {
        getPage().waitForURL(baseUrl + endPoint);
    }
}
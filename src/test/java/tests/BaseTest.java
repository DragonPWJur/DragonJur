package tests;

import com.microsoft.playwright.*;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.*;

import java.lang.reflect.Method;

import static utils.LoggerUtils.*;

@Listeners(utils.ExceptionListener.class)
public abstract class BaseTest {
    private final Playwright playwright = Playwright.create();
    private final Browser browser = BrowserManager.createBrowser(playwright);
    private BrowserContext context;
    private Page page;

    @BeforeSuite
    protected void launchBrowser(ITestContext testContext) {
        log("Playwright and Browser initialized");

        log(ReportUtils.getReportHeader());

        if (browser.isConnected()) {
            log("Browser " + browser.browserType().name().toUpperCase() + " launched\n");
        } else {
            logFatal("FATAL: BROWSER " + browser.browserType().name().toUpperCase() + " IS NOT CONNECTED\n");
            System.exit(1);
        }
    }

    @BeforeMethod
    protected void createContextAndPage(Method method) {
        log("Run " + ReportUtils.getTestMethodName(method));

        context = BrowserManager.createContext(browser);
        log("Context created");

        TracingUtils.startTracing(context);
        log("Tracing started");

        page = context.newPage();
        log("Page created");

        page.navigate(ProjectProperties.BASE_URL);
        log("Base URL opened");

        login();
    }

    @AfterMethod
    protected void closeContext(Method method, ITestResult testResult) {
        ReportUtils.logTestStatistic(method, testResult);

        page.close();
        log("Page closed");

        TracingUtils.stopTracing(page, context, method, testResult);
        log("Tracing stopped");

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

    private void login() {
        if(!page.url().equals(ProjectProperties.BASE_URL + TestData.SIGN_IN_END_POINT)) {
            waitForPageLoad(TestData.SIGN_IN_END_POINT);
        } else {
            log("On " + TestData.SIGN_IN_END_POINT);
        }

        page.fill("form input:only-child", ProjectProperties.USERNAME);
        page.fill("input[type='password']", ProjectProperties.PASSWORD);
        page.click("button[type='submit']");

        waitForPageLoad(TestData.HOME_END_POINT);

        if(page.url().equals(ProjectProperties.BASE_URL + TestData.HOME_END_POINT)) {
            log("Login successful");
        } else {
            logError("ERROR: Unsuccessful login");
        }
    }

    protected Page getPage() {
        return page;
    }

    protected Playwright getPlaywright() {
        return playwright;
    }

    protected void waitForPageLoad(String endPoint) {
        getPage().waitForURL(ProjectProperties.BASE_URL + endPoint);
    }
}
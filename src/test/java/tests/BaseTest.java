package tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import tests.helpers.TestData;
import utils.api.APIServices;
import utils.api.APIUtils;
import utils.reports.LoggerInfo;
import utils.runner.BrowserManager;
import utils.runner.LoginUtils;
import utils.runner.ProjectProperties;
import utils.reports.ExceptionListener;
import utils.reports.ReportUtils;
import utils.reports.TracingUtils;

import java.io.IOException;
import java.lang.reflect.Method;

import static utils.reports.LoggerUtils.*;
import static utils.runner.LoginUtils.COOKIES_FILE_PATH;

@Listeners(ExceptionListener.class)
abstract class BaseTest {
    private final Playwright playwright = Playwright.create();
    private final Browser browser = BrowserManager.createBrowser(playwright);
    private BrowserContext context;
    private Page page;

    @BeforeSuite
    void launchBrowser(ITestContext testContext) {
        LoginUtils.loginAndCollectCookies(COOKIES_FILE_PATH, ProjectProperties.USERNAME, ProjectProperties.PASSWORD);

        logInfo(ReportUtils.getReportHeader());

        if(playwright != null) {
            logInfo("Playwright " + LoggerInfo.getPlaywrightId(playwright) + " created.");
        } else {
            logFatal("FATAL: Playwright is NOT created\n");
            System.exit(1);
        }

        if (browser.isConnected()) {
            logInfo("Browser " + browser.browserType().name().toUpperCase() + " "
                    + LoggerInfo.getBrowserId(browser) + " launched.\n" + ReportUtils.getEndLine());
        } else {
            logFatal("FATAL: Browser " + browser.browserType().name().toUpperCase() + " is NOT connected\n"
                    + ReportUtils.getEndLine());
            System.exit(1);
        }
    }

    @BeforeMethod
    void createContextAndPage(Method method) {

        APIUtils.isGoldSubscriptionActive(playwright);

        logInfo("RUN " + ReportUtils.getTestMethodName(method));

        APIServices.cleanData(playwright);
        APIUtils.deletePaymentMethod(playwright);

        context = BrowserManager.createContextWithCookies(browser, COOKIES_FILE_PATH);
        logInfo("Context created");

        TracingUtils.startTracing(context);
        logInfo("Tracing started");

        page = context.newPage();
        logInfo("Page created");

        page.navigate(ProjectProperties.BASE_URL);

        if(isOnHomePage()) {
            getPage().onLoad(p -> page.content());
            if (!page.content().isEmpty()) {
                logInfo("Open Home page");
            }
            logInfo("Testing....");
        } else {
            logError("HomePage is NOT opened");
        }
    }

    @AfterMethod
    void closeContext(Method method, ITestResult testResult) throws IOException {
        ReportUtils.logTestStatistic(method, testResult);
        ReportUtils.addScreenshotToAllureReportForCIFailure(page,testResult);

        if (page != null) {
            page.close();
            logInfo("Page closed");
        }

        TracingUtils.stopTracing(page, context, method, testResult);
        logInfo("Tracing stopped");

        ReportUtils.addVideoAndTracingToAllureReportForCIFailure(method, testResult);

        if (context != null) {
            context.close();
            logInfo("Context closed" + ReportUtils.getEndLine());
        }
    }

    @AfterSuite
    void closeBrowser() {
        if(browser != null) {
            browser.close();
            logInfo("Browser closed");
        }
        if(playwright != null) {
            playwright.close();
            logInfo("Playwright closed"
                    + ReportUtils.getEndLine());
        }
    }

    protected  boolean isOnHomePage() {
        String pageUrl = ProjectProperties.BASE_URL + TestData.HOME_END_POINT;

        if (!getPage().url().equals(pageUrl) || getPage().content().isEmpty()) {
            getPage().waitForTimeout(2000);
        }

        return !getPage().content().isEmpty();
    }

    protected Page getPage() {

        return page;
    }

    public Playwright getPlaywright() {

        return playwright;
    }

    public Browser getBrowser() {

        return browser;
    }
}

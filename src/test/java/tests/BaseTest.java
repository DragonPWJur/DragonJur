package tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.*;

import java.io.IOException;
import java.lang.reflect.Method;

import static utils.LoggerUtils.log;
import static utils.LoggerUtils.logFatal;

@Listeners(utils.ExceptionListener.class)
public abstract class BaseTest {
    private Playwright playwright = Playwright.create();
    private Browser browser = BrowserManager.createBrowser(playwright, getClass());
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

        LoginUtils.loginAndCollectCookies(playwright, browser);

        LoginUtils.parseUserToken();
        log("User token extracted from cookies");

        playwright = Playwright.create();
        log("Playwright initialized");

        browser = BrowserManager.createBrowser(playwright, getClass());
    }

    @BeforeMethod
    protected void createContextAndPage(Method method) {
        log("Run " + ReportUtils.getTestMethodName(method));

        APIServises.cleanData(playwright);
        log("API: Course data cleared");

        context = BrowserManager.createContextWithCookies(browser);
        log("Context created");

        TracingUtils.startTracing(context);
        log("Tracing started");

        page = context.newPage();
        log("Page created");

        page.navigate(ProjectProperties.BASE_URL);
        log("Base URL opened");

    }

    @AfterMethod
    protected void closeContext(Method method, ITestResult testResult) throws IOException {
        ReportUtils.logTestStatistic(method, testResult);

        ReportUtils.addScreenshotToAllureReportForFailedTestsOnCI(page,testResult);

        page.close();
        log("Page closed");

        TracingUtils.stopTracing(page, context, method, testResult);
        log("Tracing stopped");

        ReportUtils.addVideoAndTracingToAllureReportForFailedTestsOnCI(method, testResult);

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

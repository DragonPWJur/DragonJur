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

        LoginUtils.loginAndCollectCookies(playwright, browser);

        LoginUtils.parseUserToken();
        log("User token extracted from cookies");

        log(ReportUtils.END_LINE);
        log(ReportUtils.getReportHeader());

        playwright = Playwright.create();
        browser = BrowserManager.createBrowser(playwright, getClass());

        if(playwright != null) {
            log("Playwright " + getPlaywrightId() + " created.");
        } else {
            logFatal("FATAL: Playwright is NOT created\n");
            System.exit(1);
        }

        if (browser.isConnected()) {
            log("Browser " + browser.browserType().name().toUpperCase() + " " + getBrowserId() + " launched.\n");
        } else {
            logFatal("FATAL: Browser " + browser.browserType().name().toUpperCase() + " is NOT connected\n");
            System.exit(1);
        }
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
        ReportUtils.addScreenshotToAllureReportForCIFailure(page,testResult);

        page.close();
        log("Page closed");

        TracingUtils.stopTracing(page, context, method, testResult);
        log("Tracing stopped");

        ReportUtils.addVideoAndTracingToAllureReportForCIFailure(method, testResult);

        context.close();
        log("Context closed" + ReportUtils.END_LINE);
    }

    @AfterSuite
    protected void closeBrowser() {
        if(browser != null & playwright != null) {
            browser.close();
            log("Browser closed");

            playwright.close();
            log("Playwright closed");
        }
    }

    private String getPlaywrightId() {
        String[] text = playwright.toString().split("impl.");
        return text[text.length - 1];
    }

    private String getBrowserId() {
        String[] text = browser.toString().split("impl.");
        return text[text.length - 1];
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

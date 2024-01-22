package tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import tests.helpers.TestData;
import tests.helpers.TestUtils;
import utils.api.APIServices;
import utils.reports.ReportInfo;
import utils.runner.BrowserManager;
import utils.runner.LoginUtils;
import utils.runner.ProjectProperties;
import utils.reports.ExceptionListener;
import utils.reports.ReportUtils;
import utils.reports.TracingUtils;

import java.io.IOException;
import java.lang.reflect.Method;

import static utils.reports.LoggerUtils.*;

@Listeners(ExceptionListener.class)
abstract class BaseTest {
    private final Playwright playwright = Playwright.create();;
    private final Browser browser = BrowserManager.createBrowser(playwright);;
    private BrowserContext context;
    private Page page;

    @BeforeSuite
    void launchBrowser(ITestContext testContext) {
        LoginUtils.loginAndCollectCookies();

        logInfo(ReportUtils.getReportHeader());

        if(playwright != null) {
            logInfo("Playwright " + ReportInfo.getPlaywrightId(playwright) + " created.");
        } else {
            logFatal("FATAL: Playwright is NOT created\n");
            System.exit(1);
        }

        if (browser.isConnected()) {
            logInfo("Browser " + browser.browserType().name().toUpperCase() + " "
                    + ReportInfo.getBrowserId(browser) + " launched.\n");
        } else {
            logFatal("FATAL: Browser " + browser.browserType().name().toUpperCase() + " is NOT connected\n");
            System.exit(1);
        }

        logInfo(ReportUtils.getEndLine());
    }

    @BeforeMethod
    void createContextAndPage(Method method) {
        logInfo("Run " + ReportUtils.getTestMethodName(method));

        APIServices.cleanData(playwright);
        logInfo("API: Course data cleared");

        context = BrowserManager.createContextWithCookies(browser);
        logInfo("Context created");

        TracingUtils.startTracing(context);
        logInfo("Tracing started");

        page = context.newPage();
        logInfo("Page created");

        page.navigate(ProjectProperties.BASE_URL);

        if(TestUtils.isOnPage(TestData.HOME_END_POINT, page)) {
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
            logInfo("Playwright closed");
        }
    }

    protected Page getPage() {

        return page;
    }
}

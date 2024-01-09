package tests;

import com.microsoft.playwright.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.BrowserManager;
import utils.ProjectProperties;
import utils.ReportUtils;

import java.lang.reflect.Method;
import java.nio.file.Paths;


@Listeners(utils.ExceptionListener.class)
public abstract class BaseTest {
    private final Playwright playwright = Playwright.create();
    private final Browser browser = BrowserManager.createBrowser(playwright);
    private BrowserContext context;
    protected Page page;
    public static Logger log = LogManager.getLogger();

    @BeforeSuite
    protected void launchBrowser(ITestContext testContext) {
        log.info(ReportUtils.getReportHeader(testContext));
        assert browser != null : "ERROR: Browser is null";
        if (browser.isConnected()) {
            log.info("BROWSER " + ProjectProperties.BROWSER_TYPE_NAME.toUpperCase() + " LAUNCHED\n");
        }
    }

    @BeforeMethod
    protected void createContextAndPage(Method method) {
        log.info("RUN " + this.getClass().getName() + "." + method.getName());
        assert browser != null : "ERROR: Browser is null";
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(ProjectProperties.SCREEN_SIZE_WIDTH, ProjectProperties.SCREEN_SIZE_HEIGHT)
                .setStorageStatePath(Paths.get("src/test/resources/state.json"))
        );
        context.tracing().start(
                new Tracing.StartOptions()
                        .setScreenshots(true)
                        .setSnapshots(true)
                        .setSources(true)
        );
        page = context.newPage();
        log.info("CONTEXT AND PAGE CREATED");
        page.navigate(ProjectProperties.BASE_URL);
        log.info("BASE URL OPENED");
//        login();
        log.info("LOGIN SUCCESSFUL");
    }

    @AfterMethod
    protected void closeContext(Method method, ITestResult testResult) {
        log.info(ReportUtils.getTestStatistics(method, testResult));
        Tracing.StopOptions tracingStopOptions = null;
        String classMethodName = this.getClass().getName() + method.getName();
        if (!testResult.isSuccess()) {
            tracingStopOptions = new Tracing.StopOptions()
                    .setPath(Paths.get("testTracing/" + classMethodName + ".zip"));
            log.info("TRACING SAVED");
        }
        context.tracing().stop(
                tracingStopOptions
        );

        page.close();
        context.close();
        log.info("CONTEXT AND PAGE CLOSED" + ReportUtils.END_LINE);
    }

    @AfterSuite
    protected void closeBrowser() {
        assert browser != null : "ERROR: Browser is null";
        browser.close();
        log.info("BROWSER CLOSED");
        playwright.close();
        log.info("PLAYWRIGHT CLOSED");
    }

    private void login() {
        page.locator("//span[text()='Email']/../div/input").fill(ProjectProperties.USERNAME);
        page.locator("//input[@type='password']").fill(ProjectProperties.PASSWORD);
        page.locator("//button[@type='submit']").click();
    }

    public Page getPage() {
        return page;
    }

    public Playwright getPlaywright() {
        return playwright;
    }
}
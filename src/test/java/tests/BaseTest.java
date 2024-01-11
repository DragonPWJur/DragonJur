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
    private Page page;
    public static Logger log = LogManager.getLogger();

    @BeforeSuite
    protected void launchBrowser(ITestContext testContext) {
        log.info(ReportUtils.getReportHeader(testContext));
        if (browser.isConnected()) {
            log.info("BROWSER " + browser.browserType().name().toUpperCase() + " LAUNCHED\n");
        }
    }

    @BeforeMethod
    protected void createContextAndPage(Method method) {
        log.info("RUN " + ReportUtils.getTestMethodName(method));
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(ProjectProperties.SCREEN_SIZE_WIDTH, ProjectProperties.SCREEN_SIZE_HEIGHT)
                .setRecordVideoDir(Paths.get("videos/"))
                .setRecordVideoSize(1280, 720)
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
        login();
        log.info("LOGIN SUCCESSFUL");
    }

    @AfterMethod
    protected void closeContext(Method method, ITestResult testResult) {
        Tracing.StopOptions tracingStopOptions = null;
        String testMethodName = ReportUtils.getTestMethodNameWithInvocationCount(method, testResult);

        log.info(ReportUtils.getTestStatistics(method, testResult));
        page.close();
        log.info("PAGE CLOSED");

        if (!testResult.isSuccess()) {
            if (ProjectProperties.TRACING_MODE) {
                tracingStopOptions = new Tracing.StopOptions()
                        .setPath(Paths.get("testTracing/" + testMethodName + ".zip"));
                log.info("TRACING SAVED");
            }
            if (ProjectProperties.VIDEO_MODE) {
                page.video().saveAs(Paths.get("videos/" + testMethodName + ".webm"));
                log.info("VIDEO SAVED");
            }
        }
        context.tracing().stop(tracingStopOptions);
        page.video().delete();
        context.close();
        log.info("CONTEXT CLOSED" + ReportUtils.END_LINE);
    }

    @AfterSuite
    protected void closeBrowser() {
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
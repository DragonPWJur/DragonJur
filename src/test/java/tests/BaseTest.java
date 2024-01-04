package tests;

import com.microsoft.playwright.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ReportUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.Properties;

@Listeners(utils.ExceptionListener.class)
public abstract class BaseTest {
    private final Playwright playwright = Playwright.create();
    private Browser browser;
    private BrowserContext context;
    private Page page;
    private static Properties properties;
    private static final String ENV_WEB_OPTIONS = "WEB_OPTIONS";
    private static final String ENV_BROWSER_OPTIONS = "BROWSER_OPTIONS";
    private int width;
    private int height;
    private String baseURL;
    public static Logger log = LogManager.getLogger();

    @BeforeSuite
    protected void launchBrowser(ITestContext testContext) {
        log.info(ReportUtils.getReportHeader(testContext));
        init_properties();

        final String browserName = properties.getProperty("browser").trim();
        final boolean isHeadless = Boolean.parseBoolean(properties.getProperty("headless").trim());
        final double isSlow = Double.parseDouble(properties.getProperty("slowMo").trim());


        switch (browserName) {
            case "chromium" -> browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(isHeadless).setSlowMo(isSlow));
            case "firefox" -> browser = playwright.firefox().launch(
                    new BrowserType.LaunchOptions().setHeadless(isHeadless).setSlowMo(isSlow));
            case "safari" -> browser = playwright.webkit().launch(
                    new BrowserType.LaunchOptions().setHeadless(isHeadless).setSlowMo(isSlow));
            case "chrome" -> browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(isHeadless).setSlowMo(isSlow));
            default -> System.out.println("Please enter the right browser name...");
        }

        width = Integer.parseInt(properties.getProperty("width"));
        height = Integer.parseInt(properties.getProperty("height"));
        baseURL = properties.getProperty("base_url");
        log.info("BROWSER " + browserName.toUpperCase() + " LAUNCHED\n");
    }

    @BeforeMethod
    protected void createContextAndPage(Method method) {
        log.info("RUN " + this.getClass().getName() + "." +  method.getName());
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        context.tracing().start(
                new Tracing.StartOptions()
                        .setScreenshots(true)
                        .setSnapshots(true)
                        .setSources(true)
        );
        page = context.newPage();
        log.info("CONTEXT AND PAGE CREATED");

        page.navigate(baseURL);
        login();
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
        browser.close();
        log.info("BROWSER CLOSED");
        playwright.close();
    }

    private static void init_properties() {
        if (properties == null) {
            properties = new Properties();
            if (isServerRun()) {

                if (System.getenv(ENV_BROWSER_OPTIONS) != null) {
                    for (String option : System.getenv(ENV_BROWSER_OPTIONS).split(";")) {
                        String[] browserOptionArr = option.split("=");
                        properties.setProperty(browserOptionArr[0], browserOptionArr[1]);
                    }
                }

                if (System.getenv(ENV_WEB_OPTIONS) != null) {
                    for (String option : System.getenv(ENV_WEB_OPTIONS).split(";")) {
                        String[] webOptionArr = option.split("=");
                        properties.setProperty(webOptionArr[0], webOptionArr[1]);
                    }
                }

            } else {
                try {
                    InputStream inputStream = BaseTest.class.getClassLoader().getResourceAsStream("config.properties");
                    if (inputStream == null) {
                        System.out.println("ERROR: The \u001B[31mconfig.properties\u001B[0m file not found.");
                        System.out.println("You need to create it from config.properties.TEMPLATE file.");
                        System.exit(1);
                    }
                    properties.load(inputStream);
                } catch (IOException ignore) {
                }
            }
        }
    }

    static boolean isServerRun() {
        return System.getenv("CI_RUN") != null;
    }

    private void login() {
        page.locator("//span[text()='Email']/../div/input").fill(properties.getProperty("username"));
        page.locator("//input[@type='password']").fill(properties.getProperty("password"));
        page.locator("//button[@type='submit']").click();
    }

    public Page getPage() {
        return page;
    }

    public Playwright getPlaywright() {
        return playwright;
    }

    public String getBaseUrl() {
        return baseURL;
    }
}
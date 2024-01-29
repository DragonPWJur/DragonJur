package utils.runner;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.json.JSONObject;
import utils.reports.LoggerInfo;
import utils.reports.ReportUtils;
import utils.reports.TracingUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static utils.reports.LoggerUtils.*;

public final class LoginUtils {
    public static final String COOKIES_FILE_PATH = "src/test/resources/state.json";
    private static final String HOME_PAGE_URL = ProjectProperties.BASE_URL + "/home";
    private static final String SIGN_IN_URL = ProjectProperties.BASE_URL + "/sign-in";

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;

    private static String userToken;

    public static String getUserToken() {

        return userToken;
    }

    public static void loginAndCollectCookies() {
        setUpPlaywright();
        loginOnceAndStoreCookies();

        parseUserToken();
        logInfo("Login context: User token extracted from cookies" + ReportUtils.getEndLine());
    }

    private static void loginOnceAndStoreCookies() {
        boolean isLogged = false;
        try {
            login();

            if (isOnHomePage()) {
                isLogged = true;
                logInfo("Login context: Login successful.");

                context.storageState(getStorageStateOptions());
                logInfo("Login context: Cookies collected.");
            }

        } catch (Exception e) {
            logError("ERROR: Unsuccessful login");
            e.printStackTrace();

        } finally {
            page.close();
            logInfo("Login context: Page closed");

            TracingUtils.stopTracingForUILogin(context, isLogged);
            logInfo("Login context: Tracing stopped.");

            tearDownPlaywright();

            if (!isLogged) {
                logFatal("FATAL: Unsuccessful termination.\n");
                System.exit(1);
            }
        }
    }

    private static void setUpPlaywright() {
        playwright = Playwright.create();
        logInfo("Login context: Playwright " + LoggerInfo.getPlaywrightId(playwright) + " created.");

        browser = BrowserManager.createBrowser(playwright);
        logInfo("Login context: Browser " + browser.browserType().name() + " "
                + LoggerInfo.getBrowserId(browser) + " is launched");

        context = BrowserManager.createContext(browser);
        logInfo("Login context: Login Context created.");

        TracingUtils.startTracing(context);
        logInfo("Login context: Tracing started.");

        page = context.newPage();
        logInfo("Login context: Page created.");
    }

    private static void tearDownPlaywright() {
        if(context != null) {
            context.close();
            logInfo("Login context: Login Context closed");
        }

        if (browser != null) {
            browser.close();
            logInfo("Login context: Browser closed");
        }

        if (playwright != null) {
            playwright.close();
            logInfo("Login context: Playwright closed");
        }
    }

    private static void navigateToBaseUrl() {
        page.navigate(ProjectProperties.BASE_URL);
        logInfo("Login context: Base URL opened.");
    }

    private static boolean isOnHomePage() {
        if(!page.url().equals(HOME_PAGE_URL)) {
            page.waitForURL(HOME_PAGE_URL);
        } else {
            logInfo("Login context: Landed on '/home'");

            return true;
        }

        return page.url().equals(HOME_PAGE_URL);
    }

    private static boolean isOnSignInPage() {
        int count = 3;
        while (count > 0) {
            if (!page.url().equals(SIGN_IN_URL)) {
                page.waitForURL(SIGN_IN_URL);
                count--;
            } else {
                logInfo("Login context: Landed on '/sign-in'");
                page.waitForLoadState();

                return true;
            }
        }

        return page.url().equals(SIGN_IN_URL);
    }

    private static void login() {
        navigateToBaseUrl();

        if (isOnSignInPage()) {
            page.fill("form input:only-child", ProjectProperties.USERNAME);
            page.fill("input[type='password']", ProjectProperties.PASSWORD);
            page.click("button[type='submit']");

            logInfo("Login context: Fill user credentials.");
        } else {
            logFatal("FATAL: NOT on SignIn Page. Unable to login.");
        }
    }

    private static BrowserContext.StorageStateOptions getStorageStateOptions() {
        return new BrowserContext
                .StorageStateOptions()
                .setPath(Paths.get(COOKIES_FILE_PATH));
    }

    private static void parseUserToken() {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(COOKIES_FILE_PATH)));
            JSONObject apiLoginResponseJSON = new JSONObject(jsonString);

            String jsonValue = apiLoginResponseJSON
                    .getJSONArray("origins")
                    .getJSONObject(0)
                    .getJSONArray("localStorage")
                    .getJSONObject(2)
                    .getString("value");

            userToken = new JSONObject((new JSONObject(jsonValue))
                    .getString("auth"))
                    .getString("accessToken");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

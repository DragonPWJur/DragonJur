package utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static utils.LoggerUtils.*;

public class LoginUtils {
    public static final String COOKIES_FILE_PATH = "src/test/resources/state.json";
    private static final String HOME_PAGE_URL = ProjectProperties.BASE_URL + TestData.HOME_END_POINT;
    private static final String SIGN_IN_URL = ProjectProperties.BASE_URL + TestData.SIGN_IN_END_POINT;
    private static String userToken;

    static String getUserToken() {

        return userToken;
    }

    public static void loginAndCollectCookies(Playwright playwright, Browser browser) {
        BrowserContext context = BrowserManager.createContext(browser);
        log("Login context created.");

        TracingUtils.startTracing(context);
        log("Tracing started.");

        Page page = context.newPage();
        log("Page created.");

        page.navigate(ProjectProperties.BASE_URL);
        log("Base URL opened.");

        boolean isLogged = false;
        try {
            login(page);
            page.waitForURL(HOME_PAGE_URL);

            if (page.url().equals(HOME_PAGE_URL)) {
                isLogged = true;
                log("Login successful.");

                context.storageState(getStorageStateOptions());
                log("Cookies collected.");
            }
        } catch (Exception e) {
            logError("ERROR: Unsuccessful login");
            e.printStackTrace();
        } finally {
            page.close();
            log("Page for cookies closed");

            TracingUtils.stopTracingForUILogin(page, context);

            context.close();
            log("Context for cookies closed");

            browser.close();
            log("Browser closed");

            playwright.close();
            log("Playwright closed");

            if (!isLogged) {
                logFatal("FATAL: Unsuccessful termination.\n");
                System.exit(1);
            }
        }
    }

    private static BrowserContext.StorageStateOptions getStorageStateOptions() {
        return new BrowserContext
                .StorageStateOptions()
                .setPath(Paths.get(COOKIES_FILE_PATH));
    }

    private static void login(Page page) {
        int count = 3;
        while (count > 0) {
            if (!page.url().equals(SIGN_IN_URL)) {
                page.waitForURL(SIGN_IN_URL);
                count--;
            } else {
                log("Landed on " + TestData.SIGN_IN_END_POINT);
            }
        }

        page.fill("form input:only-child", ProjectProperties.USERNAME);
        page.fill("input[type='password']", ProjectProperties.PASSWORD);
        page.click("button[type='submit']");
    }

    public static void parseUserToken() {
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

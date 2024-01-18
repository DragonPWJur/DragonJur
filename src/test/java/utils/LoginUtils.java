package utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static tests.BaseTest.waitForPageLoad;
import static utils.LoggerUtils.log;
import static utils.LoggerUtils.logError;

public class LoginUtils {
    private static boolean tracingSave = false;
    public static String cookiesFilePath = "src/test/resources/state.json";
    private static String userToken;

    public static String getUserToken() {
        return userToken;
    }

    public static boolean getTracingSave() {
        return tracingSave;
    }

    public static void collectCookies(Browser browser) {
        BrowserContext context = BrowserManager.createContext(browser);
        log("Context for cookies created");

        TracingUtils.startTracing(context);
        log("Tracing for cookies started");

        Page page = context.newPage();
        log("Page for cookies created");

        page.navigate(ProjectProperties.BASE_URL);
        log("Base URL for cookies opened");

        try {
            login(page);
            if (page.url().equals(ProjectProperties.BASE_URL + TestData.HOME_END_POINT)) {
                log("Login successful");
                context.storageState(LoginUtils.storageStateOptions());
                log("Cookies collected");
            }
        } catch (Exception e) {
            logError("ERROR: Unsuccessful login");
            tracingSave = true;
        } finally {
            page.close();
            log("Page for cookies closed");

            TracingUtils.stopTracingForUILogin(page, context);

            context.close();
            log("Context for cookies closed");
        }
    }

    public static BrowserContext.StorageStateOptions storageStateOptions() {
        return new BrowserContext
                .StorageStateOptions()
                .setPath(Paths.get(cookiesFilePath));
    }

    public static void login(Page page) {
        if (!page.url().equals(ProjectProperties.BASE_URL + TestData.SIGN_IN_END_POINT)) {
            waitForPageLoad(page, TestData.SIGN_IN_END_POINT);
        } else {
            log("Landed on " + TestData.SIGN_IN_END_POINT);
        }

        page.fill("form input:only-child", ProjectProperties.USERNAME);
        page.fill("input[type='password']", ProjectProperties.PASSWORD);
        page.click("button[type='submit']");

        waitForPageLoad(page, TestData.HOME_END_POINT);
    }

    public static void parseUserToken() {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(cookiesFilePath)));
            JSONObject apiLoginResponseJSON = new JSONObject(jsonString);
            String jsonValue = apiLoginResponseJSON.getJSONArray("origins").getJSONObject(0).getJSONArray("localStorage").getJSONObject(2).getString("value");
            userToken = new JSONObject((new JSONObject(jsonValue)).getString("auth")).getString("accessToken");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

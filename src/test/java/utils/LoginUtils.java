package utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

import java.nio.file.Paths;

import static tests.BaseTest.waitForPageLoad;
import static utils.LoggerUtils.log;
import static utils.LoggerUtils.logError;

public class LoginUtils {
    private static boolean tracingSave = false;

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
            }
            context.storageState(LoginUtils.storageStateOptions());
            log("Cookies collected");
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
                .setPath(Paths.get("src/test/resources/state.json"));
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
}
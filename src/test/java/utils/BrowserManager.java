package utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

import java.nio.file.Paths;

public class BrowserManager {
    public static Browser createBrowser(Playwright playwright, Class<?> testClass) {

        switch (ProjectProperties.BROWSER_TYPE_NAME) {
            case "chromium" -> {
                LoggerUtils.log("Browser Chromium for " + testClass.getSimpleName() + " launched");
                return playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(ProjectProperties.IS_HEADLESS)
                        .setSlowMo(ProjectProperties.IS_SLOW));
            }
            case "firefox" -> {
                LoggerUtils.log("Browser Firefox for " + testClass.getSimpleName() + " launched");
                return playwright.firefox().launch(new BrowserType.LaunchOptions()
                        .setHeadless(ProjectProperties.IS_HEADLESS)
                        .setSlowMo(ProjectProperties.IS_SLOW));
            }
            case "webkit" -> {
                LoggerUtils.log("Browser Webkit for " + testClass.getSimpleName() + " launched");
                return playwright.webkit().launch(new BrowserType.LaunchOptions()
                        .setHeadless(ProjectProperties.IS_HEADLESS)
                        .setSlowMo(ProjectProperties.IS_SLOW));
            }
            default -> {
                LoggerUtils.logWarning("WARNING: Default browser CHROMIUM launched \n");
                return playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(ProjectProperties.IS_HEADLESS)
                        .setSlowMo(ProjectProperties.IS_SLOW));
            }
        }
    }

    public static BrowserContext createContext(Browser browser) {
        return browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(ProjectProperties.SCREEN_SIZE_WIDTH, ProjectProperties.SCREEN_SIZE_HEIGHT)
                .setRecordVideoDir(Paths.get("videos/"))
                .setRecordVideoSize(1280, 720)
        );
    }

    public static BrowserContext createContextWithCookies(Browser browser) {
        return browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(ProjectProperties.SCREEN_SIZE_WIDTH, ProjectProperties.SCREEN_SIZE_HEIGHT)
                .setRecordVideoDir(Paths.get("videos/"))
                .setRecordVideoSize(1280, 720)
                .setStorageStatePath(Paths.get(LoginUtils.COOKIES_FILE_PATH))
        );
    }
}
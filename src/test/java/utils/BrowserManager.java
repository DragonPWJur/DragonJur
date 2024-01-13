package utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

import java.util.Arrays;

public class BrowserManager {
    PropertyType.logEnvBrowserProperties();


    private static final Object[] browserOptions = {
            PropertyType.Browser.BROWSER_TYPE_NAME,
            PropertyType.Browser.BASE_URL,
            PropertyType.Browser.SCREEN_SIZE_WIDTH,
            PropertyType.Browser.SCREEN_SIZE_HEIGHT,
            PropertyType.Browser.IS_HEADLESS,
            PropertyType.Browser.SLOW_MO
    };

    public static Browser createBrowser(Playwright playwright) {
        LoggerUtils.log("Inside createBrowser(playwright)");
        LoggerUtils.log("browserOptions inside BrowserManager = " + Arrays.toString(browserOptions));

        LoggerUtils.log("BROWSER_TYPE_NAME = " + PropertyType.Browser.BROWSER_TYPE_NAME);

        switch (PropertyType.Browser.BROWSER_TYPE_NAME) {
            case "chromium" -> {
                LoggerUtils.log("Inside chromium");
                return playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(PropertyType.Browser.IS_HEADLESS)
                        .setSlowMo(PropertyType.Browser.SLOW_MO));
            }
            case "firefox" -> {
                return playwright.firefox().launch(new BrowserType.LaunchOptions()
                        .setHeadless(PropertyType.Browser.IS_HEADLESS)
                        .setSlowMo(PropertyType.Browser.SLOW_MO));
            }
            case "webkit" -> {
                return playwright.webkit().launch(new BrowserType.LaunchOptions()
                        .setHeadless(PropertyType.Browser.IS_HEADLESS)
                        .setSlowMo(PropertyType.Browser.SLOW_MO));
            }
            default -> {
                LoggerUtils.logWarning("!!! DEFAULT BROWSER CHROMIUM LAUNCHED\n !!!");
                return playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(PropertyType.Browser.IS_HEADLESS)
                        .setSlowMo(PropertyType.Browser.SLOW_MO));
            }
        }
    }
}

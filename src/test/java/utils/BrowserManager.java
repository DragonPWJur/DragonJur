package utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public class BrowserManager {
    public static Browser createBrowser(Playwright playwright) {
        LoggerUtils.log("Inside createBrowser(playwright)");
        LoggerUtils.log(PropertyType.Browser.BROWSER_TYPE_NAME);

        switch (PropertyType.Browser.BROWSER_TYPE_NAME) {
            case "chromium" -> {
                LoggerUtils.log("Inside chromium");
                return playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(PropertyType.Browser.IS_HEADLESS)
                        .setSlowMo(PropertyType.Browser.IS_SLOW));
            }
            case "firefox" -> {
                return playwright.firefox().launch(new BrowserType.LaunchOptions()
                        .setHeadless(PropertyType.Browser.IS_HEADLESS)
                        .setSlowMo(PropertyType.Browser.IS_SLOW));
            }
            case "webkit" -> {
                return playwright.webkit().launch(new BrowserType.LaunchOptions()
                        .setHeadless(PropertyType.Browser.IS_HEADLESS)
                        .setSlowMo(PropertyType.Browser.IS_SLOW));
            }
            default -> {
                LoggerUtils.logWarning("!!! DEFAULT BROWSER CHROMIUM LAUNCHED\n !!!");
                return playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(PropertyType.Browser.IS_HEADLESS)
                        .setSlowMo(PropertyType.Browser.IS_SLOW));
            }
        }
    }
}

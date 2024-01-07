package utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

import static tests.BaseTest.log;

public class BrowserManager {
    public static Browser createBrowser(Playwright playwright) {
        Browser browser = null;

        switch (ProjectProperties.BROWSER_TYPE_NAME) {
            case "chromium" ->
                    browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                            .setHeadless(ProjectProperties.IS_HEADLESS)
                            .setSlowMo(ProjectProperties.IS_SLOW)
                    );
            case "firefox" ->
                    browser = playwright.firefox().launch(new BrowserType.LaunchOptions()
                            .setHeadless(ProjectProperties.IS_HEADLESS)
                            .setSlowMo(ProjectProperties.IS_SLOW)
                    );
            case "webkit" ->
                    browser = playwright.webkit().launch(new BrowserType.LaunchOptions()
                            .setHeadless(ProjectProperties.IS_HEADLESS)
                            .setSlowMo(ProjectProperties.IS_SLOW)
                    );
            default -> log.info("Please enter the right browser type name...");
        }
        return browser;
    }
}
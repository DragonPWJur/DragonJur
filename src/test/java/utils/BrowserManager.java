package utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;

public class BrowserManager {
    public static Browser createBrowser(Playwright playwright) {
        return BrowserFactory.valueOf(ProjectProperties.BROWSER_TYPE_NAME.toUpperCase())
                .createInstance(playwright);
    }
}
package utils.reports;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;

public final class LoggerInfo {

    public static String getPlaywrightId(Playwright playwright) {
        String[] text = playwright.toString().split("impl.");

        return text[text.length - 1];
    }

    public static String getBrowserId(Browser browser) {
        String[] text = browser.toString().split("impl.");

        return text[text.length - 1];
    }
}

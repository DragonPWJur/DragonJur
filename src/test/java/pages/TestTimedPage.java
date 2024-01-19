package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public final class TestTimedPage extends BaseTestsPage<TestTimedPage> {

    private final Locator timer = locator("header div div:has(button)>div");

    public TestTimedPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public Locator getTimer() {
        return timer;
    }
}
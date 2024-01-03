package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class TestsPage extends BaseLocator {

    public Locator headerQuestionsCount = getPage().locator("//header/div/div/span[2]");

    protected TestsPage(Page page, Playwright playwright) {
        super(page, playwright);
    }
}
package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class TestsPage extends SideMenuPage {

    private final Locator testQuestion = getPage().locator("#root form span");
    private final Locator testRadioButtons = radio();

    public TestsPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public int countTestRadioButtons() {
        return testRadioButtons.count();
    }

    public Locator getTestQuestion() {
        return testQuestion;
    }
}

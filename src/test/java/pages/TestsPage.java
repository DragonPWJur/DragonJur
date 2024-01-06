package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

public class TestsPage extends BaseLocator {

    public Locator testQuestion = getPage().locator("#root form span");
    public Locator testRadioButtons = getPage().getByRole(AriaRole.RADIO);

    protected TestsPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public int getTestRadioButtonsCount() {
        return testRadioButtons.count();
    }
}
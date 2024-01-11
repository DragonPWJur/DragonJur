package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

public class TestsPage extends BaseLocator {

    private final Locator testQuestion = getPage().locator("#root form span");
    private final Locator testRadioButtons = radio();

    public TestsPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public int countTestRadioButtons() {
        return testRadioButtons.count();
    }

    public Locator getListTestRadioButtons() {
        return waitForListOfElementsLoaded(testRadioButtons);
    }

    public Locator getTestQuestion() {
        return testQuestion;
    }
}
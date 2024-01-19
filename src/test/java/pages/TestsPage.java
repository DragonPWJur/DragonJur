package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class TestsPage extends BaseSideMenu {

    private final Locator testQuestion = getPage().locator("#root form span");
    private final Locator testRadioButtons = radio();

    public TestsPage(Page page) {
        super(page);
    }

    public int countTestRadioButtons() {
        return testRadioButtons.count();
    }

    public Locator getTestQuestion() {
        return testQuestion;
    }
}

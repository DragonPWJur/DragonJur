package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class TestResultPage extends SideMenuPage {

    private final Locator closeTheTestButton = exactButton("Close the test");

    public TestResultPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public TestListPage clickCloseTheTestButton() {
        closeTheTestButton.click();

        return new TestListPage(getPage(), getPlaywright());
    }
}

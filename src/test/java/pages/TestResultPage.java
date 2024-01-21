package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class TestResultPage extends BaseSideMenu {

    private final Locator closeTheTestButton = exactButton("Close the test");

    public TestResultPage(Page page) {
        super(page);
    }

    public TestListPage clickCloseTheTestButton() {
        closeTheTestButton.click();

        return new TestListPage(getPage());
    }
}

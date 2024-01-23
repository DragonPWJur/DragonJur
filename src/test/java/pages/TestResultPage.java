package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.constants.Constants;

public final class TestResultPage extends BaseLocator<TestResultPage> {

    private final Locator closeTheTestButton = exactButton("Close the test");

    public TestResultPage(Page page) {
        super(page);
    }

    @Override
    public TestResultPage createPage() {

        return init(new TestResultPage(getPage()), Constants.TEST_RESULT_END_POINT);
    }

    public TestListPage clickCloseTheTestButton() {
        closeTheTestButton.click();

        return new TestListPage(getPage()).createPage();
    }
}

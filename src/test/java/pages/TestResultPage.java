package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import pages.constants.Constants;

public final class TestResultPage extends BaseLocator<TestResultPage> {
    private final Locator closeTheTestButton = exactButton("Close the test");

    TestResultPage(Page page) {
        super(page);
    }

    @Override
    public TestResultPage init() {

        return createPage(new TestResultPage(getPage()), Constants.TEST_RESULT_END_POINT);
    }

    @Step("Click 'Close the test' button")
    public TestListPage clickCloseTheTestButton() {
        closeTheTestButton.click();

        return new TestListPage(getPage()).init();
    }
}

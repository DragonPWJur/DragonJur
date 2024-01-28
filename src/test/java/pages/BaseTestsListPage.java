package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

abstract class BaseTestsListPage<TPage> extends BaseSideMenu<TPage> {
    private final Locator generateAndStartButton = button("Generate & Start");

    BaseTestsListPage(Page page) {
        super(page);
    }

    @Step("Click 'Generate and Start' button")
    public TestTutorPage clickGenerateAndStartTutorTestButton() {
        generateAndStartButton.click();

        return new TestTutorPage(getPage()).init();
    }

    @Step("Click 'Generate and Start' button")
    public TestListPage clickGenerateAndStartTimedTestButton() {
        generateAndStartButton.click();

        return new TestListPage(getPage()).init();
    }
}

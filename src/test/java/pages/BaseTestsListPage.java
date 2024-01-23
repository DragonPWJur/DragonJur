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
    public TPage clickGenerateAndStartButton() {
        generateAndStartButton.click();

        return init();
    }
}

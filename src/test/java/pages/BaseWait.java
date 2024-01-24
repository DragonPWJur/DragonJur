package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import utils.runner.ProjectProperties;

abstract class BaseWait<TPage> extends BasePage<TPage> {

    BaseWait(Page page) {
        super(page);
    }

    protected void waitWithTimeout(int timeout) {
        getPage().waitForTimeout(timeout);
    }

    protected void waitForPageLoad() {
        getPage().waitForLoadState();
    }

    protected void waitForPageLoad(String endPoint) {
        getPage().waitForURL(ProjectProperties.BASE_URL + endPoint);
    }

    protected void waitForLocator(String css, int timeout) {
        getPage().locator(css)
                .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(timeout));
    }
}

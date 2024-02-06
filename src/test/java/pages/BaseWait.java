package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

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

    protected void waitForLocator(Locator locator, int timeout) {
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(timeout));
    }
}

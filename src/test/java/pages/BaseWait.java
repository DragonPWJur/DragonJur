package pages;

import com.microsoft.playwright.Page;

abstract class BaseWait<TPage> extends BasePage<TPage> {

    protected BaseWait(Page page) {
        super(page);
    }

    protected void waitWithTimeout(int timeout) {
        getPage().waitForTimeout(timeout);
    }

    protected void waitForPageLoad() {
        getPage().waitForLoadState();
    }
}

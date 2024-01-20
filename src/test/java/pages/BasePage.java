package pages;

import com.microsoft.playwright.Page;

abstract class BasePage {
    private final Page page;

    protected BasePage(Page page) {
        this.page = page;
    }

    Page getPage() {
        return page;
    }

    public void waitWithTimeout(int timeout) {
        getPage().waitForTimeout(timeout);
    }
}
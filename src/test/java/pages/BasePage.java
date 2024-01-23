package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import pages.constants.Constants;
import utils.runner.ProjectProperties;

import static utils.reports.LoggerUtils.logInfo;

abstract class BasePage<TPage> {
    private final Page page;

    protected BasePage(Page page) {
        this.page = page;
    }

    abstract TPage createPage();

    Page getPage() {

        return page;
    }

    protected TPage init(TPage page, String endPoint) {
        if (isOnPage(endPoint)) {

            return page;
        }

        return null;
    }


    private boolean isOnPage(String endPoint) {
        String pageUrl = ProjectProperties.BASE_URL + endPoint;

        if (!getPage().url().contains(pageUrl) || getPage().content().isEmpty()) {
            getPage().waitForTimeout(2000);
        } else {
            getPage().onLoad(p -> getPage().content());
            if(!getPage().content().isEmpty()) {
                logInfo("On page '" + endPoint + "'");
            }

            return true;
        }

        return !getPage().content().isEmpty();
    }
}
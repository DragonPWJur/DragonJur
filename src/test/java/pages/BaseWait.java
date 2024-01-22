package pages;

import com.microsoft.playwright.Page;
import utils.runner.ProjectProperties;

import static utils.reports.LoggerUtils.logInfo;

abstract class BaseWait extends BasePage {

    protected BaseWait(Page page) {
        super(page);
    }

    protected void waitWithTimeout(int timeout) {

        getPage().waitForTimeout(timeout);
    }

    protected void waitForPageLoad() {

        getPage().waitForLoadState();
    }

    protected boolean isOnPage(String endPoint) {
        String pageUrl = ProjectProperties.BASE_URL + endPoint;
        int count = 3;
        while (count > 0) {
            if (!getPage().url().equals(pageUrl)) {
                getPage().waitForURL(pageUrl);
                count--;
            } else {
                logInfo("On page '" + endPoint + "'");
                waitForPageLoad();

                return true;
            }
        }

        return getPage().url().equals(pageUrl);
    }

    protected HomePage isOnHomePage() {
        if(isOnPage("/home")) {

            return new HomePage(getPage());
        }

        return null;
    }

    protected StudyGuidePage isOnStudyGuidePage() {
        if(isOnPage("/study-guide")) {

            return new StudyGuidePage(getPage());
        }

        return null;
    }

    protected TestListPage isOnTestListPage() {
        if(isOnPage("/test-list")) {

            return new TestListPage(getPage());
        }

        return null;
    }

    protected FlashcardPacksPage isOnFlashcardsPackPage() {
        if(isOnPage("/flashcard-packs")) {

            return new FlashcardPacksPage(getPage());
        }

        return null;
    }

    protected MnemonicCardListPage isOnMnemonicCardListPage() {
        if(isOnPage("/mnemoniccard-list")) {

            return new MnemonicCardListPage(getPage());
        }

        return null;
    }

    protected PerformancePage isOnPerformancePage() {
        if(isOnPage("/performance")) {

            return new PerformancePage(getPage());
        }

        return null;
    }

    protected ProfilePage isOnProfilePage() {
        if(isOnPage("/profile")) {

            return new ProfilePage(getPage());
        }

        return null;
    }
}

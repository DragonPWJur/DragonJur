package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PreconditionPage extends BasePage {

    public PreconditionPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public void startTestDomain2(String numberOfQuestions) {

        new HomePage(getPage(), getPlaywright())
                .clickTestsMenu()
                .cancelDialogIfVisible()
                .clickDomainsButton()
                .clickTestDomain2CheckBox()
                .inputNumberOfQuestions(numberOfQuestions)
                .clickGenerateAndStartButton();
    }

    public String getCurrentNumberOfCardForRechecking() {
        return new HomePage(getPage(), getPlaywright())
                .clickFlashcardsMenu()
                .getNumberMarkedForRechecking();
    }

    public void clickRemoveFromFlashcardsButtonIfVisible() {
        new TestTutorPage(getPage(), getPlaywright())
                .clickRemoveFromFlashcardsButtonIfVisible();
    }

    public void endTest() {
        new TestTutorPage(getPage(), getPlaywright())
                .clickEndButton()
                .clickYesButton()
                .clickSkipButton()
                .clickCloseTheTestButton();
    }

    public void resetCourseResults() {
        new HomePage(getPage(), getPlaywright())
                .clickProfileMenu()
                .clickResetCourseResultsButton()
                .clickYesButton()
                .clickHomeMenu();
    }

    public void startTest(String numberOfQuestions) {
        new HomePage(getPage(), getPlaywright())
                .clickTestsMenu()
                .cancelDialogIfVisible()
                .clickDomainsButton()
                .clickRandomCheckbox()
                .inputNumberOfQuestions(numberOfQuestions)
                .clickGenerateAndStartButton();
    }

    public void startFlashcardPack(int randomIndex) {
        FlashcardPacksPage flashcardPacksPage = new HomePage(getPage(), getPlaywright()).clickFlashcardsMenu();

        flashcardPacksPage
                .clickRandomFlashcardPack(randomIndex)
                .clickGotButton()
                .clickFlashcardsBackButton()
                .clickYesButton();
    }
}

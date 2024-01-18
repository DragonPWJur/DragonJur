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
        String numberMarkedForRechecking = new HomePage(getPage(), getPlaywright())
                .clickFlashcardsMenu()
                .getNumberMarkedForRechecking();

        new TestsPage(getPage(), getPlaywright()).clickHomeMenu();
        return numberMarkedForRechecking;
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

    public void startTest(String numberOfQuestions) {
        new HomePage(getPage(), getPlaywright())
                .clickHomeMenu()
                .clickTestsMenu()
                .cancelDialogIfVisible()
                .clickDomainsButton()
                .clickRandomCheckbox()
                .inputNumberOfQuestions(numberOfQuestions)
                .clickGenerateAndStartButton();
    }

    public int getCurrentNumberOfFlashcardPack() {
        int flashcardsPackRandomNumber = new HomePage(getPage(), getPlaywright())
                .clickFlashcardsMenu()
                .getNumberOfFlashcardsPack();

        new FlashcardPacksPage(getPage(), getPlaywright()).clickHomeMenu();

        return flashcardsPackRandomNumber;
    }

    public void startFlashcardPackAndGoBack(int index) {
        new HomePage(getPage(), getPlaywright())
                .clickHomeMenu()
                .clickFlashcardsMenu()
                .clickNthFlashcardPack(index)
                .clickGotButtonIfVisible()
                .clickFlashcardsBackButton()
                .clickYesButton()
                .clickHomeMenu();
    }

    public boolean checkIfListCheckBoxesIsNotEmptyAndAllUnchecked() {

        HomePage homePage = new HomePage(getPage(), getPlaywright());
        if (homePage.isListCheckBoxesNotEmpty()) {
            return homePage.areAllCheckBoxesUnchecked();
        }
        return false;
    }
}

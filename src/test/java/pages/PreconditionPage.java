package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PreconditionPage extends BasePage {

    public PreconditionPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public String getCurrentNumberOfCardForRechecking() {
        String numberMarkedForRechecking = new HomePage(getPage(), getPlaywright())
                .clickFlashcardsMenu()
                .getNumberMarkedForRechecking();

        new TestsPage(getPage(), getPlaywright()).clickHomeMenu();
        return numberMarkedForRechecking;
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

    public void startFlashcardPackAndGoBack(int index) {
        new HomePage(getPage(), getPlaywright())
                .clickFlashcardsMenu()
                .clickRandomFlashcardPack(index)
                .clickGotButtonIfVisible()
                .clickFlashcardsBackButton()
                .clickYesButton();
    }

    public boolean checkIfListCheckBoxesIsNotEmptyAndAllUnchecked() {

        HomePage homePage = new HomePage(getPage(), getPlaywright());
        if (homePage.isListCheckBoxesNotEmpty()) {
            return homePage.areAllCheckBoxesUnchecked();
        }
        return false;
    }
}

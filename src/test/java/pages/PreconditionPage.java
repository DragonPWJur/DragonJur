package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import utils.TestUtils;

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

    public void startTest(String numberOfQuestions) {
        new HomePage(getPage(), getPlaywright())
                .clickTestsMenu()
                .cancelDialogIfVisible()
                .clickRandomCheckbox()
                .inputNumberOfQuestions(numberOfQuestions)
                .clickGenerateAndStartButton()
        ;
    }

    public void startFlashcardPackAndGoBack(int randomIndex) {
        new HomePage(getPage(), getPlaywright())
                .clickFlashcardsMenu()
                .clickRandomFlashcardPack(randomIndex)
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

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

    public boolean checkIfListCheckBoxesIsNotEmptyAndOneIsChecked() {

        HomePage homePage = new HomePage(getPage(), getPlaywright());
        if (homePage.isListCheckBoxesNotEmpty()) {
            homePage.clickRandomCheckBox();

            return homePage.getListCheckedCheckBoxes().size() == 1;
        }
        return false;
    }

    public boolean checkIfListCheckBoxesIsNotEmptyAndAllCheckBoxesAreChecked() {
        HomePage homePage = new HomePage(getPage(), getPlaywright());

        if (homePage.isListCheckBoxesNotEmpty()) {
            homePage.checkAllCheckBoxes();
            return homePage.areAllCheckBoxesChecked();
        }
        return false;
    }
}

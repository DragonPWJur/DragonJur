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

    public void startTestDomainForStats(String nameTest, String numberOfQuestions) {
        TestListPage testListPage = new HomePage(getPage(), getPlaywright())
                .clickTestsMenu()
                .cancelDialogIfVisible()
                .clickDomainsButton();
        if(nameTest.equals("Automation testing for stats")) {
            testListPage
                    .clickAutomationTestingForStatsCheckBox()
                    .inputNumberOfQuestions(numberOfQuestions)
                    .clickGenerateAndStartButton2();
        } else if(nameTest.equals("History and Civilization for Stats")) {
            testListPage
                    .clickHistoryAndCivilizationForStatsCheckBox()
                    .inputNumberOfQuestions(numberOfQuestions)
                    .clickGenerateAndStartButton2();
        }
    }

    public void passTestAllAnswersCorrect(int numberOfQuestions) {
        TestTutorPage testTutorPage = new TestTutorPage(getPage(), getPlaywright());
        for (int numOfQuestion = 1; numOfQuestion < numberOfQuestions; numOfQuestion++) {
            testTutorPage
                    .clickCorrectAnswerRadioButton()
                    .clickConfirmButton()
                    .clickNextQuestionButton();
        }

        testTutorPage
                .clickCorrectAnswerRadioButton()
                .clickConfirmButton()
                .clickFinishTestButton()
                .clickSkipButton()
                .clickCloseTheTestButton()
                .clickHomeMenu();
    }

    public void passTestOneAnswersIncorrect(int numberOfQuestions) {
        TestTutorPage testTutorPage = new TestTutorPage(getPage(), getPlaywright());
        for (int numOfQuestion = 1; numOfQuestion < numberOfQuestions; numOfQuestion++) {
            testTutorPage
                    .clickCorrectAnswerRadioButton()
                    .clickConfirmButton()
                    .clickNextQuestionButton();
        }

        testTutorPage
                .clickRandomIncorrectAnswer()
                .clickConfirmButton()
                .clickFinishTestButton()
                .clickSkipButton()
                .clickCloseTheTestButton()
                .clickHomeMenu();
    }

    public int checkNumberOfQuestions() {
        int numberOfQuestions = new HomePage(getPage(), getPlaywright())
                .clickPerformanceMenu()
                .getNumberOfQuestions();
        new PerformancePage(getPage(), getPlaywright()).clickHomeMenu();

        return numberOfQuestions;
    }
}

package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import io.qameta.allure.Step;

import java.util.List;

public final class PreconditionPage extends BasePage {

    private int flashcardsPackRandomIndex;
    private String flashcardsPackName;
    private String flashcardsPackCardsAmount;


    public int getFlashcardsPackRandomIndex() {
        return flashcardsPackRandomIndex;
    }

    public String getFlashcardsPackName() {
        return flashcardsPackName;
    }

    public String getFlashcardsPackCardsAmount() {
        return flashcardsPackCardsAmount;
    }

    public PreconditionPage(Page page) {
        super(page);
    }

    @Step("Precondition: Save the initial amount of 'Marked for re-checking' cards.")
    public String getInitialAmountOfCardsMarkedForRechecking() {
        String amountMarkedForRechecking = new HomePage(getPage())
                .clickFlashcardsMenu()
                .getAmountOfCardsMarkedForRechecking();

        new TestsPage(getPage()).clickHomeMenu();

        return amountMarkedForRechecking;
    }
//        new FlashcardPacksPage(getPage(), getPlaywright()).clickHomeMenu();
//        return numberMarkedForRechecking;
//
//    }

//    public void endTest() {
//        new TestTutorPage(getPage())
//                .clickEndButton()
//                .clickYesButton()
//                .clickSkipButton()
//                .clickCloseTheTestButton();
//    }
//
    @Step("Precondition: Start random domain test with {number} question(s).")
    public TestTutorPage startRandomDomainTest(String number) {
        new HomePage(getPage())
                .clickHomeMenu()
                .clickTestsMenu()
                .cancelDialogIfVisible()
                .clickDomainsButtonIfNotActive()
                .clickRandomCheckbox()
                .inputNumberOfQuestions(number)
                .clickGenerateAndStartButton();

        return new TestTutorPage(getPage());
    }

    public void collectRandomFlashcardPackInfo() {
        FlashcardPacksPage flashcardPacksPage = new HomePage(getPage()).clickFlashcardsMenu();

        this.flashcardsPackRandomIndex =  flashcardPacksPage.getRandomPackIndex();
        this.flashcardsPackCardsAmount = flashcardPacksPage.getAmountOfCardsInPack();
        this.flashcardsPackName = flashcardPacksPage.getFlashcardsPackName();

        flashcardPacksPage.clickHomeMenu();
    }

//    public List<Locator> getAllCheckBoxes() {
//
//        return new HomePage(getPage()).getAllCheckboxes();
//    }

//    public void startFlashcardPackAndGoBack(int index) {
//        new HomePage(getPage())
//                .clickHomeMenu()
//                .clickFlashcardsMenu()
//                .clickNthFlashcardPack(index)
//                .clickGotItButtonIfVisible()
//                .clickFlashcardsBackButton()
//                .clickYesButton()
//                .clickHomeMenu();
//    }

    public boolean areAllCheckBoxesUnchecked() {
        return new HomePage(getPage()).areAllCheckBoxesUnchecked();
    }

//    @Step("Start test for the stats")
//    public void startTestDomainForStats(String nameTest, String numberOfQuestions) {
//        TestListPage testListPage = new HomePage(getPage())
//                .clickTestsMenu()
//                .cancelDialogIfVisible()
//                .clickDomainsButton();
//        if(nameTest.equals("Automation testing for stats")) {
//            testListPage
//                    .clickAutomationTestingForStatsCheckBox()
//                    .inputNumberOfQuestions(numberOfQuestions)
//                    .clickGenerateAndStartButton2();
//        } else if(nameTest.equals("History and Civilization for Stats")) {
//            testListPage
//                    .clickHistoryAndCivilizationForStatsCheckBox()
//                    .inputNumberOfQuestions(numberOfQuestions)
//                    .clickGenerateAndStartButton2();
//        }
//    }

//    @Step("Pass the test with the correct answers of {numberOfQuestions} questions")
//    public void passTestAllAnswersCorrect(int numberOfQuestions) {
//        TestTutorPage testTutorPage = new TestTutorPage(getPage());
//        for (int numOfQuestion = 1; numOfQuestion < numberOfQuestions; numOfQuestion++) {
//            testTutorPage
//                    .clickCorrectAnswerRadioButton()
//                    .clickConfirmButton()
//                    .clickNextQuestionButton();
//        }
//
//        testTutorPage
//                .clickCorrectAnswerRadioButton()
//                .clickConfirmButton()
//                .clickFinishTestButton()
//                .clickSkipButton()
//                .clickCloseTheTestButton()
//                .clickHomeMenu();
//    }

    @Step("Pass the test with one wrong answer of {numberOfQuestions} questions\"")
    public void passTestOneAnswersIncorrect(int numberOfQuestions) {
        TestTutorPage testTutorPage = new TestTutorPage(getPage());
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

    @Step("Checking the number of questions on PerformancePage")
    public int checkNumberOfQuestions() {
        int numberOfQuestions = new HomePage(getPage())
                .clickPerformanceMenu()
                .getNumberOfQuestions();
        new PerformancePage(getPage()).clickHomeMenu();

        return numberOfQuestions;
    }

    public boolean checkIfListCheckBoxesIsNotEmptyAndOneIsChecked() {

//    public boolean checkIfListCheckBoxesIsNotEmptyAndOneIsChecked() {
//
//        HomePage homePage = new HomePage(getPage(), getPlaywright());
//        if (homePage.isListCheckBoxesNotEmpty()) {
//            homePage.clickRandomCheckBox();
//
//            return homePage.getListCheckedCheckBoxes().size() == 1;
//        }
//        return false;
//    }
        HomePage homePage = new HomePage(getPage());
//        if (homePage.isListCheckBoxesNotEmpty()) {
//            homePage.clickRandomCheckBox();
//
//            return homePage.getListCheckedCheckBoxes().size() == 1;
//        }
        return false;
    }

//    public boolean checkIfListCheckBoxesIsNotEmptyAndAllCheckBoxesAreChecked() {
//        HomePage homePage = new HomePage(getPage());
//
//        if (homePage.isListCheckBoxesNotEmpty()) {
//            homePage.checkAllCheckBoxes();
//            return homePage.areAllCheckBoxesChecked();
//        }
//        return false;
//    }
}

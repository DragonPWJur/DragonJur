package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import io.qameta.allure.Step;
import tests.helpers.TestData;
import utils.api.APIUtils;

import java.util.List;

public final class PreconditionPage extends BasePage<PreconditionPage> {
    private int flashcardsPackRandomIndex;
    private String flashcardsPackName;
    private String flashcardsPackCardsAmount;
    private int randomIndex;

    public PreconditionPage(Page page) {
        super(page);
    }

    @Override
    public PreconditionPage init() {

        return new PreconditionPage(getPage());
    }

    @Step("Precondition: Collect random index.")
    public int getFlashcardsPackRandomIndex() {

        return flashcardsPackRandomIndex;
    }

    @Step("Precondition: Collect chosen Flashcards pack name.")
    public String getFlashcardsPackName() {

        return flashcardsPackName;
    }

    @Step("Precondition: Collect chosen Flashcards pack cards amount.")
    public String getFlashcardsPackCardsAmount() {

        return flashcardsPackCardsAmount;
    }

    @Step("Precondition: Save the initial Flashcards pack information.")
    public String getInitialAmountOfCardsMarkedForRechecking() {
        final String amountMarkedForRechecking =
                new HomePage(getPage()).init()
                        .clickFlashcardsMenu()
                        .getAmountOfCardsMarkedForRechecking();

        new FlashcardPacksPage(getPage())
                .clickHomeMenu();

        return amountMarkedForRechecking;
    }

    @Step("Precondition: Start random domain test with {number} of question(s).")
    public TestTutorPage startRandomDomainTest(String number) {
        new HomePage(getPage()).init()
                .clickTestsMenu()
                .cancelDialogIfVisible()
                .clickDomainsButtonIfNotActive()
                .clickRandomCheckbox()
                .inputNumberOfQuestions(number)
                .clickGenerateAndStartTutorTestButton();

        return new TestTutorPage(getPage()).init();
    }

    @Step("Precondition: Collect the random index, Flashcards pack name, and initial amount of cards in the pack.")
    public void collectRandomFlashcardPackInfo() {
        FlashcardPacksPage flashcardPacksPage =
                new HomePage(getPage()).init()
                        .clickFlashcardsMenu();

        this.flashcardsPackRandomIndex = flashcardPacksPage.getRandomPackIndex();
        this.flashcardsPackCardsAmount = flashcardPacksPage.getAmountOfCardsInPack();
        this.flashcardsPackName = flashcardPacksPage.getFlashcardsPackName();

        flashcardPacksPage
                .clickHomeMenu();
    }

    @Step("Precondition: Collect all checkboxes within current plan.")
    public List<Locator> getAllCheckboxesWithinCurrentPlan() {

        return new HomePage(getPage()).init()
                .getAllCheckboxesInA2WeeksPlan();
    }

    @Step("Precondition: Ensure all checkboxes should be unchecked.")
    public boolean areAllCheckboxesUnchecked() {

        return new HomePage(getPage()).init()
                .areAllCheckboxesUnchecked();
    }

    @Step("Precondition: One checkbox should be checked and all others unchecked.")
    public boolean oneCheckboxShouldBeCheckedAndAllOthersUnchecked() {
        HomePage homePage =
                new HomePage(getPage()).init()
                        .click2WeeksButton();

        randomIndex = homePage.getSingleCheckedCheckboxIndex();

        if (homePage.isListCheckboxesNotEmpty()) {
            if (homePage.areAllCheckboxesUnchecked()) {
                homePage
                        .clickNthCheckbox(randomIndex)
                        .waitForPointsAnimationToStop();

                return homePage.getAllCheckedCheckboxes().size() == 1;
            }
        }

        return false;
    }

    @Step("Precondition: Ensure all checkboxes should be checked within {planName}.")
    public boolean areAllCheckboxesChecked(String planName) {

        APIUtils.markTasks(planName);
        getPage().reload();

        return new HomePage(getPage()).init()
                .areAllCheckboxesChecked();
    }

    @Step("Precondition: Get single checked checkbox index.")
    public int getSingleCheckedCheckboxIndex() {

        return randomIndex;
    }

    @Step("Precondition: Start test for the stats.")
    public void startTestDomainForStats(String nameTest, String numberOfQuestions) {
        TestListPage testListPage =
                new HomePage(getPage()).init()
                        .clickTestsMenu()
                        .cancelDialogIfVisible()
                        .clickDomainsButtonIfNotActive();
        if (nameTest.equals("Automation testing for stats")) {
            testListPage
                    .clickAutomationTestingForStatsCheckBox()
                    .inputNumberOfQuestions(numberOfQuestions)
                    .clickGenerateAndStartTutorTestButton();
        } else if (nameTest.equals("History and Civilization for Stats")) {
            testListPage
                    .clickHistoryAndCivilizationForStatsCheckBox()
                    .inputNumberOfQuestions(numberOfQuestions)
                    .clickGenerateAndStartTutorTestButton();
        }
    }

    @Step("Precondition: Pass the test with the correct answers of {questionsAmount} questions.")
    public void passTestAllAnswersCorrect(String questionsAmount) {
        int numberOfQuestions = Integer.parseInt(questionsAmount);

        TestTutorPage testTutorPage = new TestTutorPage(getPage()).init();

        for (int numOfQuestion = 1; numOfQuestion < numberOfQuestions; numOfQuestion++) {
            testTutorPage
                    .clickCorrectAnswer()
                    .clickConfirmButton()
                    .clickNextQuestionButton();
        }

        testTutorPage
                .clickCorrectAnswer()
                .clickConfirmButton()
                .clickFinishTestButton()
                .clickSkipButton()
                .clickCloseTheTestButton()
                .clickHomeMenu();
    }

    @Step("Precondition: Pass the test with one wrong answer of {questionsAmount} questions.")
    public void passTestOneAnswersIncorrect(String questionsAmount) {
        int numberOfQuestions = Integer.parseInt(questionsAmount);

        TestTutorPage testTutorPage = new TestTutorPage(getPage()).init();

        for (int numOfQuestion = 1; numOfQuestion < numberOfQuestions; numOfQuestion++) {
            testTutorPage
                    .clickCorrectAnswer()
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

    @Step("Precondition: Get the number of questions on PerformancePage.")
    public int getNumberOfQuestions() {

        PerformancePage performancePage =
                new HomePage(getPage()).init()
                        .clickPerformanceMenu();

        final int numberOfQuestions = performancePage.getNumberOfQuestions();

        performancePage
                .clickHomeMenu();

        return numberOfQuestions;
    }

    @Step("Precondition: Get number questions marked before test.")
    public int getNumberQuestionsMarkedBeforeTest() {
        return new HomePage(getPage()).init()
                .clickTestsMenu()
                .getMarkedNumber();
    }

    @Step("Precondition: Set answer options for 9 cards as: 3 cards - Yes, 3 cards - Kinda, 3 cards - No.")
    public void setOptionsYes3No3Kinda3(String[] stackNames) {
        APIUtils.setMarkOptionsForFlashcardPacks(stackNames, 20);
    }
}

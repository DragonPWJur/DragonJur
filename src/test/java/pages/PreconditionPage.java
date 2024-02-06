package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import io.qameta.allure.Step;
import utils.api.APIUtils;

import java.util.List;

public final class PreconditionPage extends BasePage<PreconditionPage> {
    private int flashcardsPackRandomIndex;
    private String flashcardsPackName;
    private String flashcardsPackCardsAmount;

    public PreconditionPage(Page page) {
        super(page);
    }

    @Override
    public PreconditionPage init() {

        return new PreconditionPage(getPage());
    }

    public int getFlashcardsPackRandomIndex() {

        return flashcardsPackRandomIndex;
    }

    public String getFlashcardsPackName() {

        return flashcardsPackName;
    }

    public String getFlashcardsPackCardsAmount() {

        return flashcardsPackCardsAmount;
    }

    @Step("Precondition: Save the initial amount of flashcards, marked for rechecking.")
    public String getInitialAmountOfCardsMarkedForRechecking() {
        final String amountMarkedForRechecking =
                new HomePage(getPage()).init()
                        .clickFlashcardsMenu()
                        .getAmountOfCardsMarkedForRechecking();

        new FlashcardPacksPage(getPage())
                .clickHomeMenu();

        return amountMarkedForRechecking;
    }

    @Step("Precondition: Start random domain test with {number} question(s).")
    public TestTutorPage startRandomDomainTest(String number) {
        new HomePage(getPage()).init()
                .clickTestsMenu()
                .cancelDialogIfVisible()
                .clickDomainsButton()
                .clickRandomCheckboxDomain()
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

    public List<Locator> getAllCheckboxes() {

        return new HomePage(getPage()).init()
                .getAllCheckboxes();
    }

    public boolean allCheckboxesShouldNotBeActive() {

        return new HomePage(getPage()).init()
                .areAllCheckboxesNotActive();
    }

    public int getRandomIndex() {

        return new HomePage(getPage()).getRandomIndex();
    }

    @Step("Precondition: A single checkbox should be active.")
    public void checkRandomCheckbox(int randomIndex) {
        HomePage homePage = new HomePage(getPage()).init();

        homePage
                .clickNthCheckbox(randomIndex)
                .waitForPointsAnimationToStop();
    }

    @Step("Precondition: Set All Checkboxes to Active state under the {planName} plan.")
    public void setAllCheckboxesToBeChecked(String planName) {
        APIUtils.markTasks(planName);
        getPage().reload();
    }

    public boolean allCheckboxesShouldBeChecked() {
        return new HomePage(getPage()).init()
                .areAllCheckboxesActive();
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

    @Step("Precondition: Pass the wrong answer to {questionsAmount} questions.")
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

    @Step("Precondition: Set answer options for 9 cards in each pack {packNames} as: " +
            "3 cards - 'Yes', 3 cards - 'Kinda', 3 cards - 'No', 6 cards - 'Unused'.")
    public void setOptionsYes3No3Kinda3(String[] packNames) {
        APIUtils.setMarkOptionsForFlashcardPacks(packNames, 20);
    }

    @Step("Precondition: Get random checkbox image.")
    public Locator getRandomImage(int index) {

        return new HomePage(getPage()).init().getNthCheckboxImage(index);
    }

    @Step("Precondition: Choose domains with at least {questionsPerDomain} questions,  " +
            "start tutor test with {numberOfQuestions} and answer incorrect.")
    public void startDomainsTestAndAnswerIncorrect(int questionsPerDomain, int numberOfQuestions) {
        APIUtils.answerIncorrectAndFinish(questionsPerDomain, numberOfQuestions);
    }
}

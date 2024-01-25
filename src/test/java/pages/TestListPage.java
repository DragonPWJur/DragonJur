package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import pages.constants.Constants;

import java.util.List;
import java.util.regex.Pattern;

public final class TestListPage extends BaseSideMenu<TestListPage> implements IRandom {
    private final Locator domainsButton = text("Domains");
    private final Locator tutorButton = button("Tutor");
    private final Locator numberOfQuestionsInputField = locator("input[name = 'numberOfQuestions']");
    private final Locator chaptersButton = text("Chapters");
    private final Locator timedButton = exactButton("Timed");
    private final Locator startTestButton = exactButton("Start test");
    private final Locator startButton = exactButton("Start");
    private final Locator automationTestingForStatsText = text("Automation testing for stats");
    private final Locator historyAndCivilizationForStatsText = text("History and Civilization for Stats");
    private final List<Locator> allCheckboxes = allCheckboxes("label");
    private final Locator checkboxes = locator("button:has(input[type='checkbox'])>div");
    private Locator activeCheckboxes = checkboxes.filter(new Locator.FilterOptions().setHasNot(locator("[disabled]")));
    private final Locator generateAndStartButton = button("Generate & Start");


    TestListPage(Page page) {
        super(page);
    }

    @Override
    public TestListPage init() {

        return createPage(new TestListPage(getPage()), Constants.TEST_LIST_END_POINT);
    }

    @Step("Click 'Domains' button if not active")
    public TestListPage clickDomainsButtonIfNotActive() {
        if (!domainsButton.isChecked()) {
            domainsButton.click();
        }

        return this;
    }

    @Step("Click random checkbox")
    public TestListPage clickRandomCheckbox() {
        getRandomValue(allCheckboxes).click();

        return this;
    }

    @Step("Set '{number}' as number of questions")
    public TestListPage inputNumberOfQuestions(String number) {
        numberOfQuestionsInputField.fill(number);

        return this;
    }

    @Step("Click 'Tutor' button")
    public TestListPage clickTutorButton() {
        tutorButton.click();

        return this;
    }

    @Step("Select a checkbox randomly and retrieve its name")
    public String clickRandomCheckboxAndReturnItsName() {
        int randomValue = getRandomNumber(allCheckboxes);
        allCheckboxes.get(randomValue).click();

        return allCheckboxes.get(randomValue).textContent();
    }

    public TestListPage cancelDialogIfVisible() {
        cancelDialog();

        return this;
    }

    public TestListPage clickChaptersButton() {
        // while block was added due to a bug in the application (Generate And Start button inactive)
        if (!chaptersButton.isChecked()) {
            chaptersButton.click();

            int attempt = 0;
            while (checkboxes.count() <= 24 && attempt < 3) {
                getPage().reload();
                waitWithTimeout(3000);
                attempt++;
            }
        }

        return this;
    }

    public TestListPage clickTimedButton() {
        timedButton.click();

        return this;
    }

    public TestListPage clickStartTestButton() {
        startTestButton.click();

        return this;
    }

    public TestTimedPage clickStartButton() {
        startButton.click();

        return new TestTimedPage(getPage()).init();
    }

//    public Locator getNumberMarked() {
//
//        return numberMarked;
//    }

//    public Locator checkIcon(String text) {
//
//        return allCheckboxes.getByText(text).locator("svg");
//    }

    public TestListPage clickAutomationTestingForStatsCheckBox() {
        automationTestingForStatsText.click();

        return this;
    }

    public TestListPage clickHistoryAndCivilizationForStatsCheckBox() {
        historyAndCivilizationForStatsText.click();

        return this;
    }

    public int clickRandomActiveCheckboxAndReturnNumberOfQuestions() {
        activeCheckboxes = activeCheckboxes.filter(new Locator.FilterOptions().setHasText(Pattern.compile("\\d+")));
        activeCheckboxes.last().waitFor();

        int randomValue = getRandomNumber(activeCheckboxes);
        activeCheckboxes.nth(randomValue).click();
        getPage().waitForTimeout(1000);

        return Integer.parseInt(activeCheckboxes.nth(randomValue).textContent().replaceAll("[^\\d/]+", "").split("/")[0]);
    }

    @Step("Set random number of questions")
    public TestListPage inputRandomNumberOfQuestions(int maxNumberOfQuestions) {
        String number = String.valueOf(getRandomInt(maxNumberOfQuestions));
        numberOfQuestionsInputField.fill(number);
        return this;
    }

    @Step("Click 'Generate and Start' button")
    public TestTutorPage clickGenerateAndStartButtonTutor() {
        generateAndStartButton.click();

        return new TestTutorPage(getPage()).init();
    }

    @Step("Click 'Generate and Start' button")
    public TestTimedPage clickGenerateAndStartButtonTimed() {
        generateAndStartButton.click();

        return new TestTimedPage(getPage()).init();
    }
}

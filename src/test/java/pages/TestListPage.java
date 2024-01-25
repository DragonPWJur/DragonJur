package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import pages.constants.Constants;

import java.util.List;

public final class TestListPage extends BaseTestsListPage<TestListPage> implements IRandom {
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
        if (!chaptersButton.isChecked()) {
            chaptersButton.click();
            waitWithTimeout(2000);
            getPage().reload();
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
}

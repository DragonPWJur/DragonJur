package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import pages.constants.Constants;

import java.util.List;
import java.util.regex.Pattern;

public final class TestListPage extends BaseTestsListPage<TestListPage> implements IRandom {
    private final Locator domainsButton = text("Domains");
    private final Locator domainsCheckbox = locator("label.sc-gHLcSH.colYoX");
    private final Locator tutorButton = button("Tutor");
    private final Locator numberOfQuestionsInputField = locator("input[name = 'numberOfQuestions']");
    private final Locator chaptersButton = text("Chapters");
    private final Locator timedButton = exactButton("Timed");
    private final Locator startTestButton = exactButton("Start test");
    private final Locator startButton = exactButton("Start");
    private final Locator automationTestingForStatsText = text("Automation testing for stats");
    private final Locator checkbox = locator("button:has(input[type='checkbox'])>div");
    private Locator activeCheckbox = checkbox.filter(new Locator.FilterOptions().setHasNot(locator("[disabled]")));
    private final Locator statsTests = exactText("Stats");
    private final List<Locator> allCheckboxes = allCheckboxes("div:has(button) label > span");
    private final Locator markedNumber = locator("label:has(input[value=\"MARKED\"])>span");

    TestListPage(Page page) {
        super(page);
    }

    @Override
    public TestListPage init() {

        return createPage(new TestListPage(getPage()), Constants.TEST_LIST_END_POINT);
    }

    @Step("Click 'Domains' button if not active")
    public TestListPage clickDomainsButtonIfNotActive() {
        waitWithTimeout(1000);
        if (text("Please select subject").isVisible()) {
            getPage().reload();
            waitWithTimeout(2000);

            domainsButton.click();
        }
        if (!domainsButton.isChecked()) {
            domainsButton.click();
            waitWithTimeout(2000);

            getPage().reload();
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
            while (checkbox.count() <= 24 && attempt < 3) {
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

    public int getMarkedNumber() {
        waitWithTimeout(2000);
        System.out.println(markedNumber.innerText());

        return Integer.parseInt(markedNumber.innerText());
    }

//    public Locator checkIcon(String text) {
//
//        return allCheckboxes.getByText(text).locator("svg");
//    }

    public TestListPage clickAutomationTestingForStatsCheckBox() {
        automationTestingForStatsText.click();

        return this;
    }

    public TestListPage clickHistoryAndCivilizationForStatsCheckBox() {
        statsTests.click();

        return this;
    }

    @Step("Set random number of questions")
    public TestListPage inputRandomNumberOfQuestions(int maxNumberOfQuestions) {
        String number = String.valueOf(getRandomInt(maxNumberOfQuestions));
        numberOfQuestionsInputField.fill(number);
        return this;
    }

    @Step("Click random checkbox and return related number of questions (for Bronze subscription)")
    public int getNumberOfQuestionsForRandomCheckbox() {
        activeCheckbox = activeCheckbox.filter(new Locator.FilterOptions().setHasText(Pattern.compile("\\d+")));
        activeCheckbox.last().waitFor();

        int randomValue = getRandomNumber(activeCheckbox);
        activeCheckbox.nth(randomValue).click();
        getPage().waitForTimeout(1000);

        return Integer.parseInt(activeCheckbox.nth(randomValue).textContent().replaceAll("[^\\d/]+", "").split("/")[0]);
    }
}

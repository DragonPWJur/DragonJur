package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import pages.constants.Constants;
import utils.reports.LoggerUtils;

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

    private int randomNumber;

    TestListPage(Page page) {
        super(page);
    }

    @Override
    public TestListPage init() {

        return createPage(new TestListPage(getPage()), Constants.TEST_LIST_END_POINT);
    }

    @Step("Click 'Domains' button if not active")
    public TestListPage clickDomainsButtonIfNotActive() {
        domainsButton.click();
        getPage().reload();
        waitWithTimeout(2000);

        if(!domainsButton.isChecked()) {
            getPage().reload();
            waitWithTimeout(2000);

            domainsButton.click();

            //A 'while' block is added to address the bug related to the 'Domains' button
            int count = 3;
            while (!domainsButton.isChecked() && count > 0) {
                getPage().reload();
                waitWithTimeout(1000);
                domainsButton.click();
                waitWithTimeout(2000);
                count--;
                if (count == 0 && !domainsButton.isChecked() || count == 0 && text("Please select subject").isVisible()) {
                    LoggerUtils.logError("ERROR: Domains button is not active.");
                }
            }
        }

        return this;
    }

    @Step("Click random checkbox")
    public TestListPage clickRandomCheckbox() {
        getRandomValue(allCheckboxes).click();

        return this;
    }

    @Step("Input '{number}' as number of questions")
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

    @Step("Click 'Chapters' button if not active")
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

    private void setRandomNumberOutOfAvailableCheckboxes() {
        activeCheckbox = activeCheckbox.filter(new Locator.FilterOptions().setHasText(Pattern.compile("\\d+")));
        activeCheckbox.last().waitFor();

        this.randomNumber = getRandomNumber(activeCheckbox);
    }

    @Step("Click random available checkbox")
    public TestListPage clickRandomAvailableCheckbox() {
        setRandomNumberOutOfAvailableCheckboxes();
        activeCheckbox.nth(randomNumber).click();
        waitWithTimeout(1000);

        return this;
    }

    private int getNumberOfAvailableQuestions() {

        return Integer.parseInt(activeCheckbox.nth(randomNumber)
                .textContent()
                .replaceAll("[^\\d/]+", "").split("/")[0]);
    }

    @Step("Input a random number within the range of available questions")
    public TestListPage inputRandomNumberOfQuestions() {
        final int numberOfAvailableQuestions = getNumberOfAvailableQuestions();
        final String numberToInput = String.valueOf(getRandomInt(numberOfAvailableQuestions));

        numberOfQuestionsInputField.fill(numberToInput);

        return this;
    }
}

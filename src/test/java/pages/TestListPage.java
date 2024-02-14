package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import pages.constants.Constants;
import utils.reports.LoggerUtils;

import java.util.List;
import java.util.regex.Pattern;

public final class TestListPage extends BaseSideMenu<TestListPage> implements IRandom {
    private final Locator domainsButton = text("Domains");
    private final Locator domainsCheckbox = locator("input[name='domainsQuestions']");
    private final Locator tutorButton = button("Tutor");
    private final Locator numberOfQuestionsInputField = locator("input[name = 'numberOfQuestions']");
    private final Locator chaptersButton = text("Chapters");
    private final Locator timedButton = exactButton("Timed");
    private final Locator startTestButton = exactButton("Start test");
    private final Locator startButton = exactButton("Start");
    private final Locator automationTestingForStatsText = text("Automation testing");
    private final Locator checkbox = locator("button:has(input[type='checkbox'])>div");
    private final Locator statsTests = exactText("Stats");
    private final List<Locator> allCheckboxes = allCheckboxes("label:has(span>input[type='checkbox'])");
    private final Locator markedNumber = locator("label:has(input[value='MARKED'])>span");
    private final Locator tostifyAlert = getPage().getByRole(AriaRole.ALERT).locator("div~div");
    private final Locator generateAndStartButton = button("Generate & Start");
    private Locator activeCheckbox = checkbox.filter(new Locator.FilterOptions().setHasNot(locator("[disabled]")));

    private int randomNumber;
    private Locator randomCheckbox;

    TestListPage(Page page) {
        super(page);
    }

    @Override
    public TestListPage init() {

        return createPage(new TestListPage(getPage()), Constants.TEST_LIST_END_POINT);
    }

    @Step("Click 'Domains' button if not active.")
    public TestListPage clickDomainsButtonIfNotActive() {
        waitForPageLoad();
        domainsButton.click();
        if (!domainsButton.isChecked()) {
            domainsButton.click();
            waitWithTimeout(3000);
        }

        return this;
    }

    @Step("Click 'Domains' button.")
    public TestListPage clickDomainsButton() {
        waitForPageLoad();
        while (!domainsButton.isChecked()) {
            domainsButton.click();
            getPage().reload();
            waitForPageLoad();
        }

        LoggerUtils.logInfo("domainsButton is checked");

        return this;
    }

    @Step("Click random available checkbox.")
    public TestListPage clickRandomCheckboxDomain() {
        randomNumber = getRandomNumber(allCheckboxes);
        randomCheckbox = allCheckboxes.get(randomNumber);
        if (domainsButton.isChecked()) {
            String checkboxText = randomCheckbox.innerText();
            exactText(checkboxText).click();
            waitWithTimeout(1000);
        }

        return this;
    }

    @Step("Click random available checkbox.")
    public TestListPage clickRandomCheckboxChapter() {
        randomCheckbox = getRandomValue(allCheckboxes);
        if (chaptersButton.isChecked()) {
            numberOfQuestionsInputField.clear();
            randomCheckbox.click();
            waitWithTimeout(1000);
        }

        return this;
    }

    @Step("Input '{number}' as number of questions.")
    public TestListPage inputNumberOfQuestions(String number) {
        numberOfQuestionsInputField.clear();
        numberOfQuestionsInputField.fill(number);

        return this;
    }

    @Step("Click 'Tutor' button")
    public TestListPage clickTutorButton() {
        tutorButton.click();

        return this;
    }

    @Step("Click random checkbox.")
    public TestListPage clickCheckboxRandom() {
        randomNumber = getRandomNumber(allCheckboxes);
        allCheckboxes.get(randomNumber).click();

        return this;
    }

    @Step("Get random checkbox.")
    public Locator getRandomCheckbox() {

        return domainsCheckbox.nth(randomNumber);
    }

    @Step("Retrieve random checkbox name.")
    public String getRandomCheckboxName() {

        return allCheckboxes.get(randomNumber).textContent();
    }

    @Step("Cancel 'Unfinished test' dialog if visible.")
    public TestListPage cancelDialogIfVisible() {
        cancelDialog();

        return this;
    }

    @Step("Click 'Chapters' button if not active.")
    public TestListPage clickChaptersButton() {
        // while block was added due to a bug in the application (Generate And Start button inactive)
        if (!chaptersButton.isChecked()) {
            chaptersButton.click();
            waitWithTimeout(1000);
            if (!chaptersButton.isChecked()) {
                chaptersButton.click();
                waitWithTimeout(1000);
            }

            int attempt = 0;
            while (checkbox.count() <= 24 && attempt < 3) {
                getPage().reload();
                waitWithTimeout(3000);
                attempt++;
            }
        }

        return this;
    }

    @Step("Click 'Timed' button.")
    public TestListPage clickTimedButton() {
        timedButton.click();

        return this;
    }

    @Step("Click 'Start test' button.")
    public TestListPage clickStartTestButton() {
        startTestButton.click();

        return this;
    }

    @Step("Click 'Start' button.")
    public TestTimedPage clickStartButton() {
        startButton.click();

        return new TestTimedPage(getPage()).init();
    }

    @Step("Get number of 'Marked' questions.")
    public int getMarkedNumber() {
        waitWithTimeout(2000);

        return Integer.parseInt(markedNumber.innerText());
    }

    @Step("Get checkbox image for checkbox {text}.")
    public Locator getCheckboxImage(String text) {

        return checkbox.getByText(text).locator("svg");
    }

    @Step("Click 'Automation testing for stats' checkbox.")
    public TestListPage clickAutomationTestingForStatsCheckBox() {
        automationTestingForStatsText.click();

        return this;
    }

    @Step("Click 'Stats' checkbox")
    public TestListPage clickHistoryAndCivilizationForStatsCheckBox() {
        statsTests.click();

        return this;
    }

    private void setRandomNumberOutOfAvailableCheckboxes() {
        activeCheckbox = activeCheckbox.filter(new Locator.FilterOptions().setHasText(Pattern.compile("\\d+")));
        activeCheckbox.last().waitFor();

        this.randomNumber = getRandomNumber(activeCheckbox);
    }

    @Step("Click random available checkbox.")
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

    @Step("Input a random number within the range of available questions.")
    public TestListPage inputRandomNumberOfQuestions() {
        final int numberOfAvailableQuestions = getNumberOfAvailableQuestions();
        final String numberToInput = String.valueOf(getRandomInt(numberOfAvailableQuestions));

        numberOfQuestionsInputField.fill(numberToInput);

        return this;
    }

    @Step("Input a random number Greater than the number of available questions.")
    public TestListPage inputGreaterBy1NumberOfQuestions() {
        final int numberOfAvailableQuestions = getNumberOfAvailableQuestions() + 1;
        final String numberToInput = String.valueOf(numberOfAvailableQuestions);

        numberOfQuestionsInputField.fill(numberToInput);

        return this;
    }

    public Locator getAlert() {

        return tostifyAlert;
    }

    public String getAlertMessage() {

        return tostifyAlert.textContent();
    }

    @Step("Click 'Generate and Start' button to start the Tutor test.")
    public TestTutorPage clickGenerateAndStartTutorTestButton() {
        generateAndStartButton.click();

        return new TestTutorPage(getPage()).init();
    }

    @Step("Click 'Generate and Start' button to start the Timed test.")
    public TestListPage clickGenerateAndStartTimedTestButton() {
        generateAndStartButton.click();

        return new TestListPage(getPage()).init();
    }

    @Step("Click 'Generate and Start' button to start the Tutor test.")
    public TestListPage clickGenerateAndStartTestButton() {
        generateAndStartButton.click();

        return new TestListPage(getPage()).init();
    }
}

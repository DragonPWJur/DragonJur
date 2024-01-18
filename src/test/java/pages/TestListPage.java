package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.qameta.allure.Step;
import utils.TestUtils;

public class TestListPage extends SideMenuPage {

    private final Locator domainsButton = text("Domains");
    private final Locator tutorButton = button("Tutor");
    private final Locator numberOfQuestionsInputField = locator("input[name = 'numberOfQuestions']");
    private final Locator generateAndStartButton = button("Generate & Start");
    private final Locator listCheckboxes = locator("button:has(input[type='checkbox'])");
    private final Locator numberMarked = numberMarked();
    private final Locator testDomain2Text = text("Test domain 2");
    private final Locator chaptersButton = text("Chapters");
    private final Locator timedButton = exactButton("Timed");
    private final Locator startTestButton = exactButton("Start test");
    private final Locator startButton = exactButton("Start");

    public TestListPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public Locator getListCheckboxes() {
        return waitForListOfElementsLoaded(listCheckboxes);
    }

    @Step("Click 'Domains' button")
    public TestListPage clickDomainsButton() {
        if (!domainsButton.isChecked()) {
            domainsButton.click();
        }
        return this;
    }

    @Step("Click 'Tutor' button")
    public TestListPage clickTutorButton() {
        tutorButton.click();
        return this;
    }

    @Step("Set '{number}' as number of questions")
    public TestListPage inputNumberOfQuestions(String number) {
        numberOfQuestionsInputField.fill(number);
        return this;
    }

    @Step("Click 'Generate and Start' button")
    public TestsPage clickGenerateAndStartButton() {
        generateAndStartButton.click();
        return new TestsPage(getPage(), getPlaywright());
    }

    @Step("Click random checkbox")
    public TestListPage clickRandomCheckbox() {
        TestUtils.clickRandomElement(getListCheckboxes());
        return this;
    }

    @Step("Cancel the dialog")
    public TestListPage cancelDialogIfVisible() {
        cancelDialog();
        return this;
    }

    public TestListPage clickTestDomain2CheckBox() {
        testDomain2Text.click();
        return this;
    }

    public TestListPage clickChaptersButton() {
        if (!chaptersButton.isChecked()) {
            chaptersButton.click();
            getPage().waitForTimeout(2000);
            getPage().reload();
        }
        return this;
    }

    public TestTutorPage clickGenerateAndStartButton2() {
        generateAndStartButton.click();
        return new TestTutorPage(getPage(), getPlaywright());
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

        return new TestTimedPage(getPage(), getPlaywright());
    }

    public TestListPage clickGenerateAndStartButton1() {
        generateAndStartButton.click();
        return this;
    }

    public Locator getNumberMarked() {
        return numberMarked;
    }
}


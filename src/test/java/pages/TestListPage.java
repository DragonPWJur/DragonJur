package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import utils.TestUtils;

public class TestListPage extends SideMenuPage {

    private final Locator domainsButton = text("Domains");
    private final Locator tutorButton = button("Tutor");
    private final Locator numberOfQuestionsInputField = getPage().locator("input[name = 'numberOfQuestions']");
    private final Locator generateAndStartButton = button("Generate & Start");
    private final Locator listCheckboxes = waitForListOfElementsLoaded("button:has(input[type='checkbox'])");
    private final Locator numberMarked = text("Marked").locator("span");
    private final Locator testDomain2Text = text("Test domain 2");
    private final Locator chaptersButton = text("Chapters");
    private final Locator timedButton = exactButton("Timed");
    private final Locator startTestButton = exactButton("Start test");
    private final Locator startTButtonModalWindowWithQuestions = exactButton("Start");

    public TestListPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public TestListPage clickDomainsButton() {
        if (!domainsButton.isChecked()) {
            domainsButton.click();
        }
        return this;
    }

    public TestListPage clickTutorButton() {
        tutorButton.click();
        return this;
    }

    public TestListPage inputNumberOfQuestions(String number) {
        numberOfQuestionsInputField.fill(number);
        return this;
    }

    public TestsPage clickGenerateAndStartButton() {
        generateAndStartButton.click();
        return new TestsPage(getPage(), getPlaywright());
    }

    public TestListPage clickRandomCheckbox() {
        TestUtils.clickRandomElement(listCheckboxes);
        return this;
    }

    public TestListPage cancelDialogIfVisible() {
        if(dialog().isVisible()) {
            cancelDialog();
        }
        return this;
    }

    public TestListPage clickTestDomain2CheckBox() {
        testDomain2Text.click();
        return this;
    }

    public TestListPage clickChaptersButton() {
        if (!chaptersButton.isChecked()) {
            chaptersButton.click();
        }
        return this;
    }

    public TestListPage clickTimedButton() {
        timedButton.click();

        return this;
    }

    public TestListPage clickStartTestButtonModalTimedTest() {
        startTestButton.click();

        return this;
    }

    public TestTimedPage clickStartOnTheModalWindowWithQuestions() {
        startTButtonModalWindowWithQuestions.click();

        return new TestTimedPage(getPage(), getPlaywright());
    }

    public TestListPage clickGenerateStartButton() {
        generateAndStartButton.click();
        return this;
    }
}

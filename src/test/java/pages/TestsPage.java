package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.util.List;
import static utils.TestUtils.getRandomValue;

public class TestsPage extends BaseLocator {
    public Locator testsMenuButton = button("Tests");
    public Locator numberOfQuestionsInput = textBox();
    public Locator generateStartButton = button("Generate & Start");
    public Locator cancelButtonOnPromptWindow = button("Cancel");
    public Locator promptMessageUnfinishedTest  = text("You have unfinished test. Do you want to continue?");
    public Locator numberMarkedQuestionMode  = text("Marked").locator("span");

    public TestsPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    protected String catchSubject() {
        List<Locator> subjects = getPage().locator("div#root form button label").all();

        return getRandomValue(subjects).textContent();
    }

    public void initiateTest() {
        testsMenuButton.click();
        if (promptMessageUnfinishedTest.isVisible()) {
            cancelButtonOnPromptWindow.click();
        }
        clickSubject(catchSubject());

        numberOfQuestionsInput.fill("1");
        generateStartButton.click();
    }

    public void clickTestsMenuButton() {
        testsMenuButton.click();
    }

    public String getNumberMarked() {
        clickTestsMenuButton();

        return numberMarkedQuestionMode.innerText();
    }
}


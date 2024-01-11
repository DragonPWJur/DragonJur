package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.util.List;
import static utils.TestUtils.getRandomValue;

public class TestsPage extends BaseLocator {
    public Locator testsMenuButton = button("Tests");
    public Locator numberOfQuestions = textBox();
    public Locator generateStartButton = button("Generate & Start");
    public Locator cancelButtonOnPromptWindow = button("Cancel");
    public Locator promptMessageUnfinishedTest  = text("You have unfinished test. Do you want to continue?");

    public TestsPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    protected String catchSubject() {

        List<Locator> subjects = getPage().locator("div.sc-gHnmx.iEQmLh").all();

        String text = getRandomValue(subjects).textContent();

        String substring = text.substring(text.lastIndexOf('('));

        return text.replace(substring, "").trim();

    }

    public void initiateTest() {

        testsMenuButton.click();
        if (promptMessageUnfinishedTest.isVisible()) {
            cancelButtonOnPromptWindow.click();
        }
        clickSubject(catchSubject());

        numberOfQuestions.fill("1");
        generateStartButton.click();
    }
}


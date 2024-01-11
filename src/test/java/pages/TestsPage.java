package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

import java.util.List;
import static utils.TestUtils.getRandomValue;

public class TestsPage extends BaseLocator {
    private final Locator testsMenu = button("Tests");
    private final Locator numberOfQuestionsInput = textBox();
    private final Locator generateAndStartButton = button("Generate & Start");
    private final Locator cancelButton = button("Cancel");
    private final Locator unfinishedTestText  = text("You have unfinished test. Do you want to continue?");
    private final Locator numberMarkedQuestionMode  = text("Marked").locator("span");

    public TestsPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public Locator getNumberMarkedQuestionMode() {
        return numberMarkedQuestionMode;
    }

    protected String getSubject() {
        List<Locator> subjects = getPage().locator("div#root form button label").all();

        return getRandomValue(subjects).textContent();
    }

    protected void clickSubject(String subject) {
        getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(subject)).click();
    }

    public void initiateTest() {
        testsMenu.click();
        if (unfinishedTestText.isVisible()) {
            cancelButton.click();
        }
        clickSubject(getSubject());

        numberOfQuestionsInput.fill("1");
        generateAndStartButton.click();
    }

    public void clickTestsMenu() {
        testsMenu.click();
    }

    public String getNumberMarked() {
        clickTestsMenu();

        return numberMarkedQuestionMode.innerText();
    }

}


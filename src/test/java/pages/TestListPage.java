package pages;

import com.microsoft.playwright.Dialog;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

import java.util.Random;

public class TestListPage extends BaseLocator {

    public Locator domainsButton = text("Domains");
    public Locator tutorButton = button("Tutor");
    public Locator numberOfQuestionsInputField = getPage().locator("input[name = 'numberOfQuestions']");
    public Locator generateAndStartButton = button("Generate & Start");
    public Locator listCheckboxes = waitForListOfElementsLoaded("//div//form/div/div[3]//button");

    protected TestListPage(Page page, Playwright playwright) {
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

    public TestListPage fillNumberOfQuestionsInputField(String number) {
        numberOfQuestionsInputField.fill(number);
        return this;
    }

    public TestsPage clickGenerateStartButton() {
        generateAndStartButton.click();
        return new TestsPage(getPage(), getPlaywright());
    }

    public TestListPage handleDialogAndCancel() {
        if (getPage().getByRole(AriaRole.DIALOG).isVisible()) {
            getPage().onDialog(Dialog::dismiss);
            getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Cancel")).click();
        }
        return this;
    }

    public static int getRandomNumber(Locator list) {
        if (list.count() == 0) {
            return 0;
        }
        return new Random().nextInt(1, list.count());
    }

    public static void clickRandomElement(Locator list) {
        int randomValue = getRandomNumber(list);
        list.nth(randomValue).click();
    }

    public TestListPage clickRandomCheckbox() {
        clickRandomElement(listCheckboxes);
        return this;
    }
}

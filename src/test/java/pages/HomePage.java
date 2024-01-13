package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomePage extends BaseLocator {
    private final Locator studyThisButton = button("Study This");
    private final Locator testsButton = exactButton("Tests");
    private final  Locator flashcardsButton = button("Flashcards");
    private final Locator listCheckboxes = waitForListOfElementsLoaded("#root label");

    public HomePage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public TestListPage clickTestsMenu() {
        testsButton.click();
        return new TestListPage(getPage(), getPlaywright());
    }

    public FlashcardPacksPage clickFlashcardsMenu() {
        flashcardsButton.click();
        return new FlashcardPacksPage(getPage(), getPlaywright());
    }

    public Locator getStudyThisButton() {
        return studyThisButton;
    }

    public TestTutorPage initiateTest() {

        new HomePage(getPage(), getPlaywright())
                .clickTestsMenu()
                .cancelDialogIfVisible()
                .clickDomainsButton()
                .clickRandomCheckbox()
                .clickTutorButton()
                .inputNumberOfQuestions("1")
                .clickGenerateAndStartButton();

        return new TestTutorPage(getPage(), getPlaywright());
    }

    public Locator getCheckboxUnderLearningSchedulerSection() {
        return listCheckboxes;
    }

    public HomePage clickCheckbox(Locator checkboxes, int number) {
        checkboxes.nth(number).check();
        return this;
    }
}


package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

abstract class BaseSideMenu extends BaseLocator{

    private final Locator homeButton = exactButton("Home");
    private final Locator flashcardsButton = exactButton("Flashcards");
    private final Locator testsButton = exactButton("Tests");
    private final Locator profileButton = exactButton("Profile");
    private final Locator mnemonicCardsButton = exactButton("Mnemonic cards");
    private final Locator studyGuideButton = button("Study guide");
    private final Locator performanceButton = exactButton ("Performance");

    protected BaseSideMenu(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public HomePage clickHomeMenu() {
        homeButton.click();
        return new HomePage(getPage(), getPlaywright());
    }

    public FlashcardPacksPage clickFlashcardsMenu() {
        flashcardsButton.click();
        return new FlashcardPacksPage(getPage(), getPlaywright());
    }

    public TestListPage clickTestsMenu() {
        testsButton.click();
        return new TestListPage(getPage(), getPlaywright());
    }

    public ProfilePage clickProfileMenu() {
        profileButton.click();

        return new ProfilePage(getPage(), getPlaywright());
    }

    public MnemonicCardListPage clickMnemonicCardsMenu() {
        mnemonicCardsButton.click();

        return new MnemonicCardListPage(getPage(), getPlaywright());
    }

    public StudyGuidePage clickStudyGuide() {
        studyGuideButton.click();

        return new StudyGuidePage(getPage(), getPlaywright());
    }

    public PerformancePage clickPerformanceMenu() {

        performanceButton.click();
        return new PerformancePage(getPage(), getPlaywright());
    }
}

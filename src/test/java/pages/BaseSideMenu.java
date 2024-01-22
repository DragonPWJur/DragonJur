package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

abstract class BaseSideMenu extends BaseModal {
    private final Locator homeButton = exactButton("Home");
    private final Locator studyGuideButton = button("Study guide");
    private final Locator testsButton = exactButton("Tests");
    private final Locator flashcardsButton = exactButton("Flashcards");
    private final Locator mnemonicCardsButton = exactButton("Mnemonic cards");
    private final Locator performanceButton = exactButton("Performance");
    private final Locator profileButton = exactButton("Profile");

    protected BaseSideMenu(Page page) {
        super(page);
    }

    @Step("Click side menu 'Home'.")
    public void clickHomeMenu() {
        homeButton.click();

        isOnHomePage();
    }

    @Step("Click side menu 'Study Guide'.")
    public StudyGuidePage clickStudyGuide() {
        studyGuideButton.click();

        return isOnStudyGuidePage();
    }

    @Step("Click side menu 'Tests'.")
    public TestListPage clickTestsMenu() {
        testsButton.click();

        return isOnTestListPage();
    }

    @Step("Click side menu 'Flashcards'.")
    public FlashcardPacksPage clickFlashcardsMenu() {
        flashcardsButton.click();

        return isOnFlashcardsPackPage();
    }

    @Step("Click side menu 'Mnemonic Cards'.")
    public MnemonicCardListPage clickMnemonicCardsMenu() {
        mnemonicCardsButton.click();

        return isOnMnemonicCardListPage();
    }

    @Step("Click side menu 'Performance'.")
    public PerformancePage clickPerformanceMenu() {
        performanceButton.click();

        return isOnPerformancePage();
    }

    @Step("Click side menu 'Profile'.")
    public ProfilePage clickProfileMenu() {
        profileButton.click();

        return isOnProfilePage();
    }
}

package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

abstract class SideMenuPage extends BaseLocator{

    private final Locator homeButton = exactButton("Home");
    private final Locator flashcardsButton = exactButton("Flashcards");
    private final Locator testsButton = exactButton("Tests");

    protected SideMenuPage(Page page, Playwright playwright) {
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
}

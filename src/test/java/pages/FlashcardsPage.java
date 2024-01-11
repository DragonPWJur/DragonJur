package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

public class FlashcardsPage extends BaseLocator{

    private final Locator numberMarkedForRechecking = button("Marked for re-checking").locator("div:nth-of-type(2)");
    private final  Locator flashcardsMenu = button("Flashcards");

    public FlashcardsPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public FlashcardsPage clickFlashcardsMenu() {
        flashcardsMenu.click();

        return this;
    }

    public String getNumberOfCardsForReChecking() {
        clickFlashcardsMenu();

        return numberMarkedForRechecking.innerText();
    }

    public Locator getNumberMarkedForRechecking() {
        return numberMarkedForRechecking;
    }


}

package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class FlashcardPacksPage extends BaseLocator {

    private final Locator numberMarkedForRechecking = button("Marked for re-checking").locator("div:nth-of-type(2)");

    protected FlashcardPacksPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public Locator getNumberMarkedForRechecking() {
        return numberMarkedForRechecking;
    }
}

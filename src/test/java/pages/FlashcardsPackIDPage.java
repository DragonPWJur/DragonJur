package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class FlashcardsPackIDPage extends SideMenuPage {
    private final Locator questionHeading = getPage().locator( "span.sc-iBkjds.gpMBxJ.sc-dGBNLl.igYIXR");
    private final Locator gotButton = button("Got it");
    private final Locator flashcardsBackButton = button( "Flashcards /");
    private final Locator yesButton = button("Yes");

    public FlashcardsPackIDPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public Locator getQuestionHeading() {

        return questionHeading;
    }

    public FlashcardsPackIDPage clickGotButtonIfVisible() {
        gotButton.click();
        return this;
    }

    public FlashcardsPackIDPage clickFlashcardsBackButton() {
        flashcardsBackButton.click();

        return this;
    }

    public FlashcardPacksPage clickYesButton() {
        yesButton.click();

        return new FlashcardPacksPage(getPage(), getPlaywright());
    }
}

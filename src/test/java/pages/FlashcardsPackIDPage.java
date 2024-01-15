package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import utils.TestData;

public class FlashcardsPackIDPage extends SideMenuPage {
    private final Locator questionHeading = getPage().locator( "span.sc-iBkjds.gpMBxJ.sc-dGBNLl.igYIXR");
    private final Locator gotButton = button(TestData.GOT_IT);
    private final Locator flashcardsBackButton = button(TestData.FLASHCARDS + " /");
    private final Locator yesButton = button(TestData.YES);

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

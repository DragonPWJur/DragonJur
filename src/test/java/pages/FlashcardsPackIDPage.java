package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import utils.TestData;


public class FlashcardsPackIDPage extends BaseLocator {
    private final Locator questionHeading = getPage().locator( "span.sc-iBkjds.gpMBxJ.sc-dGBNLl.igYIXR");
    private final Locator gotButton = button(TestData.GOT_IT);
    private final Locator flashcardsBackButton = button(TestData.FLASHCARDS + " /");
    public final Locator yesButton = button(TestData.YES);

     public FlashcardsPackIDPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public Locator  getGotButton() {
        return gotButton;
    }

    public FlashcardsPackIDPage clickGotButton() {
        if (gotButton.isVisible()) {
            gotButton.click();
        }
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

    public FlashcardPacksPage startFlashcardsPackForTheFirstTimeAndGoBack() {
        if (gotButton.isVisible()) {
            gotButton.click();
            flashcardsBackButton.click();
            yesButton.click();
        }
        return new FlashcardPacksPage(getPage(), getPlaywright());
    }

    public String questionHeadingText() {
        return questionHeading.innerText();
    }
}

package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.util.List;

public class FlashcardPacksPage extends SideMenuPage {

    private final Locator markedForRecheckingButton = button("Marked for re-checking").locator("div:nth-of-type(2)");
    protected final Locator flashcardsPacksToLearn = waitForListOfElementsLoaded("//div[contains(text(),'Learned')]");

    public FlashcardPacksPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public String getNumberMarkedForRechecking() {
        return markedForRecheckingButton.innerText();
    }

    public Locator getFlashcardsPacksToLearn() {

        return flashcardsPacksToLearn;
    }

    public List<Locator> allFlashcardsPacksToLearn() {

        return flashcardsPacksToLearn.all();
    }

    public FlashcardsPackIDPage clickRandomFlashcardPack(int randomIndex) {
        allFlashcardsPacksToLearn().get(randomIndex).click();

        return new FlashcardsPackIDPage(getPage(), getPlaywright());
    }
}

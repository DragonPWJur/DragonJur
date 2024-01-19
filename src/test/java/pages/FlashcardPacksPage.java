package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import utils.TestUtils;

public class FlashcardPacksPage extends SideMenuPage {

    private final Locator markedForRecheckingButton = button("Marked for re-checking").locator("div:nth-of-type(2)");
    private final Locator listOfFlashcardsPacksToLearn = locator("//div[contains(text(),'Learned')]");

    private final int flashcardsPackNumber = getRandomFlashcardsPacksToLearnNumber();

    public FlashcardPacksPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public String getNumberMarkedForRechecking() {
        return markedForRecheckingButton.innerText();
    }

    public Locator getFlashcardsPacksToLearn() {
        return waitForListOfElementsLoaded(listOfFlashcardsPacksToLearn);
    }

    public FlashcardsPackIDPage clickNthFlashcardPack(int randomIndex) {
        getFlashcardsPacksToLearn().nth(randomIndex).click();

        return new FlashcardsPackIDPage(getPage(), getPlaywright());
    }

    public int getRandomFlashcardsPacksToLearnNumber() {
        return TestUtils.getRandomNumber(getFlashcardsPacksToLearn());
    }

    public int getNumberOfFlashcardsPack() {
        return flashcardsPackNumber;
    }
}

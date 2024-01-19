package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.TestUtils;

import java.util.List;

public final class FlashcardPacksPage extends BaseSideMenu {

    private final Locator markedForRecheckingButton = button("Marked for re-checking");
    private final Locator packToLearnButton = button("Learned");
    private final List<Locator> allPacksToLearnButtons = allButtons(packToLearnButton);

    private final int randomPackIndex = TestUtils.getRandomNumber(allPacksToLearnButtons);

    public FlashcardPacksPage(Page page) {
        super(page);
    }

    int getRandomPackIndex() {
        return randomPackIndex;
    }

    private String[] getFlashcardsPackSplitText() {
        return allPacksToLearnButtons.get(randomPackIndex).innerText().trim().split("\n");
    }

    public String getAmountOfCardsMarkedForRechecking() {
        String[] textToArray = markedForRecheckingButton.innerText().trim().split("\n");

        return textToArray[textToArray.length - 1];
    }

    public String getFlashcardsPackName() {
        return getFlashcardsPackSplitText()[0];
    }

    public String getAmountOfCardsInPack() {
        return getFlashcardsPackSplitText()[1]
                .trim()
                .split(" ")[0]
                .split("/")[1];
    }

    public FlashcardsPackIDPage clickNthFlashcardPack(int randomIndex) {
        allPacksToLearnButtons.get(randomIndex).click();

        return new FlashcardsPackIDPage(getPage());
    }
}

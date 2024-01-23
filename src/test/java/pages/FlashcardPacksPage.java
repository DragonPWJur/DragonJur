package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.constants.Constants;

import java.util.List;

public final class FlashcardPacksPage extends BaseSideMenu<FlashcardPacksPage> implements IRandom {
    private final Locator markedForRecheckingButton = button("Marked for re-checking");
    private final Locator learnedButton = button("Learned");
    private final List<Locator> allLearnedButtons = allButtons(learnedButton);

    private final int randomPackIndex = getRandomNumber(allLearnedButtons);

    FlashcardPacksPage(Page page) {
        super(page);
    }

    @Override
    public FlashcardPacksPage init() {

        return createPage(new FlashcardPacksPage(getPage()), Constants.FLASHCARD_PACKS_END_POINT);
    }

    int getRandomPackIndex() {

        return randomPackIndex;
    }

    private String[] getPackSplitText() {

        return allLearnedButtons.get(randomPackIndex).innerText().trim().split("\n");
    }

    public String getFlashcardsPackName() {

        return getPackSplitText()[0];
    }

    public String getAmountOfCardsInPack() {

        return getPackSplitText()[1].trim().split(" ")[0].split("/")[1];
    }

    public String getAmountOfCardsMarkedForRechecking() {
        String[] textToArray = markedForRecheckingButton.innerText().trim().split("\n");

        return textToArray[textToArray.length - 1];
    }

    public FlashcardsPackIDPage clickNthFlashcardPack(int randomIndex) {
        allLearnedButtons.get(randomIndex).click();

        return new FlashcardsPackIDPage(getPage()).init();
    }
}

package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.TestUtils;

import java.util.List;

public final class FlashcardPacksPage extends BaseSideMenu {

    private final Locator markedForRecheckingButton = button("Marked for re-checking");
    private final Locator learnButton = button("Learned");
    private final List<Locator> allLearnedButtons = allButtons(learnButton);

    private final int randomPackIndex = TestUtils.getRandomNumber(allLearnedButtons);

    public FlashcardPacksPage(Page page) {
        super(page);
    }

    int getRandomPackIndex() {

        return randomPackIndex;
    }

    private String[] getPackSplitText() {

        return allLearnedButtons.get(randomPackIndex).innerText().trim().split("\n");
    }

    public String getAmountOfCardsMarkedForRechecking() {
        String[] textToArray = markedForRecheckingButton.innerText().trim().split("\n");

        return textToArray[textToArray.length - 1];
    }

    public String getFlashcardsPackName() {

        return getPackSplitText()[0];
    }

    public String getAmountOfCardsInPack() {

        return getPackSplitText()[1].trim().split(" ")[0].split("/")[1];
    }

    public FlashcardsPackIDPage clickNthFlashcardPack(int randomIndex) {
        allLearnedButtons.get(randomIndex).click();

        return new FlashcardsPackIDPage(getPage());
    }
}

package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import pages.constants.Constants;

import java.util.List;

public final class FlashcardPacksPage extends BaseSideMenu<FlashcardPacksPage> implements IRandom {
    private final Locator markedForRecheckingButton = button("Marked for re-checking");
    private final Locator learnedButton = button("Learned");
    private final List<Locator> allLearnedButtons = allButtons(learnedButton);
    private final Locator autotestFlashcards = button("AUTOTEST FLASHCARDS");
    private final Locator learned = locator("//div/span[text() = 'AUTOTEST FLASHCARDS']" +
                    "/parent::div//parent::button/div[contains(text(), 'Learned')]");

    private final int randomPackIndex = getRandomNumber(allLearnedButtons);

    FlashcardPacksPage(Page page) {
        super(page);
    }

    @Override
    public FlashcardPacksPage init() {

        return createPage(new FlashcardPacksPage(getPage()), Constants.FLASHCARD_PACKS_END_POINT);
    }

    @Step("Precondition: Collect random index.")
    int getRandomPackIndex() {

        return randomPackIndex;
    }

    private String[] getPackSplitText() {

        return allLearnedButtons.get(randomPackIndex).innerText().trim().split("\n");
    }

    @Step("Precondition: Collect random Flashcards pack's name.")
    public String getFlashcardsPackName() {

        return getPackSplitText()[0];
    }

    @Step("Precondition: Collect random Flashcards pack cards amount.")
    public String getAmountOfCardsInPack() {

        return getPackSplitText()[1].trim().split(" ")[0].split("/")[1];
    }

    @Step("Save the amount of cards marked for rechecking.")
    public String getAmountOfCardsMarkedForRechecking() {
        String[] textToArray = markedForRecheckingButton.innerText().trim().split("\n");

        return textToArray[textToArray.length - 1];
    }

    @Step("Click random ( {randomIndex}-th ) Flashcards pack.")
    public FlashcardsPackIDPage clickNthFlashcardPack(int randomIndex) {
        allLearnedButtons.get(randomIndex).click();

        return new FlashcardsPackIDPage(getPage()).init();
    }

    @Step("Click 'AUTOTEST FLASHCARDS' pack.")
    public FlashcardsPackIDPage clickAutotestFlashcardsPack() {
        getPage().reload();
        autotestFlashcards.click();

        return new FlashcardsPackIDPage(getPage());
    }

    @Step("Collect Learned Cards Amount")
    public String getLearnedCardsAmount() {
        waitForLocator(learned, 2000);

        return learned.innerText().split("/")[0];
    }
}

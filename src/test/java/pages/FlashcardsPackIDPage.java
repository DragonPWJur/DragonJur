package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.constants.Constants;

public final class FlashcardsPackIDPage extends BaseFooter<FlashcardsPackIDPage> {
    private final Locator questionHeading = exactText("Question");
    private final Locator answerHeading = exactText("Answer");
    private final Locator showAnswerButton = exactButton("Show answer");
    private final Locator yesButton = exactButton("Yes");
    private final Locator noButton = exactButton("No");
    private final Locator kindaButton = exactButton("Kinda");

    FlashcardsPackIDPage(Page page) {
        super(page);
    }

    @Override
    public FlashcardsPackIDPage init() {

        return createPage(new FlashcardsPackIDPage(getPage()), Constants.FLASHCARDS_PACK_ID_END_POINT);
    }

    public Locator getQuestionHeading() {

        return questionHeading;
    }

    public Locator getAnswerHeading() {

        return answerHeading;
    }

    public Locator getShowAnswerButton() {

        return showAnswerButton;
    }

    public Locator getYesButton() {

        return yesButton;
    }

    public Locator getNoButton() {

        return noButton;
    }

    public Locator getKindaButton() {

        return kindaButton;
    }

    public FlashcardsPackIDPage clickShowAnswerButton() {
        showAnswerButton.click();

        return this;
    }

    public void clickYesMarkButton() {
        yesButton.click();
        waitWithTimeout(1500);
    }

}

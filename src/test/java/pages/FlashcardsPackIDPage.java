package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import pages.constants.Constants;

public final class FlashcardsPackIDPage extends BaseFooter<FlashcardsPackIDPage> {
    private final Locator questionHeading = exactText("Question");
    private final Locator answerHeading = exactText("Answer");
    private final Locator showAnswerButton = exactButton("Show answer");
    private final Locator yesButton = exactButton("Yes");
    private final Locator noButton = exactButton("No");
    private final Locator kindaButton = exactButton("Kinda");
    private final Locator answerText = locator("div>div:has(button > img) + span").nth(1);

    FlashcardsPackIDPage(Page page) {
        super(page);
    }

    @Override
    public FlashcardsPackIDPage init() {

        return createPage(new FlashcardsPackIDPage(getPage()), Constants.FLASHCARDS_PACK_ID_END_POINT);
    }

    @Step("Collect 'Question' Heading on the page.")
    public Locator getQuestionHeading() {

        return questionHeading;
    }

    @Step("Collect 'Answer' heading on the page.")
    public Locator getAnswerHeading() {

        return answerHeading;
    }

    public Locator getShowAnswerButton() {

        return showAnswerButton;
    }

    @Step("Collect 'Yes' button on the page.")
    public Locator getYesButton() {

        return yesButton;
    }

    @Step("Collect 'No' button on the page.")
    public Locator getNoButton() {

        return noButton;
    }

    @Step("Collect 'Kinda' button on the page.")
    public Locator getKindaButton() {

        return kindaButton;
    }

    @Step("Click 'Show answer' button.")
    public FlashcardsPackIDPage clickShowAnswerButton() {
        waitWithTimeout(3000);
        showAnswerButton.click();

        return this;
    }

    @Step("Collect answer text.")
    public Locator getAnswerText() {

        return answerText;
    }

    @Step("Click 'Yes' mark button.")
    public FlashcardsPackIDPage clickYesMarkButton() {
        waitForLocator(yesButton, 2000);
        yesButton.click();
        waitForPageLoad();
        waitWithTimeout(6000);

        return this;
    }

    @Step("Click 'Kinda' button.")
    public FlashcardsPackIDPage clickKindaMarkButton() {
        waitForLocator(kindaButton, 2000);
        kindaButton.click();
        waitForPageLoad();
        waitWithTimeout(6000);

        return this;
    }

    @Step("Click 'No' button.")
    public FlashcardsPackIDPage clickNoMarkButton() {
        waitForLocator(noButton, 2000);
        noButton.click();
        waitForPageLoad();
        waitWithTimeout(6000);

        return this;
    }
}

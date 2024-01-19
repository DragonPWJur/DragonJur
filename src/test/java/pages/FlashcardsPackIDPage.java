package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class FlashcardsPackIDPage extends SideMenuPage {
    private final Locator questionHeading = text("Answer");
    private final Locator gotButton = button("Got it");
    private final Locator flashcardsBackButton = button( "Flashcards /");
    private final Locator yesButton = button("Yes");
    private final Locator showAnswerButton = button("Show answer");
//    private final Locator questionHeaderOnFlashcardBackside = text("Question");
    private final Locator answerHeading = text("Answer");
    private final Locator yesMarkButton = button("Yes");
    private final Locator resetResultsButton = button("Reset results");
    private final Locator numberOfYesMarks = text("\nYes");

    public FlashcardsPackIDPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public Locator getQuestionHeading() {

        return questionHeading;
    }

    public Locator getAnswerHeading() {

        return answerHeading;
    }

    public Locator getResetResultsButton() {

        return resetResultsButton;
    }

    public Locator getNumberOfYesMarks() {

        return numberOfYesMarks;
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

    public FlashcardsPackIDPage clickShowAnswerButton() {
        showAnswerButton.click();

        return this;
    }

    public FlashcardsPackIDPage clickYesMarkButton() {
        yesMarkButton.click();

        return this;
    }
}

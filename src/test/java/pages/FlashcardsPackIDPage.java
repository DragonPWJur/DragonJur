package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public final class FlashcardsPackIDPage extends BaseTopMenu {

    private final Locator questionHeading = text("Question");
    private final Locator showAnswerButton = exactButton("Show answer");

    public FlashcardsPackIDPage(Page page) {
        super(page);
    }

    public Locator getQuestionHeading() {

        return questionHeading;
    }

    public Locator getShowAnswerButton() {

        return showAnswerButton;
    }



}

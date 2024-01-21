package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.TestUtils;

import java.util.regex.Pattern;

public class FlashcardsPackIDPage extends BaseTopMenu {
    private final Locator questionHeading = text("Question");
    private final Locator gotItButton = button("Got it");
    private final Locator flashcardsBackButton = button( "Flashcards /");
    private final Locator yesButton = button("Yes");
    private final Locator showAnswerButton = button("Show answer");
    private final Locator answerHeading = text("Answer");
    private final Locator yesMarkButton = button("Yes");
    private final Locator resetResultsButton = button("Reset results");
    private final Locator numberOfYesMarks = locator("div")
        .filter(new Locator.FilterOptions().setHasText(Pattern.compile("\\d+"))).getByText("\nYes").first();


    public FlashcardsPackIDPage(Page page) {
        super(page);
    }

    public Locator getQuestionHeading() {

        return questionHeading;
    }

    public Locator getShowAnswerButton() {

        return showAnswerButton;
    }

    public Locator getAnswerHeading() {

        return answerHeading;
    }

    public Locator getResetResultsButton() {

        return resetResultsButton;
    }

    public Locator getNumberOfYesMarks() {
        getPage().waitForTimeout(2000);

        return numberOfYesMarks;
    }

    public FlashcardsPackIDPage clickGotItButtonIfVisible() {
        gotItButton.click();

        return this;
    }

    public FlashcardsPackIDPage clickShowAnswerButton() {
        showAnswerButton.click();

        return this;
    }

    public FlashcardsPackIDPage clickYesMarkButton() {
        yesMarkButton.click();

        return this;
    }

    public String increaseByOne(String text) {
        String[] numberYes = text.split(" ");

        return TestUtils.add(numberYes[0], 1) + " " + numberYes[1];
    }

}

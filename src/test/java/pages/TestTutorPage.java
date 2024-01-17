package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class TestTutorPage extends SideMenuPage {

    private final Locator markForReviewButton = button("Mark for review");
    private final Locator removeFromMarkedButton = button("Remove from marked");
    private final Locator addToFlashcardButton = button("Add to flashcard");
    private final Locator removeFromFlashcards = button("Remove from flashcards");
    private final Locator endButton = exactButton("End");
    private final Locator yesButton = exactButton("Yes");
    private final Locator skipButton = exactButton("Skip");
    private final Locator reportAProblem = exactButton("Report a problem");
    private final Locator correctAnswerRadioButton = text("Correct Answer");
    private final Locator correctAnswerBackgroundColor = getPage().locator("[fill='#55B47D']");
    private final Locator h3Header = getPage().locator("h3");
    private final Locator h3HeaderExplanationText = exactHeading("Explanation");
    private final Locator confirmButton = button("Confirm");
    private final Locator explanationTextSpan = getPage().locator("h3~div>span");

    public TestTutorPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public Locator getRemoveFromMarkedButton() {
        return removeFromMarkedButton;
    }

    public Locator getH3Header() {
        return h3Header;
    }

    public Locator getCorrectAnswerBackgroundColor() {
        return correctAnswerBackgroundColor;
    }

    public Locator getH3HeaderExplanationText() {
        return h3HeaderExplanationText;
    }

    public String getExplanationText() {
        return explanationTextSpan.innerText();
    }

    public TestTutorPage clickAddToFlashCardButton() {
        addToFlashcardButton.click();

        return this;
    }

    public void clickRemoveFromFlashcardsButtonIfVisible() {

        reportAProblem.waitFor();
        if (reportAProblem.isVisible()) {
            if (removeFromFlashcards.isVisible()) {
                removeFromFlashcards.click();
            }
        } else {
            System.out.println("reportAProblem not visible");
        }
    }

    public TestTutorPage clickMarkForReviewButton() {
        markForReviewButton.click();

        return this;
    }

    public TestTutorPage clickEndButton() {
        endButton.click();

        return this;
    }

    public TestTutorPage clickYesButton() {
        yesButton.click();

        return this;
    }

    public TestResultPage clickSkipButton() {
        skipButton.click();

        return new TestResultPage(getPage(), getPlaywright());
    }

    public TestTutorPage clickCorrectAnswerRadioButton() {
        correctAnswerRadioButton.click();
        return this;
    }

    public TestTutorPage clickConfirmButton() {
        confirmButton.click();
        return this;
    }
}

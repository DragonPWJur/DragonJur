package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class TestTutorPage extends BaseLocator {
    private final  Locator addToFlashcardButton = button("Add to flashcard");
    private final  Locator removeFromFlashcards = button("Remove from flashcards");
    private final  Locator markForReviewButton = button("Mark for review");
    private final  Locator removeFromMarkedButton = button("Remove from marked");
    private final  Locator endButton = exactButton("End");
    private final  Locator yesButton = exactButton("Yes");
    private final  Locator skipButton = exactButton("Skip");
    private final  Locator closeTheTestButton = exactButton("Close the test");

    public TestTutorPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public Locator getMarkForReviewButton() {
        return markForReviewButton;
    }

    public Locator getRemoveFromMarkedButton() {
        return removeFromMarkedButton;
    }

    public TestTutorPage clickAddToFlashCardButton() {

        if (!removeFromFlashcards.isVisible()) {
            addToFlashcardButton.click();
        } else {
            removeFromFlashcards.click();
        }
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

    public TestTutorPage clickSkipButton() {
        skipButton.click();
        return this;
    }

    public void clickCloseTheTestButton() {
        closeTheTestButton.click();
    }

    public TestTutorPage clickMarkForReview() {
        if (!removeFromMarkedButton.isVisible()) {
            markForReviewButton.click();
        } else {
            removeFromMarkedButton.click();
        }
        return this;
    }
}

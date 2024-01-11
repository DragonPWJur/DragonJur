package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class TestTutorPage extends BaseLocator {
    public Locator addToFlashcardButton = button("Add to flashcard");
    public Locator removeFromFlashcards = button("Remove from flashcards");
    public Locator markForReview = button("Mark for review");
    public Locator removeFromMarked = button("Remove from marked");
    public Locator endButton = exactButton("End");
    public Locator yesButton = exactButton("Yes");
    public Locator skipButton = exactButton("Skip");
    public Locator closeTestButton = exactButton("Close the test");

    public TestTutorPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public TestTutorPage clickAddToFlashCard() {

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

    public void clickCloseTestButton() {
        closeTestButton.click();
    }

    public TestTutorPage clickMarkForReview() {
        if (!removeFromMarked.isVisible()) {
            markForReview.click();
        } else {
            removeFromMarked.click();
        }
        return this;
    }
}

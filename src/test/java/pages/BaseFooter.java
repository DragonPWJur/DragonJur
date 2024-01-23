package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

abstract class BaseFooter<TPage> extends BaseHeader<TPage> {
    private final Locator reportAProblem = exactButton("Report a problem");
    private final Locator markForReviewButton = exactButton("Mark for review");
    private final Locator removeFromMarkedButton = exactButton("Remove from marked");
    private final Locator addToFlashcardButton = exactButton("Add to flashcard");
    private final Locator removeFromFlashcards = exactButton("Remove from flashcards");
    private final Locator resetResultsButton = exactButton("Reset results");

    BaseFooter(Page page) {
        super(page);
    }

    public Locator getRemoveFromMarkedButton() {

        return removeFromMarkedButton;
    }

    public Locator getResetResultsButton() {

        return resetResultsButton;
    }

    public Locator getReportAProblem() {

        return reportAProblem;
    }

    public Locator getMarkForReviewButton() {

        return markForReviewButton;
    }

    public Locator getAddToFlashcardButton() {

        return addToFlashcardButton;
    }

    public Locator getRemoveFromFlashcards() {

        return removeFromFlashcards;
    }

    public TestTutorPage clickAddToFlashCardButton() {
        addToFlashcardButton.click();

        return new TestTutorPage(getPage());
    }

    public TestTutorPage clickMarkForReviewButton() {
        markForReviewButton.click();

        return new TestTutorPage(getPage());
    }

    public TestTutorPage clickReportAProblemButton() {
        reportAProblem.click();

        return new TestTutorPage(getPage());
    }

}

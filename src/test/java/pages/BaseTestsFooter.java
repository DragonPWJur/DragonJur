package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

abstract class BaseTestsFooter<Self extends BaseTestsFooter<Self>>  extends BaseTopMenu {
    private final Locator reportAProblem = exactButton("Report a problem");
    private final Locator markForReviewButton = button("Mark for review");
    private final Locator removeFromMarkedButton = button("Remove from marked");
    private final Locator addToFlashcardButton = button("Add to flashcard");
    private final Locator removeFromFlashcards = button("Remove from flashcards");

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

    protected BaseTestsFooter(Page page) {
        super(page);
    }

    public Locator getRemoveFromMarkedButton() {

        return removeFromMarkedButton;
    }

    public Self clickAddToFlashCardButton() {
        getAddToFlashcardButton().click();

        return (Self) this;
    }

    public Self clickMarkForReviewButton() {
        markForReviewButton.click();

        return (Self) this;
    }

    public Self clickReportButton() {
        reportAProblem.click();

        return (Self) this;
    }

}

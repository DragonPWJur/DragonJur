package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

abstract class BaseFooter<TPage> extends BaseHeader<TPage> {
    private final Locator reportAProblem = exactButton("Report a problem");
    private final Locator markForReviewButton = exactButton("Mark for review");
    private final Locator addToFlashcardButton = exactButton("Add to flashcard");
    private final Locator removeFromFlashcards = exactButton("Remove from flashcards");
    private final Locator resetResultsButton = exactButton("Reset results");
    private final Locator removeFromMarkedButton = locator("button[disabled] + div > button").nth(2);

    BaseFooter(Page page) {
        super(page);
    }

    public Locator getRemoveFromMarkedButton() {

        return removeFromMarkedButton;
    }

    public Locator getResetResultsButton() {

        return resetResultsButton;
    }

    public Locator getMarkForReviewButton() {

        return markForReviewButton;
    }

    public TestTutorPage clickAddToFlashCardButton() {
        addToFlashcardButton.click();

        return new TestTutorPage(getPage());
    }

    public TestTutorPage clickMarkForReviewButton() {
        markForReviewButton.click();

        return new TestTutorPage(getPage());
    }

    public ReportAProblemModal clickReportAProblemButton() {
        reportAProblem.click();

        return new ReportAProblemModal(getPage());
    }

}

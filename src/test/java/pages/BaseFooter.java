package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

abstract class BaseFooter<TPage> extends BaseHeader<TPage> {
    private final Locator reportAProblemButton = exactButton("Report a problem");
    private final Locator markForReviewButton = exactButton("Mark for review");
    private final Locator addToFlashcardButton = exactButton("Add to flashcard");
    private final Locator removeFromFlashcardsButton = exactButton("Remove from flashcards");
    private final Locator resetResultsButton = exactButton("Reset results");
    private final Locator removeFromMarkedButton = exactButton("Remove from marked");

    BaseFooter(Page page) {
        super(page);
    }

    @Step("Collect 'Remove from marked' button.")
    public Locator getRemoveFromMarkedButton() {
        waitForLocator(removeFromMarkedButton, 2000);

        return removeFromMarkedButton;
    }

    public Locator getResetResultsButton() {

        return resetResultsButton;
    }

    public Locator getMarkForReviewButton() {
        waitForLocator(markForReviewButton, 2000);

        return markForReviewButton;
    }

    @Step("Click 'Add to flashcard' button.")
    public TestTutorPage clickAddToFlashCardButton() {
        waitForLocator(addToFlashcardButton, 3000);
        addToFlashcardButton.click();
        waitForLocator(removeFromFlashcardsButton, 3000);

        return new TestTutorPage(getPage());
    }

    @Step("Click 'Mark for review' button.")
    public TestTutorPage clickMarkForReviewButton() {
        markForReviewButton.click();

        return new TestTutorPage(getPage());
    }

    @Step("Click 'Report a problem' button.")
    public ReportAProblemModal clickReportAProblemButton() {
        reportAProblemButton.click();

        return new ReportAProblemModal(getPage());
    }

}

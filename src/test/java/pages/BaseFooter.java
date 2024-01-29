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
    private final Locator removeFromMarkedButton = locator("button[disabled] + div > button").nth(2);

    BaseFooter(Page page) {
        super(page);
    }

    @Step("Collect 'Remove from marked' button.")
    public Locator getRemoveFromMarkedButton() {

        return removeFromMarkedButton;
    }

    @Step("Collect 'Reset results' button.")
    public Locator getResetResultsButton() {

        return resetResultsButton;
    }

    @Step("Collect 'Mark for review' button.")
    public Locator getMarkForReviewButton() {

        return markForReviewButton;
    }

    @Step("Click 'Add to flashcard' button.")
    public TestTutorPage clickAddToFlashCardButton() {
        waitForLocator(addToFlashcardButton, 1000);
        addToFlashcardButton.click();
        waitForLocator(removeFromFlashcardsButton, 1000);

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

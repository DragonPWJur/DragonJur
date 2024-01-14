package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class TestTutorPage extends SideMenuPage {

    private final Locator markForReviewButton = button("Mark for review");
    private final Locator removeFromMarkedButton = button("Remove from marked");
    private final  Locator addToFlashcardButton = button("Add to flashcard");
    private final  Locator removeFromFlashcards = button("Remove from flashcards");
    private final  Locator endButton = exactButton("End");
    private final  Locator yesButton = exactButton("Yes");
    private final  Locator skipButton = exactButton("Skip");
    private final  Locator reportAProblem = exactButton("Report a problem");

    public TestTutorPage(Page page, Playwright playwright) {
        super(page, playwright);
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
}

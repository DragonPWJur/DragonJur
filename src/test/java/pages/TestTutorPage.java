package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import utils.TestUtils;

public class TestTutorPage extends BaseLocator {

    private final Locator markForReviewButton = button("Mark for review");
    private final Locator removeFromMarkedButton = button("Remove from marked");
    private final Locator addToFlashcardButton = button("Add to flashcard");
    private final Locator endButton = exactButton("End");
    private final Locator skipButton = exactButton("Skip");
    private final Locator answerRadioButton = getPage().locator("div label");
    private final Locator correctAnswerRadioButton = text("Correct answer");
    private final Locator confirmButton = button("Confirm");
    private final Locator correctAnswerBackgroundColor = getPage().locator("[fill='#55B47D']");
    private final Locator h3Header = getPage().locator("h3");
    private final Locator h3HeaderExplanationText = exactHeading("Explanation");

    private final Locator explanationTextSpan = getPage().locator("h3~div>span");


    public TestTutorPage(Page page, Playwright playwright) {
        super(page, playwright);
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

    public Locator getRemoveFromMarkedButton() {
        return removeFromMarkedButton;
    }

    public Locator getAnswerRadioButton() {
        return answerRadioButton;
    }

    public TestTutorPage clickMarkForReviewButtonIfVisible() {

        if (markForReviewButton.isVisible()) {
            markForReviewButton.click();
        }
        return this;
    }

    public TestTutorPage clickAddToFlashCardButtonIfVisible() {
        if (addToFlashcardButton.isVisible()) {
            addToFlashcardButton.click();
        }
        return this;
    }

    public TestTutorPage clickEndButton() {
        endButton.click();
        return this;
    }

    public TestTutorPage endTestIfVisible() {
        endTestDialog();
        return this;
    }

    public TestResultPage clickSkipButton() {
        skipButton.click();
        return new TestResultPage(getPage(), getPlaywright());
    }

    public TestTutorPage clickRandomAnswerRadioButton() {
        TestUtils.clickRandomElement(answerRadioButton);
        return this;
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

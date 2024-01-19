package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import utils.TestUtils;

public class TestTutorPage extends BaseSideMenu {

    private final Locator markForReviewButton = button("Mark for review");
    private final Locator removeFromMarkedButton = button("Remove from marked");
    private final Locator addToFlashcardButton = button("Add to flashcard");
    private final Locator removeFromFlashcards = button("Remove from flashcards");
    private final Locator endButton = exactButton("End");


    private final Locator reportAProblem = exactButton("Report a problem");
    private final Locator correctAnswerRadioButton = text("Correct Answer");
    private final Locator correctAnswerBackgroundColor = locator("[fill='#55B47D']");
    private final Locator h3Header = locator("h3");
    private final Locator h3HeaderExplanationText = exactHeading("Explanation");
    private final Locator confirmButton = button("Confirm");
    private final Locator explanationTextSpan = getPage().locator("h3~div>span");
    private final Locator reportAProblemModal = dialog();
    private final Locator describeTheProblemTextarea = textbox();
    private final Locator sendButton = button("Send");
    private final Locator closeButton = button("Close");
    private final Locator reportSentSuccessfullyMessage = exactText("The report has been sent successfully");

    public TestTutorPage(Page page) {
        super(page);
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

    @Step("Click 'Add to flashcard' button to mark current test question for re-checking.")
    public TestTutorPage clickAddToFlashCardButton() {
        addToFlashcardButton.click();

        return this;
    }

    public TestTutorPage clickMarkForReviewButton() {
        markForReviewButton.click();

        return this;
    }

    @Step("Click 'End' button to end the test")
    public TestTutorPage clickEndButton() {
        endButton.click();

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

    public TestTutorPage clickReportButton() {
        reportAProblem.click();

        return this;
    }

    public TestTutorPage inputSymbolsIntoReportAProblemTextarea() {
        if (describeTheProblemTextarea.isVisible()) {
            describeTheProblemTextarea.fill(TestUtils.geteRandomString(10));
        }
        return this;
    }

    public TestTutorPage clickSendButton() {
        sendButton.click();

        return this;
    }

    public Locator getReportSentSuccessfullyMessage() {
        closeButton.waitFor();
        if (closeButton.isVisible()) {
            return reportSentSuccessfullyMessage;
        }

        return null;
    }

    public Locator getReportAProblemModal() {
        return reportAProblemModal;
    }
}

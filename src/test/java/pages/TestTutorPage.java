package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import utils.TestUtils;

public class TestTutorPage extends SideMenuPage {

    private final Locator markForReviewButton = button("Mark for review");
    private final Locator removeFromMarkedButton = button("Remove from marked");
    private final Locator addToFlashcardButton = button("Add to flashcard");
    private final Locator removeFromFlashcards = button("Remove from flashcards");
    private final Locator endButton = exactButton("End");
    private final Locator yesButton = exactButton("Yes");
    private final Locator skipButton = exactButton("Skip");
    private final Locator reportAProblem = exactButton("Report a problem");
    private final Locator reportAProblemModal = dialog();
    private final Locator describeTheProblemTextarea = getPage().getByRole(AriaRole.TEXTBOX);
    private final Locator sendButton = button("Send");
    private final Locator closeButton = button("Close");
    private final Locator reportSentSuccessfullyMessage = exactText("The report has been sent successfully");

    public TestTutorPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public Locator getRemoveFromMarkedButton() {
        return removeFromMarkedButton;
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

    public TestTutorPage clickMarkForReviewButton() {
        markForReviewButton.click();

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

    public TestResultPage clickSkipButton() {
        skipButton.click();

        return new TestResultPage(getPage(), getPlaywright());
    }

    public TestTutorPage clickReportButton() {
        reportAProblem.click();

        return this;
    }

    public TestTutorPage inputSymbolsIntoReportAProblemTextarea() {
        if (describeTheProblemTextarea.isVisible()) {
            describeTheProblemTextarea.fill(TestUtils.geteRandomString(10) + " " + TestUtils.geteRandomString(5));
        }
//        getPage().pause();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public TestTutorPage clickSendButton() {
        sendButton.click();

        return this;
    }

    public Locator getDescribeTheProblemTextarea() {
        return describeTheProblemTextarea;
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

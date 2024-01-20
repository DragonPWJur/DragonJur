package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import utils.TestUtils;

import java.util.List;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

abstract class BaseTestsPage<Self extends BaseTestsPage<Self>> extends BaseLocator {

    private final Locator testQuestion = getPage().locator("#root form span");
    private final Locator questionMarkText = text("?");
    private final Locator testRadioButtons = radio();
    private final List<Locator> radioButtons = radioButtonsAll();
    private final Locator markForReviewButton = button("Mark for review");
    private final Locator removeFromMarkedButton = button("Remove from marked");
    private final Locator addToFlashcardButton = button("Add to flashcard");
    private final Locator removeFromFlashcards = button("Remove from flashcards");
    private final Locator endButton = exactButton("End");
    private final Locator yesButton = exactButton("Yes");
    private final Locator skipButton = exactButton("Skip");
    private final Locator reportAProblem = exactButton("Report a problem");
    private final Locator correctAnswerRadioButton = text("Correct Answer");
    private final Locator correctAnswerBackgroundColor = locator("[fill='#55B47D']");
    private final Locator h3Header = locator("h3");
    private final Locator h3HeaderExplanationText = exactHeading("Explanation");
    private final Locator confirmButton = button("Confirm");
    private final Locator explanationTextSpan = locator("h3~div>span");
    private final Locator reportAProblemModal = dialog();
    private final Locator describeTheProblemTextarea = textbox();
    private final Locator sendButton = button("Send");
    private final Locator closeButton = button("Close");
    private final Locator reportSentSuccessfullyMessage = exactText("The report has been sent successfully");
    private final Locator nextQuestionButton = button("Next question");
    private final Locator finishTestButton = button("Finish test");
    private final Locator listOfIncorrectAnswers = locator("//label[not(contains(text(), 'Correct Answer'))]");
    private final Locator congratulationPoints = locator("div[role='dialog']")
            .locator("span")
            .filter(new Locator.FilterOptions().setHasText(Pattern.compile("\\d+")));
    private final Locator nextButton = exactButton("Next");
    private final Locator testProgressbarPoints = locator("div>svg.CircularProgressbar+div>span").first();
    private final Locator okButton = exactButton("Ok");

    protected BaseTestsPage(Page page, Playwright playwright) {
        super(page, playwright);
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

    public int countTestRadioButtons() {
        return testRadioButtons.count();
    }

    public Locator getTestQuestion() {
        return testQuestion;
    }

    public Self clickAddToFlashCardButton() {
        addToFlashcardButton.click();

        return (Self) this;
    }

    public Self clickMarkForReviewButton() {
        markForReviewButton.click();

        return (Self) this;
    }

    public Self clickEndButton() {
        endButton.click();

        return (Self) this;
    }

    public Self clickYesButton() {
        yesButton.click();

        return (Self) this;
    }

    public TestResultPage clickSkipButton() {
        skipButton.click();

        return new TestResultPage(getPage(), getPlaywright());
    }

    public int getAnswersCount() {
        return radioButtons.size();
    }

    public Locator getQuestionMark() {
        return questionMarkText;
    }

    public Self clickCorrectAnswerRadioButton() {
        correctAnswerRadioButton.click();
        return (Self) this;
    }

    public Self clickConfirmButton() {
        confirmButton.click();
        return (Self) this;
    }

    public Self clickReportButton() {
        reportAProblem.click();

        return (Self) this;
    }

    public Self inputSymbolsIntoReportAProblemTextarea() {
        if (describeTheProblemTextarea.isVisible()) {
            describeTheProblemTextarea.fill(TestUtils.geteRandomString(10));
        }
        return (Self) this;
    }

    public Self clickSendButton() {
        sendButton.click();

        return (Self) this;
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

    public Locator getListOfIncorrectAnswers() {
        return listOfIncorrectAnswers;
    }

    public void clickNextQuestionButton() {
        nextQuestionButton.click();
    }

    public Self clickFinishTestButton() {
        finishTestButton.click();

        return (Self) this;
    }

    public Self clickRandomIncorrectAnswer() {
        TestUtils.clickRandomElement(getListOfIncorrectAnswers());

        return (Self) this;
    }

    public String getCongratulationPointsText() {
        return congratulationPoints.innerText();
    }

    public int getCongratulationPoints() {
        String pointsText = getCongratulationPointsText();

        return parseInt(pointsText);
    }

    public Self clickTestNextButton() {
        nextButton.click();

        return (Self) this;
    }
    public String getTestProgressbarPointsText() {
        return testProgressbarPoints.innerText();
    }

    public int getTestProgressbarPointsNumber() {
        String pointsText = getTestProgressbarPointsText();

        return parseInt(pointsText);
    }

    public TestResultPage clickTestOkButton() {
        okButton.click();

        return new TestResultPage(getPage(), getPlaywright());
    }
}
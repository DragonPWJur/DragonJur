package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import pages.constants.Constants;

import java.util.List;

public final class TestTutorPage extends BaseTestsPage<TestTutorPage> {

    private final List<Locator> listOfActiveButtons = button("Previous").locator("xpath=following-sibling::div[1]//button").all();

    private final Locator testQuestion = locator("form span");
    private final Locator answerRadioButton = radio();

    TestTutorPage(Page page) {
        super(page);
    }

    @Override
    public TestTutorPage init() {

        return createPage(new TestTutorPage(getPage()), Constants.TEST_TUTOR_END_POINT);
    }

    @Step("Collect list of active button names in the footer")
    public List<String> listOfButtonNamesInFooter() {

        return listOfActiveButtons.stream().map(Locator::innerText).toList();
    }

    public int countAnswersRadioButtons() {

        return answerRadioButton.count();
    }

    public Locator getTestQuestion() {

        return testQuestion;
    }


//    public Locator getRemoveFromMarkedButton() {
//        return removeFromMarkedButton;
//    }
//
//    public Locator getH3Header() {
//        return h3Header;
//    }
//
//    public Locator getCorrectAnswerBackgroundColor() {
//        return correctAnswerBackgroundColor;
//    }
//
//    public Locator getExplanationHeader() {
//        return h3HeaderExplanationText;
//    }
//
//    public String getExplanationTextLocator() {
//        return explanationTextSpan.innerText();
//    }
//
//    @Step("Click 'Add to flashcard' button to mark current test question for re-checking.")
//    public Locator getListOfIncorrectAnswers() {
//        return listOfIncorrectAnswers;
//    }
//
//    public TestTutorPage clickAddToFlashCardButton() {
//        addToFlashcardButton.click();
//
//        return this;
//    }
//
//    public TestTutorPage clickMarkForReviewButton() {
//        markForReviewButton.click();
//
//        return this;
//    }
//
//    @Step("Click 'End' button to end the test")
//    public TestTutorPage clickEndButton() {
//        endButton.click();
//
//        return this;
//    }
//
//    public TestTutorPage clickCorrectAnswerRadioButton() {
//        correctAnswerRadioButton.click();
//        return this;
//    }
//
//    public TestTutorPage clickConfirmButton() {
//        confirmButton.click();
//        return this;
//    }
//
//
//    public void clickNextQuestionButton() {
//        nextQuestionButton.click();
//    }
//
//    public TestTutorPage clickFinishTestButton() {
//        finishTestButton.click();
//
//        return this;
//    }
//
//    public TestTutorPage clickRandomIncorrectAnswer() {
//        TestUtils.clickRandomElement(getListOfIncorrectAnswers());
//
//        return this;
//    }
//
//    public TestTutorPage clickReportButton() {
//        reportAProblem.click();
//
//        return this;
//    }
//
//    public TestTutorPage inputSymbolsIntoReportAProblemTextarea() {
//        if (describeTheProblemTextarea.isVisible()) {
//            describeTheProblemTextarea.fill(TestUtils.geteRandomString(10));
//        }
//        return this;
//    }
//
//    public TestTutorPage clickSendButton() {
//        sendButton.click();
//
//        return this;
//    }
//
//    public Locator getReportSentSuccessfullyMessage() {
//        closeButton.waitFor();
//        if (closeButton.isVisible()) {
//            return reportSentSuccessfullyMessage;
//        }
//
//        return null;
//    }
//
//    public Locator getReportAProblemModal() {
//        return reportAProblemModal;
//    }
//
//    public String getCongratulationPointsText() {
//        return congratulationPoints.innerText();
//    }
//
//    public int getCongratulationPoints() {
//        String pointsText = getCongratulationPointsText();
//
//        return parseInt(pointsText);
//    }
//
//    public TestTutorPage clickNextButton() {
//        nextButton.click();
//
//        return this;
//    }
//    public String getTestProgressbarPointsText() {
//        return testProgressbarPoints.innerText();
//    }
//
//    public int getTestProgressbarPointsNumber() {
//        String pointsText = getTestProgressbarPointsText();
//
//        return parseInt(pointsText);
//    }
//
//    public TestResultPage clickTestOkButton() {
//        okButton.click();
//
//        return new TestResultPage(getPage(), getPlaywright());
//    }
}


package pages;

import com.microsoft.playwright.Page;
import pages.constants.Constants;

public final class TestTutorPage extends BaseFooter<TestTutorPage> {

    TestTutorPage(Page page) {
        super(page);
    }

    @Override
    public TestTutorPage init() {

        return createPage(new TestTutorPage(getPage()), Constants.TEST_TUTOR_END_POINT);
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


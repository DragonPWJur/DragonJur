package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import pages.constants.Constants;

public final class TestTutorPage extends BaseTestsPage<TestTutorPage> implements IRandom {

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
    @Step("Click 'Add to flashcard' button to mark current test question for re-checking.")
    public Locator getListOfIncorrectAnswers() {

        return getIncorrectAnswer();
    }
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
    public TestTutorPage clickCorrectAnswerRadioButton() {
        getCorrectAnswer().click();
        return this;
    }

    public TestTutorPage clickConfirmButton() {
        getConfirmButton().click();
        return this;
    }


    public void clickNextQuestionButton() {
        getNextQuestionButton().click();
    }

    public TestTutorPage clickFinishTestButton() {
        getFinishTestButton().click();

        return this;
    }

    public TestTutorPage clickRandomIncorrectAnswer() {
        clickRandomElement(getListOfIncorrectAnswers());

        return this;
    }

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

//
//    public int getTestProgressbarPointsNumber() {
//        String pointsText = getTestProgressbarPointsText();
//
//        return parseInt(pointsText);
//    }
//

}


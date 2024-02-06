package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import pages.constants.Constants;

import java.util.List;

public final class TestTutorPage extends BaseTestsPage<TestTutorPage> implements IRandom {

    private final List<Locator> listOfActiveButtons = button("Previous").locator("xpath=following-sibling::div[1]//button").all();
    private final Locator testQuestion = locator("form span");
    private final Locator answerRadioButton = radio();

    TestTutorPage(Page page) {
        super(page);
    }

    @Override
    TestTutorPage init() {

        return createPage(new TestTutorPage(getPage()), Constants.TEST_TUTOR_END_POINT);
    }

    public List<String> listOfButtonNamesInFooter() {

        return listOfActiveButtons.stream().map(Locator::innerText).toList();
    }

    public int countAnswers() {

        return answerRadioButton.count();
    }

    public Locator getTestQuestion() {

        return testQuestion;
    }

    @Step("Click 'Add to flashcard' button to mark current test question for re-checking.")
    public Locator getListOfIncorrectAnswers() {

        return getIncorrectAnswer();
    }

    @Step("Click Correct Answer.")
    public TestTutorPage clickCorrectAnswer() {
        getCorrectAnswer().click();
        return this;
    }

    @Step("Click 'Confirm' button.")
    public TestTutorPage clickConfirmButton() {
        getConfirmButton().click();
        return this;
    }

    @Step("Click 'Next Question' button.")
    public void clickNextQuestionButton() {
        getNextQuestionButton().click();
    }

    @Step("click 'Finish Test' button.")
    public TestTutorPage clickFinishTestButton() {
        getFinishTestButton().click();

        return this;
    }

    @Step("Click wrong answer randomly.")
    public TestTutorPage clickRandomIncorrectAnswer() {
        clickRandomElement(getListOfIncorrectAnswers());

        return this;
    }
}


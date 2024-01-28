package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

import java.util.List;

abstract class BaseTestsPage<TPage> extends BaseFooter<TPage> {
    private final Locator testQuestion = locator("form span");
    private final Locator questionMark = exactText("?");
    private final List<Locator> radioButtons = allItems("input[type='radio']");
//    private final List<Locator> radioButtons = allRadioButtons();
    private final Locator correctAnswer = text("Correct Answer");
    private final Locator correctAnswerBackgroundColor55B47D = locator("[fill='#55B47D']");
    private final Locator h3Header = locator("div h3");
    private final Locator explanationHeader = exactHeading("Explanation");
    private final Locator explanationTextLocator = locator("h3~div>span");
    private final Locator confirmButton = exactButton("Confirm");
    private final Locator nextQuestionButton = button("Next question");
    private final Locator finishTestButton = button("Finish test");
    private final Locator incorrectAnswer = locator("//label[not(contains(text(), 'Correct Answer'))]");

    BaseTestsPage(Page page) {
        super(page);
    }

    public List<Locator> getRadioButtons() {

        return radioButtons;
    }

    public Locator getCorrectAnswer() {

        return correctAnswer;
    }

    public Locator getCorrectAnswerBackgroundColor55B47D() {

        return correctAnswerBackgroundColor55B47D;
    }

    public Locator getH3Header() {

        return h3Header;
    }

    public Locator getExplanationHeader() {

        return explanationHeader;
    }

    public Locator getExplanationTextLocator() {

        return explanationTextLocator;
    }

    public Locator getConfirmButton() {

        return confirmButton;
    }

    public Locator getNextQuestionButton() {

        return nextQuestionButton;
    }

    public Locator getFinishTestButton() {

        return finishTestButton;
    }

    public Locator getIncorrectAnswer() {

        return incorrectAnswer;
    }

    @Step("Get test question")
    public Locator getTestQuestion() {

        return testQuestion;
    }

    @Step("Count answers radio buttons")
    public int getAnswersCount() {

        return radioButtons.size();
    }

    public Locator getQuestionMark() {

        return questionMark;
    }

//    public Locator getCorrectAnswerBackgroundColor() {
//
//        return correctAnswerBackgroundColor;
//    }
//
//    public Locator getExplanationHeader() {
//
//        return explanationHeader;
//    }
//
//    public String getExplanationText() {
//
//        return explanationTextLocator.innerText();
//    }
//
//    public int countAnswersRadioButtons() {
//
//        return answerRadioButton.count();
//    }
//
//    public int getAnswersCount() {
//
//        return radioButtons.size();
//    }

//    public List<Locator> getTestAnswers() {
//
//        return radioButtons;
//    }

//    public Self clickCorrectAnswerRadioButton() {
//        correctAnswer.click();
//
//        return (Self) this;
//    }
//
//    public Self clickConfirmButton() {
//        confirmButton.click();
//
//        return (Self) this;
//    }

//    public Locator getIncorrectAnswer() {
//
//        return incorrectAnswer;
//    }
//
//    public void clickNextQuestionButton() {
//
//        nextQuestionButton.click();
//    }

//
//    public Self clickFinishTestButton() {
//        finishTestButton.click();
//
//        return (Self) this;
//    }

//    public Self clickRandomIncorrectAnswer() {
//        TestUtils.clickRandomElement(getIncorrectAnswer());
//
//        return (Self) this;
//    }
}
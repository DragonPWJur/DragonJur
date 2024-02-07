package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

import java.util.List;

abstract class BaseTestsPage<TPage> extends BaseFooter<TPage> {
    private final Locator testQuestion = locator("form span");
    private final List<Locator> radioButtons = allItems("input[type='radio']");
    private final Locator correctAnswer = text("Correct Answer");
    private final Locator correctAnswerBackgroundColor55B47D = locator("[fill='#55B47D']");
    private final Locator h3Header = locator("div h3");
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

    public int getAnswersCount() {

        return radioButtons.size();
    }
}
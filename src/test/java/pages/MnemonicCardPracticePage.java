package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class MnemonicCardPracticePage extends BaseLocator {

    private final Locator mnemonicCardPracticeHeader = locator("header>div>span");
    private final Locator answersToQuestion = text("Answers to question");
    private final Locator mnemonicWords = text("Mnemonic words");

    protected MnemonicCardPracticePage(Page page) {
        super(page);
    }

    public Locator getMnemonicCardPracticeHeader() {
        return mnemonicCardPracticeHeader;
    }

    public Locator getAnswersToQuestion() {
        return answersToQuestion;
    }

    public Locator getMnemonicWords() {
        return mnemonicWords;
    }
}

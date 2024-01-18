package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class MnemonicCardPracticePage extends BaseLocator {

    private final Locator mnemonicCardPracticeHeader = locator("div~span");
    private final Locator answersToQuestion = text("Answers to question");
    private final Locator mnemonicWords = text("Mnemonic words");

    protected MnemonicCardPracticePage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public Locator getMnemonicCardPracticeHeader() {
        return mnemonicCardPracticeHeader;
    }

    public String getMnemonicCardHeaderText() {
        return getMnemonicCardPracticeHeader().nth(0).innerText();
    }

    public Locator getAnswersToQuestion() {
        return answersToQuestion;
    }

    public Locator getMnemonicWords() {
        return mnemonicWords;
    }
}

package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.constants.Constants;

public final class MnemonicCardPracticePage extends BaseHeader<MnemonicCardPracticePage> {
    private final Locator mnemonicCardPracticeHeader = locator("header>div>span");
    private final Locator answersToQuestion = text("Answers to question");
    private final Locator mnemonicWords = text("Mnemonic words");

    MnemonicCardPracticePage(Page page) {
        super(page);
    }

    @Override
    public MnemonicCardPracticePage init() {

        return createPage(new MnemonicCardPracticePage(getPage()), Constants.MNEMONIC_CARD_PRACTICE_END_POINT);
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

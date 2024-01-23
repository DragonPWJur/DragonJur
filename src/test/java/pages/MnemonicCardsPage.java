package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.constants.Constants;

public final class MnemonicCardsPage extends BaseSideMenu<MnemonicCardsPage> {
    private final Locator mnemonicCardHeader = locator("div~span").first();
    private final Locator mnemonicCardTotalQuantity = locator("div~span").last();
    private final Locator startPracticeButton = button("Start practice");

    MnemonicCardsPage(Page page) {
        super(page);
    }

    @Override
    public MnemonicCardsPage init() {

        return createPage(new MnemonicCardsPage(getPage()), Constants.MNEMONIC_CARDS_END_POINT);
    }

    public String getMnemonicCardHeaderText() {
        String mnemonicHeader = mnemonicCardHeader.innerText();
        int mnemonicHeaderLength = mnemonicHeader.length();
        if (mnemonicHeader.contains("...")) {
            mnemonicHeader = mnemonicHeader.substring(0, mnemonicHeaderLength - 3);
        }

        return mnemonicHeader;
    }

    public Locator getMnemonicCardHeader() {

        return mnemonicCardHeader;
    }

    public Locator getMnemonicCardTotalQuantity() {

        return mnemonicCardTotalQuantity;
    }

    public MnemonicCardPracticePage clickStartPracticeButton() {
        startPracticeButton.click();

        return new MnemonicCardPracticePage(getPage()).init();
    }
}

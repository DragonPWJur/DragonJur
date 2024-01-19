package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MnemonicCardsPage extends BaseSideMenu {

    private final Locator mnemonicCardHeader = locator("div~span").first();
    private final Locator mnemonicCardTotalQuantity = locator("div~span").last();
    private final Locator startPracticeButton = button("Start practice");

    public MnemonicCardsPage(Page page) {
        super(page);
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

        return new MnemonicCardPracticePage(getPage());
    }
}

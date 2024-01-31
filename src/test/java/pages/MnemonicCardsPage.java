package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import pages.constants.Constants;

public final class MnemonicCardsPage extends BaseHeader<MnemonicCardsPage> {
    private final Locator startPracticeButton = button("Start practice");
    private final Locator mnemonicCardTotalQuantity = locator("div>button[disabled] + span");

    MnemonicCardsPage(Page page) {
        super(page);
    }

    @Override
    public MnemonicCardsPage init() {

        return createPage(new MnemonicCardsPage(getPage()), Constants.MNEMONIC_CARDS_END_POINT);
    }

    public String getMnemonicCardTotalQuantity() {

        return mnemonicCardTotalQuantity.innerText().split("/")[1];
    }
@Step("Click 'Start Practice' button.")
    public MnemonicCardPracticePage clickStartPracticeButton() {
        startPracticeButton.click();

        return new MnemonicCardPracticePage(getPage()).init();
    }
}

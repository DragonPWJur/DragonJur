package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import pages.constants.Constants;

import java.util.List;

public final class MnemonicCardListPage extends BaseSideMenu<MnemonicCardListPage> implements IRandom {

    private final List<Locator> allStacks = allCheckboxes("button:has(span)");
    private final Locator randomStack = getRandomValue(allStacks);

    MnemonicCardListPage(Page page) {
        super(page);
    }

    @Override
    public MnemonicCardListPage init() {

        return createPage(new MnemonicCardListPage(getPage()), Constants.MNEMONIC_CARDS_LIST_END_POINT);
    }

    private String getRandomStackText() {

        return randomStack.innerText();
    }

    public String getRandomStackName() {
        String[] textArray =getRandomStackText().split("\n");

        return textArray[0];
    }

    public String getRandomStackCardsAmount() {
        String[] textArray =getRandomStackText().split("\n")[1].split(" ");

        return textArray[0];
    }
@Step("Click random 'Mnemonic Cards' stack.")
    public MnemonicCardsPage clickRandomMnemonicCardsStack() {
        randomStack.click();

        return new MnemonicCardsPage(getPage()).init();
    }
}

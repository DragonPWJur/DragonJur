package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.constants.Constants;

import java.util.List;

public final class MnemonicCardListPage extends BaseSideMenu<MnemonicCardListPage> implements IRandom {

    private final List<Locator> allStacks = locator("button:has(span)").all();

    private final int randomIndex = getRandomNumber(allStacks);

    MnemonicCardListPage(Page page) {
        super(page);
    }

    @Override
    public MnemonicCardListPage init() {

        return createPage(new MnemonicCardListPage(getPage()), Constants.MNEMONIC_CARDS_LIST_END_POINT);
    }

    private String getRandomStackText(int randomIndex) {

        return allStacks.get(randomIndex).innerText();
    }

    public String getRandomStackName() {
        String[] textArray = getRandomStackText(randomIndex).split("\n");

        return textArray[0];
    }

    public String getRandomStackCardsAmount() {
        String[] textArray = getRandomStackText(randomIndex).split("\n")[1].split(" ");

        return textArray[0];
    }

    public MnemonicCardsPage clickRandomMnemonicCardsStack() {
        allStacks.get(randomIndex).click();

        return new MnemonicCardsPage(getPage()).init();
    }

//    default int getRandomNumber(List<Locator> list) {
//
//        return new Random().nextInt(0, list.size() - 1);
//    }

}

package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.constants.Constants;

public final class MnemonicCardListPage extends BaseSideMenu<MnemonicCardListPage> implements IRandom {
    private final Locator listOfStacks = locator("button:has(span)");

    private final String[] randomStack = getRandomStackText();

    public MnemonicCardListPage(Page page) {
        super(page);
    }

    @Override
    public MnemonicCardListPage createPage() {

        return init(new MnemonicCardListPage(getPage()), Constants.MNEMONIC_CARDS_LIST_END_POINT);
    }

   public Locator getListOfStacks() {
       System.out.println("listOfStacks = " + listOfStacks);

        return listOfStacks;
    }

    public String[] getRandomStackText() {
        String text = getRandomTextValue(getListOfStacks());
        System.out.println("random value = " + text);
        String[] arrayOfNamesAndQuantity = text.split("\n");
        String expectedQuantity = arrayOfNamesAndQuantity[1].split(" ")[0];
        arrayOfNamesAndQuantity[1] = expectedQuantity;

        return arrayOfNamesAndQuantity;
    }

    public String getExpectedStackName() {

        return randomStack[0];
    }

    public String getExpectedStackQuantity() {

        return randomStack[1];
    }

    public void clickRandomMnemonicCardsStack() {
        exactText(getExpectedStackName()).click();
    }

    public MnemonicCardsPage clickRandomMnemonicCardsStackAndGo() {
        exactText(getExpectedStackName()).click();

        return new MnemonicCardsPage(getPage());
    }
}

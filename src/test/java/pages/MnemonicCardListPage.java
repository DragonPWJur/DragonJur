package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import utils.TestUtils;

public class MnemonicCardListPage extends SideMenuPage {
    private final Locator listOfStacks = locator("button:has(span)");

    protected MnemonicCardListPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public Locator getListOfStacks() {
        return waitForListOfElementsLoaded(listOfStacks);
    }

    public String[] getRandomStackText() {
        String text = TestUtils.getRandomTextValue(getListOfStacks());
        String[] arrayOfNamesAndQuantity = text.split("\n");
        String expectedQuantity = arrayOfNamesAndQuantity[1].split(" ")[0];
        arrayOfNamesAndQuantity[1] = expectedQuantity;

        return arrayOfNamesAndQuantity;
    }

    public MnemonicCardListPage clickStack(String text) {
        exactText(text).click();

        return this;
    }
}

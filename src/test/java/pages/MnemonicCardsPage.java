package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class MnemonicCardsPage extends SideMenuPage{

    private final Locator mnemonicCardHeader = locator("div:has(button)+span").first();
    private final Locator mnemonicCardTotalQuantity = locator("div:has(button)+span").last();

    public MnemonicCardsPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public Locator getMnemonicCardHeader() {
        return mnemonicCardHeader;
    }

    public Locator getMnemonicCardTotalQuantity() {
        return mnemonicCardTotalQuantity;
    }
}

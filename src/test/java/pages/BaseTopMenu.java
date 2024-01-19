package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

abstract class BaseTopMenu extends BaseModal {

    private final Locator yesCardsAmountText = locator("span").getByText("Yes");

    protected BaseTopMenu(Page page) {
        super(page);
    }

    public Locator packNameOnTopMenu(String name) {
        return text(name);
    }

    public Locator cardsTotalAmount(String total) {
        return text(total + " Total");
    }

    public String getYesCardsAmount() {
        String[] text = yesCardsAmountText.innerText().split("\n");

        return text[0];
    }

}

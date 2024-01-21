package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

abstract class BaseTopMenu<Self extends BaseTopMenu<Self>>  extends BaseModal {

    private final Locator yesCardsAmountText = locator("span").getByText("Yes");
    private final Locator endButton = exactButton("End");

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

    public Self clickEndButton() {
        endButton.click();

        return (Self) this;
    }
}

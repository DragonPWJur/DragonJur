package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

abstract class BaseTopMenu extends BaseModal {
    private final Locator yesCardsAmountText = locator("span").getByText("Yes");
    private final Locator endButton = exactButton("End");
    private final Locator flashcardsButton = button( "Flashcards /");
    private final Locator packName = locator("div:has(svg) + span");

    protected BaseTopMenu(Page page) {
        super(page);
    }

    public Locator cardsTotalAmount(String total) {

        return text(total + " Total");
    }

    public String getYesCardsAmount() {
        String[] textToArray = yesCardsAmountText.innerText().split(" ");

        return textToArray[0];
    }

    public TestTutorPage clickEndButton() {
        endButton.click();

        return new TestTutorPage(getPage());
    }

    public Locator getPackName() {

        return packName;
    }

    public FlashcardPacksPage clickFlashcardsTopMenu() {
        flashcardsButton.click();

        return new FlashcardPacksPage(getPage());
    }
}

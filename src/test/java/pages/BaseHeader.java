package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

abstract class BaseHeader<TPage> extends BaseModal<TPage> {
    private final Locator endButton = exactButton("End");
    private final Locator yesCardsAmount = locator("span").getByText("Yes");
    private final Locator flashcardsButton = button("Flashcards /");
    private final Locator packName = locator("div:has(svg) + span");

    protected BaseHeader(Page page) {
        super(page);
    }

    public Locator cardsTotalAmount(String total) {

        return text(total + " Total");
    }

    public String getYesCardsAmount() {
        String[] textToArray = yesCardsAmount.innerText().split(" ");

        return textToArray[0];
    }

    public TestTutorPage clickEndButton() {
        endButton.click();

        return new TestTutorPage(getPage()).createPage();
    }

    public String getPackName() {
        String flashcardHeader = packName.innerText();
        int flashcardHeaderLength = flashcardHeader.length();
        if (flashcardHeader.contains("...")) {
            flashcardHeader = flashcardHeader.substring(0, flashcardHeaderLength - 3);
        }

        return flashcardHeader;
    }

    public FlashcardsPackIDPage clickFlashcardsTopMenu() {
        flashcardsButton.click();

        return new FlashcardsPackIDPage(getPage()).createPage();
    }
}

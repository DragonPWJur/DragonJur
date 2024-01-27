package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.regex.Pattern;

abstract class BaseHeader<TPage> extends BaseModal<TPage> {
    private final Locator endButton = exactButton("End");
    private final Locator yesCardsAmount = locator("span").getByText("Yes");
    private final Locator kindaCardsAmount = locator("span").getByText("Kinda");
//    private final Locator noCardsAmount = locator("span").getByText("No");
    private final Locator noCardsAmount = locator("div")
        .filter(new Locator.FilterOptions().setHasText(Pattern.compile("\\d+"))).getByText("\nNo").first();
    private final Locator flashcardsButton = button("Flashcards /");
    private final Locator packName = locator("div:has(svg) + span");
    private final Locator mnemonicCardHeader = locator("div~span").first();

    BaseHeader(Page page) {
        super(page);
    }

    public Locator cardsTotalText(String total) {

        return text(total + " Total");
    }

    public String getYesCardsAmount() {
        String[] textToArray = yesCardsAmount.innerText().split(" ");

        return textToArray[0];
    }

    public String getKindaCardsAmount() {
        String[] textToArray = kindaCardsAmount.innerText().split(" ");

        return textToArray[0];
    }

    public String getNoCardsAmount() {
        String[] textToArray = noCardsAmount.innerText().split(" ");

        return textToArray[0];
    }

    public TestTutorPage clickEndButton() {
        endButton.click();

        return new TestTutorPage(getPage()).init();
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

        return new FlashcardsPackIDPage(getPage()).init();
    }

    public String getMnemonicCardName() {
        String mnemonicHeader = mnemonicCardHeader.innerText();
        int mnemonicHeaderLength = mnemonicHeader.length();
        if (mnemonicHeader.contains("...")) {
            mnemonicHeader = mnemonicHeader.substring(0, mnemonicHeaderLength - 3);
        }

        return mnemonicHeader;
    }
}

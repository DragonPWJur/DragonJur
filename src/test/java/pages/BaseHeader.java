package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

abstract class BaseHeader<TPage> extends BaseModal<TPage> {
    private final Locator endButton = exactButton("End");
    private final Locator yesCardsAmount = locator("span.eXzdQE").nth(3);
    private final Locator kindaCardsAmount = locator("span.eXzdQE").nth(2);
    private final Locator noCardsAmount = locator("span.eXzdQE").nth(1);
    private final Locator packName = locator("div:has(svg) + span");
    private final Locator mnemonicCardHeader = locator("div~span").first();
    private final Locator addNewCourseHeader = locator("div:has(svg + button) > span");

    BaseHeader(Page page) {
        super(page);
    }

    @Step("Collect total amount of cards on the header ({initialTotal} Total).")
    public Locator cardsTotalText(String initialTotal) {

        return text(initialTotal + " Total");
    }

    @Step("Collect 'Yes' cards amount on the header.")
    public String getYesCardsAmount() {
        waitForLocator(yesCardsAmount, 1000);
        String[] textToArray = yesCardsAmount.innerText().split(" ");

        return textToArray[0];
    }

    @Step("Collect 'Kinda' cards amount on the header.")
    public String getKindaCardsAmount() {
        waitForLocator(kindaCardsAmount, 1000);
        String[] textToArray = kindaCardsAmount.innerText().split(" ");

        return textToArray[0];
    }

    @Step("Collect 'No' cards amount on the header.")
    public String getNoCardsAmount() {
        waitForLocator(noCardsAmount, 1000);
        String[] textToArray = noCardsAmount.innerText().split(" ");

        return textToArray[0];
    }

    @Step("Click 'End' button.")
    public TestTutorPage clickEndButton() {
        endButton.click();

        return new TestTutorPage(getPage()).init();
    }

    @Step("Collect the flashcard pack name in the header.")
    public String getPackName() {
        waitForLocator(packName, 1000);
        String flashcardHeader = packName.innerText();
        int flashcardHeaderLength = flashcardHeader.length();
        if (flashcardHeader.contains("...")) {
            flashcardHeader = flashcardHeader.substring(0, flashcardHeaderLength - 3);
        }

        return flashcardHeader;
    }

    public String getMnemonicCardName() {
        waitForLocator(mnemonicCardHeader, 1000);
        String mnemonicHeader = mnemonicCardHeader.innerText();
        int mnemonicHeaderLength = mnemonicHeader.length();
        if (mnemonicHeader.contains("...")) {
            mnemonicHeader = mnemonicHeader.substring(0, mnemonicHeaderLength - 3);
        }

        return mnemonicHeader;
    }

    public Locator getAddNewCourseHeader() {

        return addNewCourseHeader;
    }
}

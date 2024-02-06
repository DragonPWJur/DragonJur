package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

abstract class BaseHeader<TPage> extends BaseModal<TPage> {
    private final Locator endButton = exactButton("End");
    private final Locator yesCardsAmount = locator("div>div:not(:has(button)) + span").nth(3);
    private final Locator kindaCardsAmount = locator("div>div:not(:has(button)) + span").nth(2);
    private final Locator noCardsAmount = locator("div>div:not(:has(button)) + span").nth(1);
    private final Locator packName = locator("div:has(svg) + span");
    private final Locator mnemonicCardsStackHeader = locator("div~span").first();
    private final Locator addNewCourseHeader = locator("div:has(svg + button) > span");

    BaseHeader(Page page) {
        super(page);
    }

    public Locator cardsTotalText(String initialTotal) {

        return text(initialTotal + " Total");
    }

    @Step("Collect 'Yes' cards amount on the header.")
    public String getYesCardsAmount() {
        waitForLocator(yesCardsAmount, 5000);
        String[] textToArray = yesCardsAmount.innerText().split(" ");

        return textToArray[0];
    }

    @Step("Collect 'Kinda' cards amount on the header.")
    public String getKindaCardsAmount() {
        waitForLocator(kindaCardsAmount, 5000);
        String[] textToArray = kindaCardsAmount.innerText().split(" ");

        return textToArray[0];
    }

    @Step("Collect 'No' cards amount on the header.")
    public String getNoCardsAmount() {
        waitForLocator(noCardsAmount, 5000);
        String[] textToArray = noCardsAmount.innerText().split(" ");

        return textToArray[0];
    }

    @Step("Click 'End' button.")
    public TestTutorPage clickEndButton() {
        endButton.click();

        return new TestTutorPage(getPage()).init();
    }

    public String getPackName() {
        waitForLocator(packName, 5000);
        String flashcardHeader = packName.innerText();
        int flashcardHeaderLength = flashcardHeader.length();
        if (flashcardHeader.contains("...")) {
            flashcardHeader = flashcardHeader.substring(0, flashcardHeaderLength - 3);
        }

        return flashcardHeader;
    }

    public String getMnemonicCardsStackName() {
        waitForLocator(mnemonicCardsStackHeader, 5000);
        String mnemonicHeader = mnemonicCardsStackHeader.innerText();
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

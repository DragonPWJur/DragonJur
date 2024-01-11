package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

public class FlashcardsPage extends BaseLocator{
    public Locator markedForRechecking = getPage().getByRole(AriaRole.BUTTON)
            .filter(new Locator.FilterOptions().setHas(getPage().getByText("Marked for re-checking")));

    public Locator numberMarkedForRechecking = getPage().getByRole(
            AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Marked for re-checking"))
            .locator("//div[2]");
    public Locator flashcardsMenuButton = button("Flashcards");

    public FlashcardsPage(Page page, Playwright playwright) {
        super(page, playwright);
    }


    public FlashcardsPage clickFlashcardsMenuButton() {
        flashcardsMenuButton.click();

        return this;
    }

    public String getNumberOfCardsForReChecking() {
        clickFlashcardsMenuButton();

        return numberMarkedForRechecking.innerText();
    }
}

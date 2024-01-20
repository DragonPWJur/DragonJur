package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PerformancePage extends SideMenuPage {

    protected PerformancePage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    private final Locator overalButton = locator( "//div[@class=\"sc-eFWqGp edRObg\"]");

    private final Locator listOveralDropDownMenu = waitForListOfElementsLoaded("//div[@class=\"sc-bUbCnL fbqvHX\"]");

    private final Locator overalButtonfromDropDown = locator("Overall");

    private final Locator testsButtonromDropDown = locator("Tests");

    private final Locator allFlashCardsButtonFromDropDown = locator("All flashcards");


    public PerformancePage clickOveralMenu() {
        overalButton.click();

        return new PerformancePage(getPage(), getPlaywright());
    }

    public PerformancePage clickOveralFromDropDownMenu() {
        overalButtonfromDropDown.click();

        return new PerformancePage(getPage(), getPlaywright());
    }

    public PerformancePage clickTestsFromDropDownMenu() {
        testsButtonromDropDown.click();

        return new PerformancePage(getPage(), getPlaywright());
    }

    public PerformancePage allFlashCardsFromDropDownMenu() {
        allFlashCardsButtonFromDropDown.click();

        return new PerformancePage(getPage(), getPlaywright());
    }

}

package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

public class PerformancePage extends BaseLocator {
    private final Locator overallDropdown = button("Overall");
    private final Locator testsButton = getPage().getByRole(AriaRole.BANNER).getByRole(AriaRole.BUTTON, new Locator
            .GetByRoleOptions().setName("Tests"));
    private final Locator allFlashcardsButton = button("All flashcards");
//    public Locator allTestsFilter = getPage().locator("span").filter(new Locator.FilterOptions()
//            .setHasText("All tests"));
    private final Locator allTestsFilter = button("All tests");
    public PerformancePage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public PerformancePage clickOverallDropdown() {
        overallDropdown.click();
        return this;
    }
}

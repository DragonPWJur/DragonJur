package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class HomePage extends BaseLocator {
    private final Locator studyThisButton = button("Study This");
    private final Locator testsButton = exactButton("Tests");

    public HomePage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public TestListPage clickTestsMenu() {
        testsButton.click();
        return new TestListPage(getPage(), getPlaywright());
    }

    public Locator getStudyThisButton() {
        return studyThisButton;
    }
}
package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class HomePage extends BaseLocator {
    private final Locator studyThisButton = button("Study This");
    private final Locator testsButton = exactButton("Tests");
    private final Locator weakestExamAreasHeader = dialog().locator("span");
    private final Locator weakestExamAreasModal = dialog();
    private final Locator weakestExamAreasMessage = dialog().getByText("You have not studied enough in order for us to calculate your weakest areas. Keep Studying \uD83D\uDE03");

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

    public Locator getWeakestExamAreasModal() {
        return weakestExamAreasModal;
    }

    public HomePage clickStudyThisButton() {
        studyThisButton.click();
        return this;
    }

    public  Locator getWeakestExamAreasHeader() {
        return weakestExamAreasHeader;
    }

    public  Locator getWeakestExamAreasMessage() {
        return weakestExamAreasMessage;
    }
}
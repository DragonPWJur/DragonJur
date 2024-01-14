package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class HomePage extends SideMenuPage {
    private final Locator studyThisButton = button("Study This");
    private final Locator progressbarPoints = locator("div>svg.CircularProgressbar+div>span").first();

    public HomePage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public Locator getStudyThisButton() {

        return studyThisButton;
    }

    public Locator getProgressbarPoints() {

        return progressbarPoints;
    }
}
package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

class ProfilePage extends SideMenuPage {

    private final Locator resetCourseResultsButton = exactButton("Reset current course results");
    private final Locator yesButton = exactButton("Yes");

    public ProfilePage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public ProfilePage clickResetCourseResultsButton() {
        resetCourseResultsButton.click();

        return this;
    }

    public ProfilePage clickYesButton() {
        yesButton.click();

        return this;
    }
}
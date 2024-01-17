package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class AddNewCoursePage extends BaseLocator{

    private final Locator getButton = locator("div:nth-child(3) > .sc-jKDlA-D > .sc-dkzDqf");

    protected AddNewCoursePage(Page page, Playwright playwright) {
        super(page, playwright);
    }
    public AddNewCourseModalPage clickGetButton() {
        getButton.click();

        return new AddNewCourseModalPage(getPage(), getPlaywright());
    }
}

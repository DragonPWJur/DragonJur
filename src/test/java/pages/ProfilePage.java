package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class ProfilePage extends BaseSideMenu {

    private final Locator addANewCourseButton = button("Add a new course");

    public ProfilePage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public AddNewCoursePage clickAddANewCourseButton() {
        addANewCourseButton.click();

        return new AddNewCoursePage(getPage(), getPlaywright());
    }
}

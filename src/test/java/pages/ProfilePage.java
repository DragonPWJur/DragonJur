package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ProfilePage extends BaseSideMenu {

    private final Locator addANewCourseButton = button("Add a new course");

    public ProfilePage(Page page) {
        super(page);
    }

    public AddNewCoursePage clickAddANewCourseButton() {
        addANewCourseButton.click();

        return new AddNewCoursePage(getPage());
    }
}

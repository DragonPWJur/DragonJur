package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.constants.Constants;

public final class ProfilePage extends BaseSideMenu<ProfilePage> {
    private final Locator addANewCourseButton = button("Add a new course");

    public ProfilePage(Page page) {
        super(page);
    }

    @Override
    public ProfilePage createPage() {
        return init(new ProfilePage(getPage()), Constants.PROFILE_END_POINT);
    }

    public AddNewCoursePage clickAddANewCourseButton() {
        addANewCourseButton.click();

        return new AddNewCoursePage(getPage()).createPage();
    }
}

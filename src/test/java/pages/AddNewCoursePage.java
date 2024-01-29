package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.constants.Constants;

public final class AddNewCoursePage extends BaseHeader<AddNewCoursePage> {
    private final Locator getButton = locator("div:nth-child(3) > .sc-jKDlA-D > .sc-dkzDqf");

    AddNewCoursePage(Page page) {

        super(page);
    }

    @Override
    public AddNewCoursePage init() {

        return createPage(new AddNewCoursePage(getPage()), Constants.ADD_NEW_COURSE_END_POINT);
    }

    public AddNewCourseModal clickGetButton() {
        getButton.click();

        return new AddNewCourseModal(getPage()).init();
    }
}

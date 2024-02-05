package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import pages.constants.Constants;

public final class AddNewCoursePage extends BaseHeader<AddNewCoursePage> {
    private final Locator getButton = locator("main > div > div:nth-child(9) > div > button");

    AddNewCoursePage(Page page) {

        super(page);
    }

    @Override
    public AddNewCoursePage init() {

        return createPage(new AddNewCoursePage(getPage()), Constants.ADD_NEW_COURSE_END_POINT);
    }

    @Step("Click 'Get' button.")
    public AddNewCourseModal clickGetButton() {
        getButton.click();

        return new AddNewCourseModal(getPage()).init();
    }
}

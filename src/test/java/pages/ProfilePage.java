package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.constants.Constants;

public final class ProfilePage extends BaseSideMenu<ProfilePage> {
    private final Locator addANewCourseButton = button("Add a new course");
    private final Locator account = exactHeading("Account");
    private final Locator paymentMethod = exactHeading("Payment method");

    ProfilePage(Page page) {
        super(page);
    }

    @Override
    public ProfilePage init() {

        return createPage(new ProfilePage(getPage()), Constants.PROFILE_END_POINT);
    }

    public Locator getAccount() {

        return account;
    }

    public Locator getPaymentMethod() {

        return paymentMethod;
    }

    public Locator getAddANewCourseButton() {

        return addANewCourseButton;
    }

    public AddNewCoursePage clickAddANewCourseButton() {
        addANewCourseButton.click();

        return new AddNewCoursePage(getPage()).init();
    }
}

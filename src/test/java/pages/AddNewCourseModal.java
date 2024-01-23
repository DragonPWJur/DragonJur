package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public final class AddNewCourseModal extends BaseModal<AddNewCourseModal> {
    private final Locator lifeTimeButton = button("Life time");
    private final Locator chooseAProductHeading = heading("Choose a product");

    public AddNewCourseModal(Page page) {
        super(page);
    }

    @Override
    public AddNewCourseModal createPage() {

        return new AddNewCourseModal(getPage());
    }

    public AddNewCourseModal clickLifeTimeButton() {
        lifeTimeButton.click();

        return createPage();
    }

    public Locator getChooseAProductHeading() {

        return chooseAProductHeading;
    }
}

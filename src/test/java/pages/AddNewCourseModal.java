package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public final class AddNewCourseModal extends BaseTopMenu {
    private final Locator lifeTimeButton = button("Life time");
    private final Locator chooseAProductHeading = heading("Choose a product");

    public AddNewCourseModal(Page page) {
        super(page);
    }

    public AddNewCourseModal clickLifeTimeButton() {
        lifeTimeButton.click();

        return new AddNewCourseModal(getPage());
    }

    public Locator getChooseAProductHeading() {

        return chooseAProductHeading;
    }
}

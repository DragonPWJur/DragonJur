package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class AddNewCourseModalPage extends BaseLocator{

    private final Locator lifeTimeButton = button("Life time ");
    private final Locator headingChooseAProduct = heading("Choose a product");

    protected AddNewCourseModalPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public AddNewCourseModalPage clickLifeTimeButton() {
        lifeTimeButton.click();

        return new AddNewCourseModalPage(getPage(), getPlaywright());
    }
    public AddNewCourseModalPage visibleHeadingChooseAProduct() {
        headingChooseAProduct.isVisible();

        return new AddNewCourseModalPage(getPage(), getPlaywright());

    }

}

package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public final class AddNewCoursePage extends BaseTopMenu {
    private final Locator getButton = locator("div:nth-child(3) > .sc-jKDlA-D > .sc-dkzDqf");

    AddNewCoursePage(Page page) {
        super(page);
    }

    public AddNewCoursePage clickGetButton() {
        getButton.click();

        return new AddNewCoursePage(getPage());
    }
}

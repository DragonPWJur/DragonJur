package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class ProfilePage extends BaseLocator {

    public ProfilePage(Page page, Playwright playwright) {
        super(page, playwright);
    }
}
package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class HomePage extends BaseLocator {
    public Locator studyThisButton = button("Study This");

    public HomePage(Page page, Playwright playwright) {
        super(page, playwright);
    }
}
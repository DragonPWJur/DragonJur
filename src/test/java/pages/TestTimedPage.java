package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;

public final class TestTimedPage extends BaseFooter {

    private final List<Locator> radioButtons = allRadioButtons();

    private final Locator timer = locator("header div div:has(button)>div");

    public TestTimedPage(Page page) {
        super(page);
    }

    public Locator getTimer() {
        return timer;
    }
}
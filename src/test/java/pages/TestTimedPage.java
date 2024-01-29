package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.constants.Constants;

import java.util.List;

public final class TestTimedPage extends BaseTestsPage<TestTimedPage> {
    private final Locator timer = locator("header div div:has(button)>div");
    private final List<Locator> radioButtons = allRadioButtons();

    TestTimedPage(Page page) {
        super(page);
    }

    @Override
    public TestTimedPage init() {

        return createPage(new TestTimedPage(getPage()), Constants.TEST_TIMED_END_POINT);
    }

    public Locator getTimer() {

        return timer;
    }

    public List<Locator> getRadioButtons() {

        return radioButtons;
    }
}
package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import utils.TestData;

import java.util.ArrayList;
import java.util.List;

public class TestTimedPage extends SideMenuPage {
    private final Locator radioButtonLocator = radio();
    private final Locator timerOnTimedTest = locator("header div div:has(button)>div");
    private final Locator textQuestionMark = text(TestData.QUESTION_MARK);

    public TestTimedPage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public int getCountsTests() {
        List<Locator> radioButtons = new ArrayList<>();
        for (int i = 0; i < radioButtonLocator.count(); i++) {
            radioButtons.add(radioButtonLocator);
        }

        return radioButtons.size();
    }

    public Locator getTimer() {
        return timerOnTimedTest;
    }

    public Locator getQuestionMark() {
        return textQuestionMark;
    }
}
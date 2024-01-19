package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;


public class TestTimedPage extends BaseSideMenu {
    private final List<Locator> radioButtons = allRadioButtons();
    private final Locator timer = locator("header div div:has(button)>div");
    private final Locator questionMarkText = text("?");

    public TestTimedPage(Page page) {
        super(page);
    }

    public int getAnswersCount() {
       return radioButtons.size();
    }

    public Locator getTimer() {
        return timer;
    }

    public Locator getQuestionMark() {
        return questionMarkText;
    }
}
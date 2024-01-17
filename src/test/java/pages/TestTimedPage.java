package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.util.List;


public class TestTimedPage extends SideMenuPage {
    private final List<Locator> radioButtons = radioButtonsAll();
    private final Locator timer = locator("header div div:has(button)>div");
    private final Locator questionMarkText = text("?");

    public TestTimedPage(Page page, Playwright playwright) {
        super(page, playwright);
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
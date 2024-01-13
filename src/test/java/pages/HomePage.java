package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class HomePage extends BaseLocator {
    private final Locator studyThisButton = button("Study This");
    private final Locator testsButton = exactButton("Tests");
    private final Locator homeButton = exactButton("Home");
    private final Locator week1Header = exactText("Week 1");
    private final Locator week1FirstCheckbox = locator("//span[text()='Week 1']//following-sibling::label").first();
    private final Locator progressbarPoints = locator("div>svg.CircularProgressbar+div>span").first();
    private final Locator progressbarSideMenuPoints = locator("div:has(.CircularProgressbar)+span").first();
    private final Locator progressbarAnimationPoints = locator("div>svg.CircularProgressbar>path.CircularProgressbar-path").last();

    public HomePage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public TestListPage clickTestsMenu() {
        testsButton.click();
        return new TestListPage(getPage(), getPlaywright());
    }

    public Locator getStudyThisButton() {
        return studyThisButton;
    }

    public Locator getWeek1FirstCheckbox() {

        return week1FirstCheckbox;
    }

    public Locator getProgressbarPoints() {

        return progressbarPoints;
    }

    public Locator getProgressbarSideMenuPoints() {

        return progressbarSideMenuPoints;
    }

    public HomePage clickHomeMenu() {
        homeButton.click();

        return this;
    }

    public HomePage focusWeek1Header() {
        week1Header.focus();

        return this;
    }

    public HomePage clickWeek1FirstCheckbox() {
        week1FirstCheckbox.click();

        return this;
    }

    public HomePage verifyWeek1FirstCheckboxUnchecked() {
        if(getWeek1FirstCheckbox().isChecked()){
            week1FirstCheckbox.click();
        }
        progressbarAnimationPoints.waitFor();
        assertThat(progressbarAnimationPoints).hasCSS("stroke-dashoffset", "282.743px");

        return this;
    }
}

package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import pages.constants.Constants;
import tests.helpers.TestUtils;

import java.util.List;

public final class HomePage extends BaseSideMenu<HomePage> implements IRandom {
    private final Locator studyThisButton = button("Study This");
    private final Locator twoWeeksButton = exactButton("2 Weeks");
    private final Locator week1Header = exactText("Week 1");
    private final Locator week1FirstCheckbox = exactText("Week 1").locator("~label").first();
    private final Locator mainSectionPoints = locator("div>svg.CircularProgressbar+div>span").first();
    private final Locator sideMenuPoints = locator("div:has(.CircularProgressbar)+span").first();
    private final Locator streaksButton = locator("button>svg+p").last();
    private final List<Locator> allCheckboxes = allCheckboxes("label");
    private final Locator streakDaysModalWindowText = locator("div[role='dialog']>div>p");

    private final int randomIndex = getRandomInt(0, allCheckboxes.size());

    public HomePage(Page page) {
        super(page);
    }

    @Override
    public HomePage init() {

        return createPage(new HomePage(getPage()), Constants.HOME_END_POINT);
    }

    public Locator getMainSectionPointsLocator() {

        return mainSectionPoints;
    }

    public String getMainSectionPointsText() {
        waitForPointsAnimationToStop();

        return mainSectionPoints.innerText();
    }

    @Step("Get Main Section Points")
    public int getMainSectionPoints() {

        return TestUtils.getInt(getMainSectionPointsText());
    }

    public String getSideMenuPointsText() {
        waitForPointsAnimationToStop();

        return sideMenuPoints.innerText();
    }

    @Step("Get Side Menu Points.")
    public int getSideMenuPoints() {

        return TestUtils.getInt(getSideMenuPointsText());
    }

    @Step("Find a block 'Week 1'.")
    public HomePage focusWeek1Header() {
        week1Header.focus();

        return this;
    }

    public Locator getWeek1FirstCheckbox() {

        return week1FirstCheckbox;
    }

    public List<Locator> getAllCheckboxes() {

        return allCheckboxes;
    }

    @Step("Click on first checkbox in 'Week 1' section.")
    public HomePage clickWeek1FirstCheckbox() {
        week1FirstCheckbox.click();

        return this;
    }

    public HomePage click2WeeksButton() {
        twoWeeksButton.click();

        return this;
    }

    public void waitForPointsAnimationToStop() {

        waitWithTimeout(2000);
    }

    @Step("Click 'Streaks' button")
    public HomePage clickStreaksButton() {
        streaksButton.click();

        return this;
    }

    public Locator getStreaksModalWindow() {

        return getDialog();
    }

    public int getRandomIndex() {

        return randomIndex;
    }

    @Step("Collect checkbox image.")
    public Locator getNthCheckboxImage(int index) {

        return allCheckboxes.get(index).locator("svg");
    }

    public boolean areAllCheckboxesNotActive() {

        return allCheckboxes.stream().noneMatch(Locator::isChecked);
    }

    boolean areAllCheckboxesActive() {

        return allCheckboxes.stream().allMatch(Locator::isChecked);
    }

    @Step("Click random checkbox.")
    public void clickRandomCheckbox() {
        getNthCheckbox(randomIndex).click();
    }

    public Locator getNthCheckbox(int number) {

        return allCheckboxes.get(number);
    }

    @Step("Select a random checkbox.")
    public Locator getRandomCheckbox() {

        return allCheckboxes.get(randomIndex);
    }

    @Step("Click random ({randomNumber}-nth) checkbox.")
    public HomePage clickNthCheckbox(int randomNumber) {
        getNthCheckbox(randomNumber).click();

        return this;
    }

    @Step("Click 'Study This' button.")
    public StudyThisModal clickStudyThisButton() {
        studyThisButton.click();
        waitWithTimeout(1000);

        return new StudyThisModal(getPage()).init();
    }

    @Step("Get the random checkbox image.")
    public Locator getCheckboxImage(Locator checkbox) {

        return checkbox.locator("svg");
    }

    public Locator getStreakDaysModalWindowTextLocator() {

        return streakDaysModalWindowText;
    }

    public Locator getStreaksButton() {

        return streaksButton;
    }

    public void waitForAPIPrecondition(int timeout) {
        waitWithTimeout(timeout);
        getPage().reload();
        waitWithTimeout(2000);
    }
}
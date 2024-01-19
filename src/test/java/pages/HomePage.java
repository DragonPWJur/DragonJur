package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import utils.TestUtils;

import java.util.List;

import io.qameta.allure.Step;

import static java.lang.Integer.parseInt;

public class HomePage extends SideMenuPage {
    private final Locator studyThisButton = button("Study This");
    private final Locator testsButton = exactButton("Tests");
    private final Locator homeButton = exactButton("Home");
    private final Locator week1Header = exactText("Week 1");
    private final Locator twoWeeksButton = exactButton("2 Weeks");
    private final Locator week1FirstCheckbox = exactText("Week 1").locator("~label").first();
    private final Locator progressbarPoints = locator("div>svg.CircularProgressbar+div>span").first();
    private final Locator progressbarSideMenuPoints = locator("div:has(.CircularProgressbar)+span").first();
    private final Locator streaksButton = locator("button>svg+p").last();
    private final Locator streaksModalWindow = locator("div[role='dialog']");
    private final List<Locator> listCheckboxes = checkBoxesAll("label:has(input)");
    private final Locator checkboxImage = locator("label:has(input) svg");

    private final int checkBoxNumber = TestUtils.getRandomInt(0, listCheckboxes.size());

    public HomePage(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public List<Locator> getListCheckboxes() {
        return listCheckboxes;
    }

    public Locator getStudyThisButton() {

        return studyThisButton;
    }

    public Locator getProgressbarPoints() {

        return progressbarPoints;
    }

    public Locator getWeek1FirstCheckbox() {

        return week1FirstCheckbox;
    }

    public Locator getStreaksModalWindow() {

        return streaksModalWindow;
    }

    @Step("Click 'Tests' button on side menu")
    public TestListPage clickTestsMenu() {
        testsButton.click();
        return new TestListPage(getPage(), getPlaywright());
    }

    public HomePage clickHomeMenu() {
        homeButton.click();

        return this;
    }

    public HomePage focusWeek1Header() {
        week1Header.focus();

        return this;
    }

    public HomePage clickTwoWeeksButton() {
        twoWeeksButton.click();

        return this;
    }

    public HomePage clickWeek1FirstCheckbox() {
        week1FirstCheckbox.click();

        return this;
    }

    public String getProgressbarPointsText() {

        return progressbarPoints.innerText();
    }

    public String getProgressbarSideMenuPointsText() {

        return progressbarSideMenuPoints.innerText();
    }

    public int getProgressbarPointsNumber() {
        String pointsText = getProgressbarPointsText();

        return parseInt(pointsText);
    }

    public int getProgressbarSideMenuPointsNumber() {
        String pointsText = getProgressbarSideMenuPointsText();

        return parseInt(pointsText);
    }

    public HomePage clickStreaksButton() {
        streaksButton.click();

        return this;
    }

    public Locator getNthCheckbox(int number) {

        return listCheckboxes.get(number);
    }

    public  HomePage clickRandomCheckBox(){
        getNthCheckbox(checkBoxNumber).click();

        return this;
    }

    public  HomePage clickCheckBox(int index){
        getNthCheckbox(index).click();
        getPage().waitForTimeout(1000);

        return this;
    }

    public int getCheckBoxNumber() {

        return checkBoxNumber;
    }

    public HomePage checkAllCheckBoxes() {

        for (int i = 0; i < listCheckboxes.size(); i++) {
            while (!listCheckboxes.get(i).isChecked()) {
                listCheckboxes.get(i).check();
                getPage().waitForTimeout(1000);
            }
            System.out.println(listCheckboxes.get(i).isChecked());
        }
        return this;
    }

    protected boolean isListCheckBoxesNotEmpty() {

        return !listCheckboxes.isEmpty();
    }

    public boolean areAllCheckBoxesUnchecked() {

       return listCheckboxes.stream().noneMatch(Locator::isChecked);
    }

    protected boolean areAllCheckBoxesChecked() {

        return listCheckboxes.stream().allMatch(Locator::isChecked);
    }

    public boolean isCheckBoxChecked() {

        return listCheckboxes.get(checkBoxNumber).isChecked();
    }

    public Locator getCheckboxImage() {

        return checkboxImage;
    }

    protected List<Locator> getListCheckedCheckBoxes() {

        return listCheckboxes.stream().filter(Locator::isChecked).toList();
    }

    public HomePage clickCheckedBox() {

        for (Locator checkBox : listCheckboxes) {
            if (checkBox.isChecked()) {
                checkBox.click();
                break;
            }
        }
        return this;
    }
}
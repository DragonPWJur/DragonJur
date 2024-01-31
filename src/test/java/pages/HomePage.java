package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import pages.constants.Constants;
import tests.helpers.TestUtils;

import java.util.List;

public final class HomePage extends BaseSideMenu<HomePage> implements IRandom{
    private final Locator studyThisButton = button("Study This");
    private final Locator twoWeeksButton = exactButton("2 Weeks");
    private final Locator week1Header = exactText("Week 1");
    private final Locator week1FirstCheckbox = exactText("Week 1").locator("~label").first();
    private final Locator mainSectionPoints = locator("div>svg.CircularProgressbar+div>span").first();
    private final Locator sideMenuPoints = locator("div:has(.CircularProgressbar)+span").first();
    private final Locator streaksButton = locator("button>svg+p").last();
    private final Locator checkboxImage = locator("label:has(input) svg");
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

    @Step("Get Main Section Points text")
    public String getMainSectionPointsText() {
        waitForPointsAnimationToStop();

        return mainSectionPoints.innerText();
    }

    @Step("Get Main Section Points number")
    public int getMainSectionPoints() {

        return TestUtils.getInt(getMainSectionPointsText());
    }

    @Step("Get Side Menu Points text")
    public String getSideMenuPointsText() {
        waitForPointsAnimationToStop();

        return sideMenuPoints.innerText();
    }

    @Step("Get Main Section Points number")
    public int getSideMenuPoints() {

        return TestUtils.getInt(getSideMenuPointsText());
    }

    public HomePage focusWeek1Header() {
        week1Header.focus();

        return this;
    }

    public Locator getWeek1FirstCheckbox() {

        return week1FirstCheckbox;
    }

    public Locator getStudyThisButton() {

        return studyThisButton;
    }

    public List<Locator> getAllCheckboxesInA2WeeksPlan() {

        return allCheckboxes;
    }

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

    @Step("Click on the 'Streaks' button")
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

    public int getSingleCheckedCheckboxIndex() {
        final int index = randomIndex;

        return index;
    }

    private List<Locator> getAllCheckboxes() {

        return allCheckboxes;
    }

    public Locator getCheckboxImage() {

        return checkboxImage;
    }

    boolean isListCheckboxesNotEmpty() {

        return !allCheckboxes.isEmpty();
    }

    public boolean areAllCheckboxesUnchecked() {

        return allCheckboxes.stream().noneMatch(Locator::isChecked);
    }

    boolean areAllCheckboxesChecked() {

        return allCheckboxes.stream().allMatch(Locator::isChecked);
    }

    public HomePage clickRandomCheckbox(){
        getNthCheckbox(randomIndex).click();

        return this;
    }

    public Locator getNthCheckbox(int number) {

        return allCheckboxes.get(number);
    }

    public Locator getRandomCheckbox() {

        return allCheckboxes.get(randomIndex);
    }
    @Step("Click on {randomNumber}-nth checkbox")
    public HomePage clickNthCheckbox(int randomNumber) {
        getNthCheckbox(randomNumber).click();

        return this;
    }

    List<Locator> getAllCheckedCheckboxes() {

        return allCheckboxes.stream().filter(Locator::isChecked).toList();
    }

    boolean isCheckboxChecked() {

        return allCheckboxes.get(randomIndex).isChecked();
    }

    public HomePage clickCheckedCheckbox() {
        for (Locator checkbox : allCheckboxes) {
            if (checkbox.isChecked()) {
                checkbox.click();
                break;
            }
        }
        return this;
    }

    public HomePage clickStudyThisButton() {
        studyThisButton.click();

        return this;
    }

    public Locator getCheckboxImage(Locator randomCheckBox) {

        return randomCheckBox.locator("svg");
    }

    @Step("The modal window contains the text: 'You are on a 1 day study streak!'")
    public Locator getStreakDaysModalWindowTextLocator() {

        return streakDaysModalWindowText;
    }

    public Locator getStreaksButton() {

        return streaksButton;
    }

    public int getRandomCheckboxIndex(List<Locator> list) {

        return getRandomNumber(list);
    }


//
////    public boolean isCheckBoxChecked() {
////
////        return listCheckboxes.get(checkBoxNumber).isChecked();
////    }
//
//    protected boolean areAllCheckBoxesChecked() {
//
//        return listCheckboxes.stream().allMatch(Locator::isChecked);
//    }
//
//    public boolean isCheckBoxChecked() {
//
//        return listCheckboxes.get(checkBoxNumber).isChecked();
//    }
//
//public HomePage checkAllCheckBoxes() {
//
//        for (int i = 0; i < listCheckboxes.size(); i++) {
//            while (!listCheckboxes.get(i).isChecked()) {
//                listCheckboxes.get(i).check();
//                getPage().waitForTimeout(1000);
//            }
//            System.out.println(listCheckboxes.get(i).isChecked());
//        }
//        return this;
//    }
//
//    protected List<Locator> getListCheckedCheckBoxes() {
//
//        return allCheckboxes.stream().filter(Locator::isChecked).toList();
//    }
//
//    public HomePage clickCheckedBox() {
//
//        for (Locator checkBox : allCheckboxes) {
//            if (checkBox.isChecked()) {
//                checkBox.click();
//                break;
//            }
//        }
//        return this;
//    }
//



}
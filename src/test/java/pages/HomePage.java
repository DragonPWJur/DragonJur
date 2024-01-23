package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.constants.Constants;

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

    private final int checkboxRandomNumber = getRandomInt(0, allCheckboxes.size());


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

        return mainSectionPoints.innerText();
    }

    public int getMainSectionPoints() {
        ;
        return Integer.parseInt(getMainSectionPointsText());
    }

    public String getSideMenuPointsText() {

        return sideMenuPoints.innerText();
    }

    public int getSideMenuPoints() {

        return Integer.parseInt(getSideMenuPointsText());
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

    public HomePage clickStreaksButton() {
        streaksButton.click();

        return this;
    }

    public Locator getStreaksModalWindow() {

        return getDialog();
    }

    boolean areAllCheckboxesUnchecked() {

        return allCheckboxes.stream().noneMatch(Locator::isChecked);
    }

    public  HomePage clickRandomCheckbox(){
        getNthCheckbox(checkboxRandomNumber).click();

        return this;
    }

    public int getCheckboxRandomNumber() {

        return checkboxRandomNumber;
    }

    public Locator getNthCheckbox(int number) {

        return allCheckboxes.get(number);
    }

    public void clickNthCheckbox(int randomNumber) {

        getNthCheckbox(randomNumber).click();
    }

    public Locator getCheckboxImage() {

        return checkboxImage;
    }
//
//    public HomePage checkAllCheckBoxes() {
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
////    protected boolean isListCheckBoxesNotEmpty() {
////
////        return allCheckboxes;
////    }
//

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
//    public Locator getWeakestExamAreasModal() {
//        return weakestExamAreasModal;
//    }
//
//    public HomePage clickStudyThisButton() {
//        studyThisButton.click();
//        return this;
//    }
//
//    public  Locator getWeakestExamAreasHeader() {
//        return weakestExamAreasHeader;
//    }
//
//    public  Locator getWeakestExamAreasMessage() {
//        return weakestExamAreasMessage;
//    }
}
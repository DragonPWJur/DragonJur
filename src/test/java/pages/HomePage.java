package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;

public final class HomePage extends BaseSideMenu {

    private final Locator studyThisButton = button("Study This");
    private final Locator twoWeeksButton = exactButton("2 Weeks");
    private final Locator week1Header = exactText("Week 1");
    private final Locator week1FirstCheckbox = exactText("Week 1").locator("~label").first();
    private final Locator progressbarPoints = locator("div>svg.CircularProgressbar+div>span").first();
    private final Locator progressbarSideMenuPoints = locator("div:has(.CircularProgressbar)+span").first();
    private final Locator streaksButton = locator("button>svg+p").last();
    private final Locator checkboxImage = locator("label:has(input) svg");
    private final List<Locator> allCheckboxes = allCheckboxes();

//    private final int checkBoxNumber = TestUtils.getRandomInt(0, listCheckboxes.size());

    public HomePage(Page page) {
        super(page);
    }

    public List<Locator> getListCheckboxes() {
        return allCheckboxes;
    }

    public Locator getStudyThisButton() {

        return studyThisButton;
    }

    public Locator getMainSectionPointsLocator() {

        return progressbarPoints;
    }

    public Locator getWeek1FirstCheckbox() {

        return week1FirstCheckbox;
    }

    public HomePage focusWeek1Header() {
        week1Header.focus();

        return this;
    }

    public HomePage click2WeeksButton() {
        twoWeeksButton.click();

        return this;
    }

    public HomePage clickWeek1FirstCheckbox() {
        week1FirstCheckbox.click();

        return this;
    }

    public String getMainSectionPointsText() {

        return progressbarPoints.innerText();
    }

    public String getSideMenuPointsText() {

        return progressbarSideMenuPoints.innerText();
    }

    public int getMainSectionPoints() {
        ;
        return Integer.parseInt(getMainSectionPointsText());
    }

    public int getSideMenuPoints() {

        return Integer.parseInt(getSideMenuPointsText());
    }

    public HomePage clickStreaksButton() {
        streaksButton.click();

        return this;
    }

    public Locator getStreaksModalWindow() {
        return getDialog();
    }

    public Locator getNthCheckbox(int number) {

        return allCheckboxes.get(number);
    }

//    public  HomePage clickRandomCheckBox(){
//        getNthCheckbox(checkBoxNumber).click();
//
//        return this;
//    }

    List<Locator> getAllCheckboxes() {
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

        return allCheckboxes;
    }

    protected boolean areAllCheckBoxesUnchecked() {

       return allCheckboxes.stream().noneMatch(Locator::isChecked);
    }

//    public boolean isCheckBoxChecked() {
//
//        return listCheckboxes.get(checkBoxNumber).isChecked();
//    }
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

        return allCheckboxes.stream().filter(Locator::isChecked).toList();
    }

    public HomePage clickCheckedBox() {

        for (Locator checkBox : allCheckboxes) {
            if (checkBox.isChecked()) {
                checkBox.click();
                break;
            }
        }
        return this;
    }
}
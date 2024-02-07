package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import pages.constants.Constants;

import java.util.List;

public class StudyThisModal extends BaseModal<StudyThisModal> {
    private final Locator weakestExamAreasHeader = exactText("Weakest Exam Areas");
    private final Locator weakestExamAreasMessage = getDialog().getByText(Constants.YOU_HAVE_NOT_STUDIED_ENOUGH);
    private final String weakestCheckboxes = "div:has(p) + label:has(input)";

    StudyThisModal(Page page) {
        super(page);
    }

    @Override
    public StudyThisModal init() {
        return new StudyThisModal(getPage());
    }

    public Locator getWeakestExamAreasHeader() {

        return weakestExamAreasHeader;
    }

    public Locator getWeakestExamAreasMessage() {

        return weakestExamAreasMessage;
    }

    public List<Locator> getWeakestAreasCheckboxes() {

        return allCheckboxes(weakestCheckboxes);
    }

    @Step("Select two weakest areas.")
    public void selectWeakestAreas(int index1, int index2) {
        getWeakestAreasCheckboxes().get(index1).click();
        getWeakestAreasCheckboxes().get(index2).click();
    }
}

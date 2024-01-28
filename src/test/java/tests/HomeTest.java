package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.PreconditionPage;
import tests.helpers.TestData;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public final class HomeTest extends BaseTest {

    @Test(
            testName = "LMS-1365 https://app.qase.io/plan/LMS/1?case=1365",
            description = "TC1365-01 - Upon clicking the empty checkbox, the point count increases."
    )
    @Description("Objective: Verify that user points increase when you click on the empty checkbox.")
    @Story("Home Page")
    @TmsLink("fowvuhi3zcc7")
    public void testUponClickingCheckboxPointsCountIncreases() {
        HomePage homePage =
                new HomePage(getPage()).init()
                        .click2WeeksButton()
                        .focusWeek1Header();

        final int mainSectionPointsBefore = homePage.getMainSectionPoints();
        final int sideMenuPointsBefore = homePage.getSideMenuPoints();

        Assert.assertEquals(
                sideMenuPointsBefore, mainSectionPointsBefore,
                "If FAIL: Side Menu Points before test (" + sideMenuPointsBefore
                        + ") are NOT equal to Main Section Points before test (" + mainSectionPointsBefore + ").\n"
        );

        homePage
                .clickWeek1FirstCheckbox()
                .waitForPointsAnimationToStop();

        final int mainSectionPointsAfter = homePage.getMainSectionPoints();
        final int sideMenuPointsAfter = homePage.getSideMenuPoints();

        assertThat(homePage.getWeek1FirstCheckbox()).isChecked();
        Assert.assertTrue(
                mainSectionPointsBefore < mainSectionPointsAfter,
                "If FAIL: Points in Main Section before test (" + mainSectionPointsBefore
                        + ") did NOT increase compare to Points in Main Section after test  (" + mainSectionPointsAfter + ").\n"
        );
        Assert.assertTrue(
                sideMenuPointsBefore < sideMenuPointsAfter,
                "If FAIL: Side Menu Points before test (" + sideMenuPointsBefore
                        + ") did NOT increase compare to Side Menu Points after test (" + sideMenuPointsAfter + ").\n"
        );
        assertThat(homePage.getMainSectionPointsLocator()).hasText(TestData.CHECKBOX_POINTS);
        Assert.assertEquals(
                mainSectionPointsAfter, sideMenuPointsAfter,
                "If FAIL: Points on the main section after test (" + mainSectionPointsAfter
                        + ") are NOT the same as those shown on the side menu after test (" + sideMenuPointsAfter + ").\n"
        );
    }

    @Test(
            testName = "LMS-1343 https://app.qase.io/plan/LMS/1?case=1343",
            description = "TC1343-01 - Verification “Streaks” modal window appears."
    )
    @Description("Objective: To verify that the modal window will be opened after clicking on the “Streaks” button.")
    @Story("Home Page")
    @TmsLink("9rsd8ecq6x5y")
    public void testStreaksModalWindowIsAppeared() {
        HomePage homePage =
                new HomePage(getPage()).init()
                        .clickStreaksButton();

        assertThat(homePage.getStreaksModalWindow()).isVisible();
    }

    @Test(
            testName = "LMS-1341 Нажатие чекбоксов, https://app.qase.io/plan/LMS/1?case=1341",
            description = "TC1341-01 - Deactivation of a single Already Active Checkbox when all checkboxes are active."
    )
    @Description("To verify that a non-active checkbox can be successfully checked.")
    @Story("Home page")
    @TmsLink("oz4bwi8yjqj")
    public void testTheSingleNonActiveCheckboxCanBeChecked() {

        PreconditionPage precondition = new PreconditionPage(getPage()).init();
        HomePage homePage = new HomePage(getPage());

        final List<Locator> allCheckboxesInAWeek2Plan =
                precondition
                        .getAllCheckboxesInA2WeeksPlan();

        Assert.assertFalse(
                allCheckboxesInAWeek2Plan.isEmpty(),
                "If FAIL: Precondition is not reached. The List of Checkboxes IS EMPTY.\n"
        );
        Assert.assertTrue(
                precondition.areAllCheckboxesUnchecked(),
                "If FAIL: Precondition is not reached. NOT All Checkboxes are unchecked.\n"
        );

        homePage.init();

        final int randomIndex = homePage.getRandomIndex();

        assertThat(homePage.getNthCheckbox(randomIndex)).not().isChecked();

        homePage
                .clickNthCheckbox(randomIndex);

        assertThat(homePage.getNthCheckbox(randomIndex)).isChecked();

        final Locator checkboxImage = homePage.getCheckboxImage();

        assertThat(checkboxImage).hasCount(1);
        assertThat(checkboxImage).isVisible();
    }

    @Ignore
    @Test
    public void testDeactivationOfAlreadyActiveSingleCheckbox() {
        PreconditionPage preconditionPage = new PreconditionPage(getPage()).init();

        Assert.assertTrue(
                preconditionPage
                        .oneCheckboxShouldBeCheckedAndAllOthersUnchecked(),
                "If FAIL: Expected precondition 'A single checkbox is checked.' is not reached."
        );

        final int indexOfCheckedCheckbox = preconditionPage.getSingleCheckedCheckboxIndex();

        HomePage homePage = new HomePage(getPage()).init();

        final Locator singleRandomCheckbox = homePage.getNthCheckbox(indexOfCheckedCheckbox);
        final Locator checkboxImage = homePage.getCheckboxImage();

        assertThat(singleRandomCheckbox).isChecked();
        assertThat(checkboxImage).isVisible();

        homePage
                .clickNthCheckbox(indexOfCheckedCheckbox);

        assertThat(singleRandomCheckbox).not().isChecked();
        assertThat(homePage.getCheckboxImage()).not().isVisible();
        Assert.assertTrue(
                homePage.areAllCheckboxesUnchecked(),
                "If FAIL: All checkboxes are expected to be unchecked, but found checked checkbox(es)."
        );
    }

    @Test(
            testName = "LMS-1341 Нажатие чекбоксов, https://app.qase.io/plan/LMS/1?case=1341",
            description = "TC1341-03 - Deactivation of a single Already Active Checkbox when all checkboxes are active.")
    @Description("To verify the functionality when all checkboxes are checked, and a single active checkbox becomes inactive upon clicking again.")
    @Story("Home page")
    @TmsLink("nf0bbnl8cpe4")
    public void testDeactivationOfSingleCheckboxWhenAllCheckboxesAreActive(){

        Assert.assertTrue(new PreconditionPage(getPage()).init().areAllCheckboxesChecked(),
                "If FAIL: Precondition 'All checkboxes should be checked' is not reached.\n"
        );

        HomePage homePage = new HomePage(getPage()).init();

        final int randomIndexCheckBox = homePage.getRandomIndex();

        homePage
                .clickRandomCheckbox();

        final Locator randomCheckBox = homePage.getNthCheckbox(randomIndexCheckBox);
        final List<Locator> allCheckboxes = homePage.getAllCheckboxesInA2WeeksPlan();

        assertThat(randomCheckBox).not().isChecked();

        for (Locator checkbox: allCheckboxes) {
            if (checkbox != randomCheckBox) {

                assertThat(checkbox).isChecked();
            }
        }
    }

    @Test
    public void testModalWindowStudyIsOpened() {
        HomePage homePage =
                new HomePage(getPage()).init()
                        .clickStudyThisButton();

        assertThat(homePage.getDialog()).isVisible();
        assertThat(homePage.getWeakestExamAreasHeader()).hasText(TestData.WEAKEST_EXAM_AREAS);
        assertThat(homePage.getWeakestExamAreasMessage()).isVisible();
        assertThat(homePage.getWeakestExamAreasMessage()).hasText(TestData.YOU_HAVE_NOT_STUDIED_ENOUGH);
    }
}

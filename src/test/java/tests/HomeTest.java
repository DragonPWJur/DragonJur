package tests;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.PreconditionPage;
import tests.helpers.TestData;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public final class HomeTest extends BaseTest {


    @Test
    public void testcheckboxes() {
        HomePage homePage =
                new HomePage(getPage()).init();

        getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("12 month").setExact(true)).click();

        List<Locator> checkboxes = getPage().locator("input[type='checkbox']").all();
        System.out.println("checkboxes=" + checkboxes.size());

        Locator locator1 = getPage().locator("input[type='checkbox']");
        System.out.println("locator1=" + locator1.count());

        for (int i = 0; i < locator1.count(); i++) {
            System.out.println(locator1.nth(i).boundingBox().y+ "   " + checkboxes.get(i).boundingBox().y);
        }
    }

    @Test(
            testName = "TC1365-01 - Upon clicking the empty checkbox, the point count increases.",
            description = "LMS-1365 https://app.qase.io/plan/LMS/1?case=1365"
    )
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
            testName = "TC1343-01 - Verification “Streaks” modal window appears.",
            description = "LMS-1343 https://app.qase.io/plan/LMS/1?case=1343"
    )
    public void testStreaksModalWindowIsAppeared() {
        HomePage homePage =
                new HomePage(getPage()).init()
                        .clickStreaksButton();

        assertThat(homePage.getStreaksModalWindow()).isVisible();
    }

    @Test(
            testName = "TC1341-01 - The single non-active Checkbox can be checked." ,
            description = "LMS-1341 https://app.qase.io/plan/LMS/1?case=1341"
    )
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

        final int randomCheckboxNumber = homePage.getCheckboxRandomNumber();

        assertThat(homePage.getNthCheckbox(randomCheckboxNumber)).not().isChecked();

        homePage
                .clickNthCheckbox(randomCheckboxNumber);

        assertThat(homePage.getNthCheckbox(randomCheckboxNumber)).isChecked();

        final Locator checkboxImage = homePage.getCheckboxImage();

        assertThat(checkboxImage).hasCount(1);
        assertThat(checkboxImage).isVisible();
    }
//<<<<<<< HEAD
////
////    @Test
////    public void testDeactivationOfAlreadyActiveSingleCheckbox() {
////
////        Assert.assertTrue(new PreconditionPage(getPage(), getPlaywright())
////                .checkIfListCheckBoxesIsNotEmptyAndOneIsChecked(), "Precondition is not reached.");
////
////        HomePage homePage = new HomePage(getPage(), getPlaywright());
////
////        boolean allUnchecked = homePage
////                .clickCheckedBox()
////                .areAllCheckBoxesUnchecked();
////
////        Locator checkboxImage = homePage.getCheckboxImage();
////
////        Assert.assertTrue(allUnchecked, "All checkboxes are expected to be unchecked, but checked.");
////        Assert.assertFalse(checkboxImage.isVisible(), "All images of checkboxes are expected to be not visible, but visible");
////    }
//=======
//
//    @Ignore
//    @Test
//    public void testDeactivationOfAlreadyActiveSingleCheckbox() {
//
//        Assert.assertTrue(new PreconditionPage(getPage(), getPlaywright())
//                .checkIfListCheckBoxesIsNotEmptyAndOneIsChecked(), "Precondition is not reached.");
//
//        HomePage homePage = new HomePage(getPage(), getPlaywright());
//
//        boolean allUnchecked = homePage
//                .clickCheckedBox()
//                .areAllCheckBoxesUnchecked();
//
//        Locator checkboxImage = homePage.getCheckboxImage();
//
//        Assert.assertTrue(allUnchecked, "All checkboxes are expected to be unchecked, but checked.");
//        Assert.assertFalse(checkboxImage.isVisible(), "All images of checkboxes are expected to be not visible, but visible");
//    }
//
//    @Test
//    public void testDeactivationOfSingleCheckboxWhenAllCheckboxesAreActive() {
//
//        Assert.assertTrue(new PreconditionPage(getPage(), getPlaywright())
//                .checkIfListCheckBoxesIsNotEmptyAndAllCheckBoxesAreChecked(), "Precondition is not reached.");
//
//        HomePage homePage = new HomePage(getPage(), getPlaywright());
//
//        int randomIndexCheckBox = homePage.getCheckBoxNumber();
//
//        homePage.clickCheckBox(randomIndexCheckBox);
//
//        assertThat(homePage.getNthCheckbox(randomIndexCheckBox)).not().isChecked();
//
//        for (int i = 0; i < homePage.getListCheckboxes().size(); i++) {
//            if (i != randomIndexCheckBox) {
//                System.out.println(homePage.getListCheckboxes().get(i).isChecked());
//                assertThat(homePage.getListCheckboxes().get(i)).isChecked();
//            }
//        }
//    }
//
//    @Test
//    public void testModalWindowStudyIsOpened() {
//        HomePage homePage = new HomePage(getPage(), getPlaywright())
//                .clickStudyThisButton();
//
//        assertThat(homePage.getWeakestExamAreasModal()).isVisible();
//        assertThat(homePage.getWeakestExamAreasHeader()).hasText(TestData.STUDY_THIS_MODAL_HEADER);
//        assertThat(homePage.getWeakestExamAreasMessage()).isVisible();
//    }
//>>>>>>> 8952b8e84f53dbc1d24376ded00a911bdb48372e
}

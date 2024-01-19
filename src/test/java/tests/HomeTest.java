package tests;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.PreconditionPage;
import utils.ProjectProperties;
import utils.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public final class HomeTest extends BaseTest {

    @Test
    public void testLoginNavigation() {

        assertThat(getPage()).hasURL(ProjectProperties.BASE_URL + "/home");
    }

    @Test(dataProvider = "sideMenuItems", dataProviderClass = TestData.class)
    public void testNavigateToSubMenuItems(String menuName, String expectedUrl) {
        getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(menuName)).click();

        assertThat(getPage()).hasURL(expectedUrl);
    }

    @Test(testName = "TC1365-01 LMS-1365 https://app.qase.io/plan/LMS/1?case=1365")
    public void testUponClickingCheckboxPointsCountIncreases() {
        HomePage homePage = new HomePage(getPage())
                .clickHomeMenu()
                .click2WeeksButton()
                .focusWeek1Header();

        int mainSectionPointsBefore = homePage.getMainSectionPoints();
        int sideMenuPointsBefore = homePage.getSideMenuPoints();

        Assert.assertEquals(
                sideMenuPointsBefore, mainSectionPointsBefore,
                "Side Menu Points " + sideMenuPointsBefore + " are NOT equal to Main Section Points " + mainSectionPointsBefore
        );

        homePage
                .clickWeek1FirstCheckbox()
                .waitForPointsAnimationToStop();

        int mainSectionPointsAfter = homePage.getMainSectionPoints();
        int sideMenuPointsAfter = homePage.getSideMenuPoints();

        assertThat(homePage.getWeek1FirstCheckbox()).isChecked();
        Assert.assertTrue(
                mainSectionPointsBefore < mainSectionPointsAfter,
                "Points in Main Section " + mainSectionPointsBefore + "did NOT increase (" + mainSectionPointsAfter + ")."
        );
        Assert.assertTrue(
                sideMenuPointsBefore < sideMenuPointsAfter,
                "Points on Side Menu " + sideMenuPointsBefore + "did NOT increase (" + sideMenuPointsAfter + ")."
        );
        assertThat(homePage.getMainSectionPointsLocator()).hasText(TestData.CHECKBOX_POINTS);
        Assert.assertEquals(homePage.getMainSectionPointsText(), homePage.getSideMenuPointsText());
    }

    @Test(testName = "TC1343-01 LMS-1343 https://app.qase.io/plan/LMS/1?case=1343")
    public void testStreaksModalWindowIsAppeared() {
        HomePage homePage = new HomePage(getPage())
                .clickHomeMenu()
                .clickStreaksButton();

        assertThat(homePage.getStreaksModalWindow()).isVisible();
    }

    @Test(testName = "TC1341-01 LMS-1341 https://app.qase.io/plan/LMS/1?case=1341")
    public void testTheSingleNonActiveCheckboxCanBeChecked() {
        PreconditionPage precondition = new PreconditionPage(getPage());

        Assert.assertFalse(
                precondition.getAllCheckBoxes().isEmpty(),
                "Precondition is not reached. The List of Checkboxes is empty."
        );
        Assert.assertTrue(
                precondition.areAllCheckBoxesUnchecked(),
                "Precondition is not reached. NOT All Checkboxes are unchecked"
        );

        HomePage homePage = new HomePage(getPage());

        boolean isCheckBoxChecked = homePage
                .clickRandomCheckBox()
                .isCheckBoxChecked();

        Locator checkboxImage = homePage.getCheckboxImage();

        Assert.assertTrue(isCheckBoxChecked, "Randomly checked checkbox is expected to be checked, but unchecked.");
        assertThat(checkboxImage).hasCount(1);
        assertThat(checkboxImage).isVisible();
    }
//
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
}

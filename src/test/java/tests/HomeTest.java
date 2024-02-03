package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.*;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.PreconditionPage;
import tests.helpers.TestData;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public final class HomeTest extends BaseTest {

    @Severity(SeverityLevel.NORMAL)
    @Story("Checkboxes")
    @TmsLink("fowvuhi3zcc7")
    @Description("LMS-1365 Начисление points. https://app.qase.io/plan/LMS/1?case=1365"
            + "  Objective: Verify that user points increase when you click on the empty checkbox.")
    @Test(description = "TC1365-01 - Upon clicking the empty checkbox, the point count increases.")
    public void testUponClickingCheckboxPointsCountIncreases() {
        HomePage homePage =
                new HomePage(getPage()).init()
                        .clickHomeMenu()
                        .click2WeeksButton()
                        .focusWeek1Header();

        final int mainSectionPointsBefore = homePage.getMainSectionPoints();
        final int sideMenuPointsBefore = homePage.getSideMenuPoints();

        Allure.step("Assert that side menu points (" + sideMenuPointsBefore
                + ") are equals to main section points (" + mainSectionPointsBefore + ").");
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

        Allure.step("Assert that under 'Week 1' section the first checkbox is checked.");
        assertThat(homePage.getWeek1FirstCheckbox()).isChecked();

        Allure.step("Assert that main section points before test (" +  mainSectionPointsBefore
                + ") are less then points in Main Section after test  (" + mainSectionPointsAfter + ").\n");
        Assert.assertTrue(
                mainSectionPointsBefore < mainSectionPointsAfter,
                "If FAIL: Points in Main Section before test (" + mainSectionPointsBefore
                        + ") did NOT increase compare to Points in Main Section after test  (" + mainSectionPointsAfter + ").\n"
        );

        Allure.step("Assert that side menu points before test (" + sideMenuPointsBefore
                + ") are less then side menu points after test (" + sideMenuPointsAfter + ").");
        Assert.assertTrue(
                sideMenuPointsBefore < sideMenuPointsAfter,
                "If FAIL: Side Menu Points before test (" + sideMenuPointsBefore
                        + ") did NOT increase compare to Side Menu Points after test (" + sideMenuPointsAfter + ").\n"
        );

        Allure.step("Assert that main section points are equal to " + TestData.CHECKBOX_POINTS);
        assertThat(homePage.getMainSectionPointsLocator()).hasText(TestData.CHECKBOX_POINTS);

        Allure.step("Assert that main section points after test (" + mainSectionPointsAfter + ") " +
                "are equals to  side menu points after test (" + sideMenuPointsAfter + ").");
        Assert.assertEquals(
                mainSectionPointsAfter, sideMenuPointsAfter,
                "If FAIL: Points on the main section after test (" + mainSectionPointsAfter
                        + ") are NOT the same as those shown on the side menu after test (" + sideMenuPointsAfter + ").\n"
        );
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Checkboxes")
    @TmsLink("2oz4bwi8yjqj")
    @Description("LMS-1341 Нажатие чекбоксов, https://app.qase.io/plan/LMS/1?case=1341" +
            "   To verify that a non-active checkbox can be successfully checked.")
    @Test(description = "TC1341-01 - The single non-active Checkbox can be checked.")
    public void testTheSingleNonActiveCheckboxCanBeChecked() {
        PreconditionPage precondition = new PreconditionPage(getPage()).init();

        final List<Locator> allCheckboxes = precondition.getAllCheckboxes();

        Allure.step("Precondition: Assert that at least 1 checkbox is shown under the current plan.");
        Assert.assertTrue(allCheckboxes.size() > 0);

        Allure.step("Precondition: Assert that no checkboxes are currently active.");
        Assert.assertTrue(
                precondition.allCheckboxesShouldNotBeActive(),
                "If FAIL: Precondition is not reached. There are active checkboxes.\n"
        );

        HomePage homePage = new HomePage(getPage()).init();

        final int randomIndex = homePage.getRandomIndex();

        homePage
                .clickNthCheckbox(randomIndex);

        Allure.step("Assert that randomly selected checkbox (" + randomIndex + "-nth) is active.");
        assertThat(homePage.getNthCheckbox(randomIndex)).isChecked();

        final Locator checkboxImage = homePage.getNthCheckboxImage(randomIndex);

        Allure.step("Assert that randomly checked checkbox (" + randomIndex + "-nth) has an image.");
        assertThat(checkboxImage).hasCount(1);

        Allure.step("Assert that checkbox image is visible.");
        assertThat(checkboxImage).isVisible();
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Checkboxes")
    @TmsLink("khucr6xuqdib")
    @Description("LMS-1341 Нажатие чекбоксов, https://app.qase.io/plan/LMS/1?case=1341"
            + "To verify the functionality where an active checkbox becomes inactive upon being clicked again.")
    @Test(description = "TC1341-02 - Deactivation of Already Active single Checkbox.")
    public void testDeactivationOfActiveSingleCheckbox() {
        PreconditionPage precondition = new PreconditionPage(getPage()).init();

        final List<Locator> allCheckboxes = precondition.getAllCheckboxes();

        Allure.step("Precondition: Assert that at least 1 checkbox is shown under the current plan.");
        Assert.assertTrue(allCheckboxes.size() > 0);

        final int index = precondition.getRandomIndex();
        final Locator checkbox = allCheckboxes.get(index);

        precondition
                .checkRandomCheckbox(index);

        final Locator image = precondition.getRandomImage(index);

        Allure.step("Precondition: Assert that random (" + index + "-nth) checkbox is already active.");
        assertThat(checkbox).isChecked();

        Allure.step("Precondition: Assert that random (" + index + "-nth) checkbox image is visible.");
        assertThat(image).isVisible();

        HomePage homePage = new HomePage(getPage()).init();

        homePage
                .clickNthCheckbox(index);

        Allure.step("Assert that random (" + index + "-nth) checkbox is not active.");
        assertThat(checkbox).not().isChecked();

        Allure.step("Assert that random (" + index + "-nth) checkbox image is not visible.");
        assertThat(image).not().isVisible();
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Checkboxes")
    @TmsLink("nf0bbnl8cpe4")
    @Description("LMS-1341 Нажатие чекбоксов, https://app.qase.io/plan/LMS/1?case=1341" +
            "To verify the functionality when all checkboxes are checked, and a single active checkbox " +
            "becomes inactive upon clicking again.")
    @Test(description = "TC1341-03 - Deactivation of a single Already Active Checkbox when all checkboxes are active.")
    public void testDeactivationOfSingleCheckboxWhenAllOthersAreActive(){
        PreconditionPage precondition = new PreconditionPage(getPage()).init();

        precondition
                .setAllCheckboxesToBeChecked(TestData.TWO_WEEKS_PLAN);

        Allure.step("Precondition: Assert that All checkboxes are already active under the "
                + TestData.TWO_WEEKS_PLAN + " plan .");
        Assert.assertTrue(
                precondition.allCheckboxesShouldBeChecked(),
                "If FAIL: Precondition 'All checkboxes should be checked' is NOT reached.\n"
        );

        HomePage homePage = new HomePage(getPage()).init();

        final int randomIndex = homePage.getRandomIndex();
        final List<Locator> allCheckboxes = homePage.getAllCheckboxes();
        final Locator randomCheckbox = homePage.getNthCheckbox(randomIndex);

        homePage
                .clickNthCheckbox(randomIndex);

        Allure.step("Assert that single random checkbox is  now Not active.");
        assertThat(randomCheckbox).not().isChecked();

        Allure.step("Assert that all other checkboxes remain active.");
        for (Locator checkbox: allCheckboxes) {
            if (checkbox != randomCheckbox) {

                assertThat(checkbox).isChecked();
            }
        }
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Checkboxes")
    @TmsLink("sc19hl34f3cj")
    @Description("LMS-1341 Нажатие чекбоксов, https://app.qase.io/plan/LMS/1?case=1341" +
            "To verify that the deactivated single checkbox can be checked again " +
            "and the amount of points is decreasing and increasing accordingly.")
    @Test(description = "TC1341-04-E2E - Deactivation of a single Already Active Checkbox " +
            "when all checkboxes are active does not impact the main checkbox functionality.")
    public void testE2EDeactivationCheckboxWithAllCheckboxesActive(){
        PreconditionPage precondition = new PreconditionPage(getPage()).init();

        precondition
                .setAllCheckboxesToBeChecked(TestData.TWO_WEEKS_PLAN);

        Allure.step("Precondition: Assert that All checkboxes are already active under the "
                + TestData.TWO_WEEKS_PLAN + " plan .");
        Assert.assertTrue(
                precondition.allCheckboxesShouldBeChecked(),
                "If FAIL: Precondition 'All checkboxes should be checked' is NOT reached.\n"
        );

        HomePage homePage = new HomePage(getPage()).init();

        final Locator randomCheckbox = homePage.getRandomCheckbox();
        final Locator checkboxImageInitial = homePage.getCheckboxImage(randomCheckbox);
        final int mainSectionPointsInitial = homePage.getMainSectionPoints();
        final int sideMenuPointsInitial = homePage.getSideMenuPoints();

        Allure.step("Assert that the random checkbox is active.");
        assertThat(randomCheckbox).isChecked();

        Allure.step("Assert that the random checkbox image is visible.");
        assertThat(checkboxImageInitial).isVisible();

        Allure.step("Assert that the initial points on the main section and on the side menu are equal.");
        Assert.assertEquals(mainSectionPointsInitial, sideMenuPointsInitial);

        homePage
                .clickRandomCheckbox();

        final Locator checkboxImageAfterFirstClick = homePage.getCheckboxImage(randomCheckbox);
        final int sideMenuPintsAfterFirstClick = homePage.getSideMenuPoints();
        final int mainSectionPointsAfterFirstClick = homePage.getMainSectionPoints();

        Allure.step("Assert that the random checkbox is now Not active.");
        assertThat(randomCheckbox).not().isChecked();

        Allure.step("Assert that the random checkbox image is now Not visible.");
        assertThat(checkboxImageAfterFirstClick).not().isVisible();

        Allure.step("Assert that the points after the first click are less than the initial points.");
        Assert.assertTrue(
                mainSectionPointsAfterFirstClick < mainSectionPointsInitial,
                "If FAIL: Points after the first click are NOT less then initial points.\n"
        );

        Allure.step("Assert that the points on the main section after the first click " +
                "and the points on the side menu after the first click are equal.");
        Assert.assertEquals(
                mainSectionPointsAfterFirstClick, sideMenuPintsAfterFirstClick,
                "If FAIL: Points after first click are NOT equal.\n"
        );

        homePage
                .clickRandomCheckbox();

        final Locator checkboxImageAfterSecondClick = homePage.getCheckboxImage(randomCheckbox);
        final int mainSectionPointsAfterSecondClick = homePage.getMainSectionPoints();
        final int sideMenuPointsAfterSecondClick = homePage.getSideMenuPoints();

        Allure.step("Assert that the random checkbox is now Active.");
        assertThat(randomCheckbox).isChecked();

        Allure.step("Assert that the random checkbox image is now Visible.");
        assertThat(checkboxImageAfterSecondClick).isVisible();

        Allure.step("Assert that the points after the second click " +
                "are greater than the points after the first click.");
        Assert.assertTrue(
                mainSectionPointsAfterSecondClick > mainSectionPointsAfterFirstClick,
                "If FAIL: Points after the second click are NOT greater then points after first click.\n"
        );

        Allure.step("Assert that the points after the second click " +
                "are equal to the initial points amount.");
        Assert.assertEquals(
                mainSectionPointsAfterSecondClick, mainSectionPointsInitial,
                "If FAIL: Points after the second click are not equal initial points amount.\n"
        );

        Allure.step("Assert that the points in main section after the second click " +
                "are equal to the points on side menu after the second click.");
        Assert.assertEquals(
                mainSectionPointsAfterSecondClick, sideMenuPointsAfterSecondClick,
                "If FAIL: Points after the second click are NOT equal.\n"
        );
    }


    @Severity(SeverityLevel.NORMAL)
    @Story("HomePage")
    @TmsLink("zhdhkv1f6nk7")
    @Description("LMS-1342 https://app.qase.io/plan/LMS/1?case=1342" +
            "To verify the modal window is open when clicking the Study This button.")
    @Test(description = "TC1342-01 The modal window is open when clicking the Study This button")
    public void testModalWindowStudyIsOpened() {
        HomePage homePage =
                new HomePage(getPage()).init()
                        .clickStudyThisButton();

        Allure.step("Assert that the modal window is opened");
        assertThat(homePage.getDialog()).isVisible();

        final Locator weakestExamAreasHeader = homePage.getWeakestExamAreasHeader();
        final Locator weakestExamAreasMessage = homePage.getWeakestExamAreasMessage();

        Allure.step("Assert that the modal window header has " + TestData.WEAKEST_EXAM_AREAS + " text.");
        assertThat(weakestExamAreasHeader).hasText(TestData.WEAKEST_EXAM_AREAS);

        Allure.step("Assert that the weakest exam areas message is visible.");
        assertThat(weakestExamAreasMessage).isVisible();

        Allure.step("Assert that the weakest exam areas message has text '"
                + TestData.YOU_HAVE_NOT_STUDIED_ENOUGH + "'.");
        assertThat(weakestExamAreasMessage).hasText(TestData.YOU_HAVE_NOT_STUDIED_ENOUGH);
    }
}

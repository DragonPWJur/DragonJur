package tests;

import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.PreconditionPage;
import pages.TestTutorPage;
import utils.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TestTutorTest extends BaseTest {

    @Test
    public void testUserCanMarkTheCard() {
        PreconditionPage preconditionPage = new PreconditionPage(getPage(), getPlaywright());
        preconditionPage.startTest(TestData.ONE);

        Locator removeFromMarkedButton = new TestTutorPage(getPage(), getPlaywright())
                .clickMarkForReviewButton()
                .getRemoveFromMarkedButton();

        assertThat(removeFromMarkedButton).isVisible();
    }

    @Test
    public void testUserCanSeeTheRightAnswerAndTheExplanation() {

        TestTutorPage testTutorPage = new HomePage(getPage(), getPlaywright())
                .clickTestsMenu()
                .cancelDialogIfVisible()
                .clickDomainsButton()
                .clickRandomCheckbox()
                .clickTutorButton()
                .inputNumberOfQuestions(TestData.ONE)
                .clickGenerateAndStartButton2()
                .clickCorrectAnswerRadioButton()
                .clickConfirmButton();

        assertThat(testTutorPage.getCorrectAnswerBackgroundColor()).isVisible();
        assertThat(testTutorPage.getH3Header()).hasText(TestData.EXPLANATION);
        assertThat(testTutorPage.getH3HeaderExplanationText()).isVisible();
        Assert.assertFalse(testTutorPage.getExplanationText().isEmpty(), "Explanation text is empty");
    }

    @Test
    public void testSuccessfulReportSubmission() {
        new PreconditionPage(getPage(), getPlaywright()).startTest(TestData.ONE);

        TestTutorPage testTutorPage = new TestTutorPage(getPage(), getPlaywright())
                .clickReportButton()
                .inputSymbolsIntoReportAProblemTextarea()
                .clickSendButton();

        assertThat(testTutorPage.getReportAProblemModal()).isVisible();
        assertThat(testTutorPage.getReportSentSuccessfullyMessage()).hasText(TestData.REPORT_MESSAGE);
    }

    @Test
    public void testUserIsAbleToRetrieveTestResults () {

        HomePage homePage = new HomePage(getPage(), getPlaywright());
        int beforeHomeCountPoints = homePage.getProgressbarPointsNumber();
        int beforeHomeCountSideMenuPoints = homePage.getProgressbarSideMenuPointsNumber();

        TestTutorPage testTutorPage = new HomePage(getPage(), getPlaywright())
                .clickTestsMenu()
                .cancelDialogIfVisible()
                .clickDomainsButton()
                .clickRandomCheckbox()
                .inputNumberOfQuestions(TestData.ONE)
                .clickGenerateAndStartButton2()
                .clickCorrectAnswerRadioButton()
                .clickConfirmButton()
                .clickEndButton()
                .clickYesButton();

        getPage().waitForTimeout(2000);

        int testCongratulationCountPoints = testTutorPage
                .getCongratulationPoints();

        Assert.assertTrue(beforeHomeCountPoints < testCongratulationCountPoints,
                "On Congratulation pop-up, expected points after tests to be increased, but didn't");

        int testCountPoints2 = testTutorPage.clickTestNextButton().getTestProgressbarPointsNumber();

        Assert.assertTrue(beforeHomeCountPoints < testCountPoints2,
                "On test progress bar pop-up, expected points after test to be be increased, but didn't.");

        testTutorPage
                .clickTestOkButton()
                .clickCloseTheTestButton()
                .clickHomeMenu();

        int afterHomeCountPoints = homePage.getProgressbarPointsNumber();
        int afterHomeCountSideMenuPoints = homePage.getProgressbarSideMenuPointsNumber();

        Assert.assertTrue(beforeHomeCountPoints < afterHomeCountPoints,
                "Expected on Home diagram points after test to be greater than before test, but didn't.");
        Assert.assertTrue(beforeHomeCountSideMenuPoints < afterHomeCountSideMenuPoints,
                "Expected side menu diagram points after test to be greater than before test, but didn't.");
        assertThat(homePage.getProgressbarPoints()).hasText(TestData.CORRECT_ANSWER_POINTS);
    }
}

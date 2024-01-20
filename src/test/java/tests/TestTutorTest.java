package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.PreconditionPage;
import pages.TestListPage;
import pages.TestTutorPage;
import utils.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.ProjectProperties.BASE_URL;

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

    @Test(description = "TC1344-01 The single non-active Checkbox can be checked.")
    @Description("Objective: To verify that a non-active checkbox ca be successfully checked.")
    @Story("Tests")
    @TmsLink("9lf328qwx4bv")
    public void testTheSingleCheckboxCanBeChecked() {
        String checkboxText = new HomePage(getPage(), getPlaywright())
                .clickTestsMenu()
                .cancelDialogIfVisible()
                .clickDomainsButton()
                .clickRandomCheckboxAndReturnItsName();

        assertThat(new TestListPage(getPage(), getPlaywright()).checkIcon(checkboxText)).isVisible();
    }

    @Test(description = "TC1344-02 Execute Tutor Mode with a randomly selected checkbox in the Domain section.")
    @Description("Objective: To verify that User can successfully activate Tutor mode by checking a random checkbox " +
            "in the Domain section and entering valid data in the ‘Number of Questions’ field.")
    @Story("Tests")
    @TmsLink("9a0yelvj0jdh")
    public void testTutorModeWithRandomCheckboxInDomain() {
        TestTutorPage testTutorPage = new HomePage(getPage(), getPlaywright())
                .clickTestsMenu()
                .cancelDialogIfVisible()
                .clickDomainsButton()
                .clickRandomCheckbox()
                .clickTutorButton()
                .inputNumberOfQuestions(TestData.ONE)
                .clickGenerateAndStartButton();

        waitForPageLoad(TestData.TEST_TUTOR_END_POINT);

        assertThat(getPage()).hasURL(BASE_URL + TestData.TEST_TUTOR_END_POINT);
        assertThat(testTutorPage.getTestQuestion()).containsText(TestData.QUESTION_MARK);
        Assert.assertTrue(testTutorPage.countTestRadioButtons() >= 1);
    }

    @Test
    public void testTutorModeWithRandomCheckboxInChapter() {
        TestTutorPage testTutorPage = new HomePage(getPage(), getPlaywright())
                .clickTestsMenu()
                .cancelDialogIfVisible()
                .clickChaptersButton()
                .clickRandomCheckbox()
                .clickTutorButton()
                .inputNumberOfQuestions(TestData.ONE)
                .clickGenerateAndStartButton();

        waitForPageLoad( TestData.TEST_TUTOR_END_POINT);

        assertThat(getPage()).hasURL(BASE_URL + TestData.TEST_TUTOR_END_POINT);
        assertThat(testTutorPage.getTestQuestion()).containsText(TestData.QUESTION_MARK);
        Assert.assertTrue(testTutorPage.countTestRadioButtons() >= 1);
    }

    @Test
    public void testAfterMarkingTheCardTheNumberOfMarkedCardsIncreasedBy1() {
        new PreconditionPage(getPage(), getPlaywright())
                .startTest(TestData.ONE);

        Locator numberMarked = new TestTutorPage(getPage(), getPlaywright())
                .clickMarkForReviewButton()
                .clickEndButton()
                .clickYesButton()
                .clickSkipButton()
                .clickCloseTheTestButton()
                .getNumberMarked();

        assertThat(numberMarked).isVisible();
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

package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.TestTutorPage;
import tests.helpers.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TestTutorTest extends BaseTest {

//    @Test
//    public void testUserCanMarkTheCard() {
//        PreconditionPage preconditionPage = new PreconditionPage(getPage(), getPlaywright());
//        preconditionPage.startTest(TestData.ONE);
//
//        Locator removeFromMarkedButton = new TestTutorPage(getPage(), getPlaywright())
//                .clickMarkForReviewButton()
//                .getRemoveFromMarkedButton();
//
//        assertThat(removeFromMarkedButton).isVisible();
//    }
//
//    @Test
//    public void testUserCanSeeTheRightAnswerAndTheExplanation() {
//
//        TestTutorPage testTutorPage = new HomePage(getPage(), getPlaywright())
//                .clickTestsMenu()
//                .cancelDialogIfVisible()
//                .clickDomainsButton()
//                .clickRandomCheckbox()
//                .clickTutorButton()
//                .inputNumberOfQuestions(TestData.ONE)
//                .clickGenerateAndStartButton2()
//                .clickCorrectAnswerRadioButton()
//                .clickConfirmButton();
//
//        assertThat(testTutorPage.getCorrectAnswerBackgroundColor()).isVisible();
//        assertThat(testTutorPage.getH3Header()).hasText(TestData.EXPLANATION);
//        assertThat(testTutorPage.getH3HeaderExplanationText()).isVisible();
//        Assert.assertFalse(testTutorPage.getExplanationText().isEmpty(), "Explanation text is empty");
//    }
//
//    @Test
//    public void testSuccessfulReportSubmission() {
//        new PreconditionPage(getPage(), getPlaywright()).startTest(TestData.ONE);
//
//        TestTutorPage testTutorPage = new TestTutorPage(getPage(), getPlaywright())
//                .clickReportButton()
//                .inputSymbolsIntoReportAProblemTextarea()
//                .clickSendButton();
//
//        assertThat(testTutorPage.getReportAProblemModal()).isVisible();
//        assertThat(testTutorPage.getReportSentSuccessfullyMessage()).hasText(TestData.REPORT_MESSAGE);
//    }
//
//    @Test(description = "TC1344-01 The single non-active Checkbox can be checked.")
//    @Description("Objective: To verify that a non-active checkbox ca be successfully checked.")
//    @Story("Tests")
//    @TmsLink("9lf328qwx4bv")
//    public void testTheSingleCheckboxCanBeChecked() {
//        String checkboxText = new HomePage(getPage(), getPlaywright())
//                .clickTestsMenu()
//                .cancelDialogIfVisible()
//                .clickDomainsButton()
//                .clickRandomCheckboxAndReturnItsName();
//
//        assertThat(new TestListPage(getPage(), getPlaywright()).checkIcon(checkboxText)).isVisible();
//    }
//
//    @Test(description = "TC1344-02 Execute Tutor Mode with a randomly selected checkbox in the Domain section.")
//    @Description("Objective: To verify that User can successfully activate Tutor mode by checking a random checkbox " +
//            "in the Domain section and entering valid data in the ‘Number of Questions’ field.")
//    @Story("Tests")
//    @TmsLink("9a0yelvj0jdh")
//    public void testTutorModeWithRandomCheckboxInDomain() {
//        TestTutorPage testTutorPage = new HomePage(getPage(), getPlaywright())
//                .clickTestsMenu()
//                .cancelDialogIfVisible()
//                .clickDomainsButton()
//                .clickRandomCheckbox()
//                .clickTutorButton()
//                .inputNumberOfQuestions(TestData.ONE)
//                .clickGenerateAndStartButton();
//
//        waitForPageLoad(TestData.TEST_TUTOR_END_POINT);
//
//        assertThat(getPage()).hasURL(BASE_URL + TestData.TEST_TUTOR_END_POINT);
//        assertThat(testTutorPage.getTestQuestion()).containsText(TestData.QUESTION_MARK);
//        Assert.assertTrue(testTutorPage.countTestRadioButtons() >= 1);
//    }
//
//    @Test
//    public void testTutorModeWithRandomCheckboxInChapter() {
//        TestTutorPage testTutorPage = new HomePage(getPage(), getPlaywright())
//                .clickTestsMenu()
//                .cancelDialogIfVisible()
//                .clickChaptersButton()
//                .clickRandomCheckbox()
//                .clickTutorButton()
//                .inputNumberOfQuestions(TestData.ONE)
//                .clickGenerateAndStartButton();
//
//        waitForPageLoad( TestData.TEST_TUTOR_END_POINT);
//
//        assertThat(getPage()).hasURL(BASE_URL + TestData.TEST_TUTOR_END_POINT);
//        assertThat(testTutorPage.getTestQuestion()).containsText(TestData.QUESTION_MARK);
//        Assert.assertTrue(testTutorPage.countTestRadioButtons() >= 1);
//    }
//
//    @Test
//    public void testAfterMarkingTheCardTheNumberOfMarkedCardsIncreasedBy1() {
//        new PreconditionPage(getPage(), getPlaywright())
//                .startTest(TestData.ONE);
//
//        Locator numberMarked = new TestTutorPage(getPage(), getPlaywright())
//                .clickMarkForReviewButton()
//                .clickEndButton()
//                .clickYesButton()
//                .clickSkipButton()
//                .clickCloseTheTestButton()
//                .getNumberMarked();
//
//        assertThat(numberMarked).isVisible();
//    }
//
//    @Test(
//            testName = "LMS-1352 Возможность получать результаты. https://app.qase.io/project/LMS?suite=179&case=1352",
//            description = "TC1352-01 Verify the user is able to retrieve test results"
//    )
//    @Description("Objective: To confirm that users can access their test results successfully.")
//    @Story("Tests")
//    @TmsLink("rihwv9xhblbu")
//    public void testUserIsAbleToRetrieveTestResults () {
//
//        HomePage homePage =
//                new HomePage(getPage()).init();
//
//        final int mainSectionPointsBefore = homePage.getMainSectionPoints();
//        final int sideMenuPointsBefore = homePage.getSideMenuPoints();
//
//        TestTutorPage testTutorPage = new HomePage(getPage(), getPlaywright())
//                .clickTestsMenu()
//                .cancelDialogIfVisible()
//                .clickDomainsButton()
//                .clickRandomCheckbox()
//                .inputNumberOfQuestions(TestData.ONE)
//                .clickGenerateAndStartButton2()
//                .clickCorrectAnswerRadioButton()
//                .clickConfirmButton()
//                .clickEndButton()
//                .clickYesButton();
//
//        getPage().waitForTimeout(2000);
//
//        int testCongratulationCountPoints = testTutorPage
//                .getCongratulationPoints();
//
//        Assert.assertTrue(beforeHomeCountPoints < testCongratulationCountPoints,
//                "On Congratulation pop-up, expected points after tests to be increased, but didn't");
//
//        int testCountPoints2 = testTutorPage.clickTestNextButton().getTestProgressbarPointsNumber();
//
//        Assert.assertTrue(beforeHomeCountPoints < testCountPoints2,
//                "On test progress bar pop-up, expected points after test to be be increased, but didn't.");
//
//        testTutorPage
//                .clickTestOkButton()
//                .clickCloseTheTestButton()
//                .clickHomeMenu();
//
//        int mainSectionPointsAfter = homePage.getProgressbarPointsNumber();
//        int sideMenuPointsAfter = homePage.getProgressbarSideMenuPointsNumber();
//
//        Assert.assertTrue(mainSectionPointsBefore < afterHomeCountPoints,
//                "Expected on Home diagram points after test to be greater than before test, but didn't.");
//        Assert.assertTrue(sideMenuPointsBefore < sideMenuPointsAfter,
//                "Expected side menu diagram points after test to be greater than before test, but didn't.");
//        assertThat(homePage.getProgressbarPoints()).hasText(TestData.CORRECT_ANSWER_POINTS);
//    }

    @Test(
            testName = "LMS-1364 Запуск тестов. https://app.qase.io/case/LMS-1364",
            description = "TC1364-01 - Running Test in Study Guide with Gold Subscription"
    )
    @Description("Objective: To confirm that users with a Gold subscription can successfully run a test in the Study Guide section.")
    @Story("Study Guide")
    @TmsLink("ufe2bohbd0sy")
    public void testTestIsRunWhenOpenFromStudyGuide() {
        TestTutorPage testTutorPage =
                new HomePage(getPage()).init()
                        .clickStudyGuideMenu()
                        .scrollToPageBottom()
                        .clickYesButton();

        Assert.assertEquals(
                TestData.LIST_OF_TUTOR_TEST_FOOTER_BUTTONS, testTutorPage.listOfButtonNamesInFooter()
        );
        assertThat(testTutorPage.getTestQuestion()).containsText(TestData.QUESTION_MARK);
        Assert.assertTrue(
                testTutorPage.getAnswersCount() > 0);
    }

}

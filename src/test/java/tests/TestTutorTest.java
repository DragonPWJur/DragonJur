package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.*;
import tests.helpers.TestData;
import tests.helpers.TestUtils;
import utils.runner.ProjectProperties;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TestTutorTest extends BaseTest {

    @Test(
            testName = "LMS-1350 возможность для юзера помечать, https://app.qase.io/plan/LMS/1?case=1350",
            description = "TC1350-01 User can mark the card.")
    @Description("Objective: To confirm the functionality allowing the user to mark a card for review and verify the change in the button's label from 'Mark for review' to 'Remove from marked.'")
    @Story("Test page")
    @TmsLink("2xfbdetx0jqy")
    @Severity(SeverityLevel.NORMAL)
    public void testUserCanMarkTheCard() {
        PreconditionPage precondition = new PreconditionPage(getPage()).init();

        TestTutorPage testTutorPage =
                precondition
                        .startRandomDomainTest(TestData.ONE_QUESTION)
                        .clickMarkForReviewButton();

        final Locator markForReviewButton = testTutorPage.getMarkForReviewButton();
        final Locator removeFromMarkedButton = testTutorPage.getRemoveFromMarkedButton();

        assertThat(markForReviewButton).not().isVisible();
        assertThat(removeFromMarkedButton).isVisible();
        assertThat(removeFromMarkedButton).hasText(TestData.REMOVE_FROM_MARKED);
    }

    @Test(
            testName = "LMS-1348 Видимость для юзера ответа. https://app.qase.io/case/LMS-1348",
            description = "TC1348-01 User can see the right answer and the explanation.")
    @Description("Objective: To ensure that the user can view the correct answer highlighted in green"
            + "and an explanation provided below the answer choices.")
    @Story("Tests")
    @TmsLink("oyaqz2r86t96")
    @Severity(SeverityLevel.NORMAL)
    public void testUserCanSeeTheRightAnswerAndTheExplanation() {

        TestTutorPage testTutorPage =
                new HomePage(getPage()).init()
                        .clickTestsMenu()
                        .cancelDialogIfVisible()
                        .clickDomainsButtonIfNotActive()
                        .clickRandomCheckbox()
                        .clickTutorButton()
                        .inputNumberOfQuestions(TestData.ONE_QUESTION)
                        .clickGenerateAndStartTutorTestButton()
                        .clickCorrectAnswer()
                        .clickConfirmButton();

        final Locator correctAnswerBgColor55B47D = testTutorPage.getCorrectAnswerBackgroundColor55B47D();
        final Locator explanationHeader = testTutorPage.getH3Header();
        final Locator answerExplanation = testTutorPage.getExplanationTextLocator();

        assertThat(correctAnswerBgColor55B47D).isVisible();
        assertThat(explanationHeader).hasText(TestData.EXPLANATION);
        assertThat(answerExplanation).isVisible();
        Assert.assertFalse(answerExplanation.innerText().isEmpty(),
                "If FAIL: Explanation text is empty."
        );
    }

    // This test is being ignored due to a bug in the application.
    @Ignore
    @Test(
            testName = "LMS-1351 Возможность для юзера отправлять репорты. https://app.qase.io/case/LMS-1351",
            description = "TC1351-01 - Successful Report Submission by User.")
    @Description("Objective: To validate that users can report problems successfully by initiating the 'Report a Problem' function.")
    @Story("Tests")
    @TmsLink("ma75x05c8pvc")
    @Severity(SeverityLevel.NORMAL)
    public void testSuccessfulReportSubmission() {

        ReportAProblemModal reportAProblemModal =
                new PreconditionPage(getPage()).init()
                        .startRandomDomainTest(TestData.ONE_QUESTION)
                        .clickReportAProblemButton()
                        .inputText(TestUtils.getRandomString(10))
                        .clickSendButton();

        final Locator modalDialog = reportAProblemModal.getDialog();
        final Locator message = reportAProblemModal.getReportMessage();

        assertThat(modalDialog).isVisible();
        assertThat(message).isVisible();
        assertThat(message).hasText(TestData.THE_REPORT_HAS_BEEN_SENT_SUCCESSFULLY);
    }

    @Test(
            testName = "LMS-1344 Запуск tutor. https://app.qase.io/case/LMS-1344",
            description = "TC1344-01 The single non-active Checkbox can be checked.")
    @Description("Objective: To verify that a non-active checkbox ca be successfully checked.")
    @Story("Tests")
    @TmsLink("9lf328qwx4bv")
    @Severity(SeverityLevel.NORMAL)
    public void testTheSingleCheckboxCanBeChecked() {
         TestListPage testListPage = new HomePage(getPage()).init()
                .clickTestsMenu()
                .cancelDialogIfVisible()
                .clickDomainsButtonIfNotActive();

        final String checkboxText = testListPage.clickRandomCheckboxAndReturnItsName();

        assertThat(testListPage.getCheckboxImage(checkboxText)).isVisible();
    }

    @Test(
            testName = "LMS-1344 Запуск tutor. https://app.qase.io/case/LMS-1344",
            description = "TC1344-02 Execute Tutor Mode with a randomly selected checkbox in the Domain section.")
    @Description("Objective: To verify that User can successfully activate Tutor mode by checking a random checkbox " +
            "in the Domain section and entering valid data in the ‘Number of Questions’ field.")
    @Story("Tests")
    @TmsLink("9a0yelvj0jdh")
    @Severity(SeverityLevel.NORMAL)
    public void testTutorModeWithRandomCheckboxInDomain() {
        TestTutorPage testTutorPage =
                new HomePage(getPage()).init()
                        .clickTestsMenu()
                        .cancelDialogIfVisible()
                        .clickDomainsButtonIfNotActive()
                        .clickRandomCheckbox()
                        .clickTutorButton()
                        .inputNumberOfQuestions(TestData.ONE_QUESTION)
                        .clickGenerateAndStartTutorTestButton();

        final String expectedUrl = ProjectProperties.BASE_URL + TestData.TEST_TUTOR_END_POINT;
        final Locator question = testTutorPage.getTestQuestion();
        final List<Locator> answers = testTutorPage.getRadioButtons();

        assertThat(getPage()).hasURL(expectedUrl);
        assertThat(question).containsText(TestData.QUESTION_MARK);
        Assert.assertTrue(
                answers.size() >= 1,
                "If FAIL: There are no answers for the question."
        );
    }

    @Test(
            testName = "LMS-1344 Запуск tutor. https://app.qase.io/case/LMS-1344",
            description = "TC1344-03 - Execute Tutor Mode with a randomly selected checkbox in the Chapter section")
    @Description("Objective: To verify that the User can successfully activate Tutor mode by checking a random checkbox"
            + "in the Chapter section and entering valid data in the ‘Number of Questions’ field")
    @Story("Tests")
    @TmsLink("l3twyfx5esxv")
    @Severity(SeverityLevel.NORMAL)
    public void testTutorModeWithRandomCheckboxInChapter() {
        TestTutorPage testTutorPage =
                new HomePage(getPage()).init()
                        .clickTestsMenu()
                        .cancelDialogIfVisible()
                        .clickChaptersButton()
                        .clickRandomCheckbox()
                        .clickTutorButton()
                        .inputNumberOfQuestions(TestData.ONE_QUESTION)
                        .clickGenerateAndStartTutorTestButton();

        final String expectedUrl = ProjectProperties.BASE_URL + TestData.TEST_TUTOR_END_POINT;
        final Locator question = testTutorPage.getTestQuestion();
        final List<Locator> answers = testTutorPage.getRadioButtons();

        assertThat(getPage()).hasURL(expectedUrl);
        assertThat(question).containsText(TestData.QUESTION_MARK);
        Assert.assertTrue(
                answers.size() >= 1,
                "If FAIL: There are no answers for the question."
        );
    }

    @Ignore
    @Test(
            testName = "LMS-1350 возможность для юзера помечать., https://app.qase.io/plan/LMS/1?case=1350",
            description = "TC1350-02 After marking the card, the number of marked cards increased by 1.")
    @Description("Objective: To confirm the functionality allowing the user to mark a card for review and the amount of marked cards increased by 1.")
    @Story("Test page")
    @TmsLink("7qvothosvy8q")
    @Severity(SeverityLevel.NORMAL)
    public void testAfterMarkingTheCardTheNumberOfMarkedCardsIncreasedBy1() {

        PreconditionPage precondition = new PreconditionPage(getPage()).init();

        final int numberOfQuestionsMarkedBeforeTest = precondition.getNumberQuestionsMarkedBeforeTest();

        final int markedQuestionsNumber =
                precondition
                        .startRandomDomainTest(TestData.ONE_QUESTION)
                        .clickMarkForReviewButton()
                        .clickEndButton()
                        .clickYesButton()
                        .clickSkipButton()
                        .clickCloseTheTestButton()
                        .getMarkedNumber();

        Assert.assertEquals(
                markedQuestionsNumber, 1,
                "If FAIL: NumberOfMarkedCards (" + numberOfQuestionsMarkedBeforeTest
                        + ") does NOT increased by 1 (" + markedQuestionsNumber + ")."
        );
    }

    @Test(
            testName = "LMS-1352 Возможность получать результаты. https://app.qase.io/project/LMS?suite=179&case=1352",
            description = "TC1352-01 Verify the user is able to retrieve test results.")
    @Description("Objective: To confirm that users can access their test results successfully.")
    @Story("Tests")
    @TmsLink("rihwv9xhblbu")
    @Severity(SeverityLevel.NORMAL)
    public void testUserIsAbleToRetrieveTestResults() {

        HomePage homePage = new HomePage(getPage()).init();

        final int mainSectionPointsBefore = homePage.getMainSectionPoints();
        final int sideMenuPointsBefore = homePage.getSideMenuPoints();

        Assert.assertEquals(
                mainSectionPointsBefore, sideMenuPointsBefore,
                "If FAIL: Points on Main Section (" + mainSectionPointsBefore
                        + ") does NOT match points on Side Menu (" + sideMenuPointsBefore + ").\n"
        );

        CongratulationsModal congratulationsModal =
                new HomePage(getPage()).init()
                        .clickTestsMenu()
                        .cancelDialogIfVisible()
                        .clickDomainsButtonIfNotActive()
                        .clickRandomCheckbox()
                        .inputNumberOfQuestions(TestData.ONE_QUESTION)
                        .clickGenerateAndStartTutorTestButton()
                        .clickCorrectAnswer()
                        .clickConfirmButton()
                        .clickEndButton()
                        .clickYesToCongratulationButton();

        final int pointsOnModalAfter = congratulationsModal.getCongratulationPoints();

        Assert.assertTrue(
                sideMenuPointsBefore <= pointsOnModalAfter,
                "If FAIL: On Congratulation pop-up, expected points after test does NOT increased."
        );

        congratulationsModal
                .clickNextButton();

        final int secondModalPointsAfter = congratulationsModal.getTestPoints();

        Assert.assertTrue(mainSectionPointsBefore < secondModalPointsAfter,
                "If FAIL: On Congratulation second pop-up, expected points after test does NOT increased.");

        congratulationsModal
                .clickOkButton()
                .clickCloseTheTestButton()
                .clickHomeMenu();

        final int mainSectionPointsAfter = homePage.getMainSectionPoints();
        final int sideMenuPointsAfter = homePage.getSideMenuPoints();

        Assert.assertEquals(mainSectionPointsAfter, sideMenuPointsAfter,
                "If FAIL: Points on Main Section (" + mainSectionPointsAfter
                        + ") does NOT match points on Side Menu (" + sideMenuPointsAfter + ").\\n"
        );
        Assert.assertTrue(
                mainSectionPointsBefore < mainSectionPointsAfter,
                "If FAIL: Points after running test (" + mainSectionPointsAfter
                        + ") are not greater then points before running test (" + mainSectionPointsBefore + ").\n"
        );
    }

    @Ignore
    @Test(
            testName = "LMS-1364 Запуск тестов. https://app.qase.io/case/LMS-1364",
            description = "TC1364-01 - Running Test in Study Guide with Gold Subscription")
    @Description("Objective: To confirm that users with a Gold subscription can successfully run a test in the Study Guide section.")
    @Story("Study Guide")
    @TmsLink("ufe2bohbd0sy")
    @Severity(SeverityLevel.NORMAL)
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
                testTutorPage.getAnswersCount() > 0,
                "If FAIL: The multiple-choice test should contain at least one answer.\n");
    }
}
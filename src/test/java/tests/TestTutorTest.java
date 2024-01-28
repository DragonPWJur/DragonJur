package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
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

    @Test
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

    @Test
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

    @Ignore
    @Test
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

    @Test(description = "TC1344-02 Execute Tutor Mode with a randomly selected checkbox in the Domain section.")
    @Description("Objective: To verify that User can successfully activate Tutor mode by checking a random checkbox " +
            "in the Domain section and entering valid data in the ‘Number of Questions’ field.")
    @Story("Tests")
    @TmsLink("9a0yelvj0jdh")
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

    @Test
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
    @Test
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

    @Ignore
    @Test
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
                sideMenuPointsBefore < pointsOnModalAfter,
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
}

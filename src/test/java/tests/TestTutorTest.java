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

    @Severity(SeverityLevel.NORMAL)
    @Story("Tests")
    @TmsLink("2xfbdetx0jqy")
    @Description("LMS-1350 возможность для юзера помечать, https://app.qase.io/plan/LMS/1?case=1350" +
            "   Objective: To confirm the functionality allowing the user to mark a card for review " +
            "and verify the change in the button's label from 'Mark for review' to 'Remove from marked.'")
    @Test(description = "TC1350-01 User can mark the card.")
    public void testUserCanMarkTheCard() {
        PreconditionPage precondition = new PreconditionPage(getPage()).init();

        TestTutorPage testTutorPage =
                precondition
                        .startRandomDomainTest(TestData.ONE_QUESTION);

        final Locator markForReviewButton = testTutorPage.getMarkForReviewButton();

        Allure.step("Assert that 'Mark for review' button is visible.");
        assertThat(markForReviewButton).isVisible();

        testTutorPage
                .clickMarkForReviewButton();

        final Locator removeFromMarkedButton = testTutorPage.getRemoveFromMarkedButton();

        Allure.step("Assert that 'Remove From Marked' button is visible.");
        assertThat(removeFromMarkedButton).isVisible();
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Tests")
    @TmsLink("oyaqz2r86t96")
    @Description("LMS-1348 Видимость для юзера ответа. https://app.qase.io/case/LMS-1348" +
            "Objective: To ensure that the user can view the correct answer highlighted in green"
            + "and an explanation provided below the answer choices.")
    @Test(description = "TC1348-01 User can see the right answer and the explanation.")
    public void testUserCanSeeTheRightAnswerAndTheExplanation() {
        TestTutorPage testTutorPage =
                new HomePage(getPage()).init()
                        .clickTestsMenu()
                        .cancelDialogIfVisible()
                        .clickDomainsButton()
                        .clickRandomCheckboxDomain()
                        .clickTutorButton()
                        .inputNumberOfQuestions(TestData.ONE_QUESTION)
                        .clickGenerateAndStartTutorTestButton()
                        .clickCorrectAnswer()
                        .clickConfirmButton();

        final Locator correctAnswerBgColor55B47D = testTutorPage.getCorrectAnswerBackgroundColor55B47D();
        final Locator explanationHeader = testTutorPage.getH3Header();
        final Locator answerExplanation = testTutorPage.getExplanationTextLocator();
        final String correctAnswerText = testTutorPage.getCorrectAnswer().innerText();

        Allure.step("Assert that correct answer '" + correctAnswerText + "' contains 'Correct' word.");
        Assert.assertTrue(correctAnswerText.contains("Correct"));

        Allure.step("Assert that correct answer highlighted in green.");
        assertThat(correctAnswerBgColor55B47D).isVisible();

        Allure.step("Assert that header '" + TestData.EXPLANATION + "' is present on the page.");
        assertThat(explanationHeader).hasText(TestData.EXPLANATION);

        Allure.step("Assert that answer explanation is visible.");
        assertThat(answerExplanation).isVisible();

        Allure.step("Assert that answer explanation text '" + answerExplanation.innerText() + "' is not empty.");
        Assert.assertFalse(answerExplanation.innerText().isEmpty(),
                "If FAIL: Explanation text is empty."
        );
    }

    // This test is flaky due to a bug in the application.
    //<div><span class="sc-iBkjds gpMBxJ sc-fJoEar ivteeS">Sending error</span><span class="sc-iBkjds gpMBxJ sc-bQRcPC klVndr">Try again later</span></div>
    @Ignore
    @Severity(SeverityLevel.NORMAL)
    @Story("Tests")
    @TmsLink("ma75x05c8pvc")
    @Description("LMS-1351 Возможность для юзера отправлять репорты. https://app.qase.io/case/LMS-1351" +
            "Objective: To validate that users can report problems successfully by initiating the 'Report a Problem' function.")
    @Test(description = "TC1351-01 - Successful Report Submission by User.")
    public void testSuccessfulReportSubmission() {
        ReportAProblemModal reportAProblemModal =
                new PreconditionPage(getPage()).init()
                        .startRandomDomainTest(TestData.ONE_QUESTION)
                        .clickReportAProblemButton()
                        .inputText(TestUtils.getRandomString(10))
                        .clickSendButton();

        final Locator modalDialog = reportAProblemModal.getDialog();
        final Locator message = reportAProblemModal.getReportMessage();

        Allure.step("Assert that 'Report a Problem' modal window is visible.");
        assertThat(modalDialog).isVisible();

        Allure.step("Assert that the report message is visible.");
        assertThat(message).isVisible();

        Allure.step("Assert that the report message has text '" + TestData.THE_REPORT_HAS_BEEN_SENT_SUCCESSFULLY + "'.");
        assertThat(message).hasText(TestData.THE_REPORT_HAS_BEEN_SENT_SUCCESSFULLY);
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Tests")
    @TmsLink("9lf328qwx4bv")
    @Description("LMS-1344 Запуск tutor. https://app.qase.io/case/LMS-1344" +
            "Objective: To verify that a non-active checkbox ca be successfully checked.")
    @Test(description = "TC1344-01 The single non-active Checkbox can be checked on TestList Page.")
    public void testTheSingleCheckboxCanBeCheckedOnTestListPage() {
        TestListPage testListPage = new HomePage(getPage()).init()
                .clickTestsMenu()
                .cancelDialogIfVisible()
                .clickDomainsButton()
                .clickCheckboxRandom();

        final Locator randomCheckbox = testListPage.getRandomCheckbox();

        Allure.step("Assert that random checkbox has a 'checked' state.");
        assertThat(randomCheckbox).isChecked();

        final String checkboxText = testListPage.getRandomCheckboxName();
        final Locator checkBoxImage = testListPage.getCheckboxImage(checkboxText);

        Allure.step("Assert that random checkbox has a 'checked' image.");
        assertThat(checkBoxImage).isVisible();
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Tests")
    @TmsLink("9a0yelvj0jdh")
    @Description("LMS-1344 Запуск tutor. https://app.qase.io/case/LMS-1344" +
            "Objective: To verify that User can successfully activate Tutor mode by checking a random checkbox " +
            "in the Domain section and entering valid data in the ‘Number of Questions’ field.")
    @Test(description = "TC1344-02 Execute Tutor Mode with a randomly selected checkbox in the Domain section.")
    public void testTutorModeWithRandomCheckboxInDomain() {
        TestTutorPage testTutorPage =
                new HomePage(getPage()).init()
                        .clickTestsMenu()
                        .cancelDialogIfVisible()
                        .clickDomainsButton()
                        .clickRandomCheckboxDomain()
                        .clickTutorButton()
                        .inputNumberOfQuestions(TestData.ONE_QUESTION)
                        .clickGenerateAndStartTutorTestButton();

        final String expectedUrl = ProjectProperties.BASE_URL + TestData.TEST_TUTOR_END_POINT;
        final Locator question = testTutorPage.getTestQuestion();
        final List<Locator> answers = testTutorPage.getRadioButtons();

        Allure.step("Assert that user was redirected to '" + TestData.TEST_TUTOR_END_POINT + "' page.");
        assertThat(getPage()).hasURL(expectedUrl);

        Allure.step("Assert that user can see the test question.");
        assertThat(question).isVisible();

        Allure.step("Assert that question text contains question mark.");
        assertThat(question).containsText(TestData.QUESTION_MARK);

        Allure.step("Assert that at least one answer is shown.");
        Assert.assertTrue(
                answers.size() >= 1,
                "If FAIL: There are no answers for the question."
        );
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Tests")
    @TmsLink("l3twyfx5esxv")
    @Description("LMS-1344 Запуск tutor. https://app.qase.io/case/LMS-1344" +
            "Objective: To verify that the User can successfully activate Tutor mode by checking a random checkbox"
            + "in the Chapter section and entering valid data in the ‘Number of Questions’ field")
    @Test(description = "TC1344-03 - Execute Tutor Mode with a randomly selected checkbox in the Chapter section",
            priority = 1)
    public void testTutorModeWithRandomCheckboxInChapter() {
        TestTutorPage testTutorPage =
                new HomePage(getPage()).init()
                        .clickTestsMenu()
                        .cancelDialogIfVisible()
                        .clickChaptersButton()
                        .clickRandomCheckboxChapter()
                        .clickTutorButton()
                        .inputNumberOfQuestions(TestData.ONE_QUESTION)
                        .clickGenerateAndStartTutorTestButton();

        final String expectedUrl = ProjectProperties.BASE_URL + TestData.TEST_TUTOR_END_POINT;
        final Locator question = testTutorPage.getTestQuestion();
        final List<Locator> answers = testTutorPage.getRadioButtons();

        Allure.step("Assert that user was redirected to '" + TestData.TEST_TUTOR_END_POINT + "' page.");
        assertThat(getPage()).hasURL(expectedUrl);

        Allure.step("Assert that user can see the test question.");
        assertThat(question).isVisible();

        Allure.step("Assert that question text contains question mark.");
        assertThat(question).containsText(TestData.QUESTION_MARK);

        Allure.step("Assert that at least one answer is shown.");
        Assert.assertTrue(
                answers.size() >= 1,
                "If FAIL: There are no answers for the question."
        );
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Tests")
    @TmsLink("7qvothosvy8q")
    @Description("LMS-1350 возможность для юзера помечать., https://app.qase.io/plan/LMS/1?case=1350" +
            "  Objective: To confirm the functionality allowing the user to mark a card for review and the amount of marked cards increased by 1.")
    @Test(description = "TC1350-02 After marking the card, the number of marked cards increased by 1.")
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

        Allure.step("Assert that number of questions marked before test (" + numberOfQuestionsMarkedBeforeTest +
                ") is increased by one and now equals " +  markedQuestionsNumber + ".");
        Assert.assertEquals(
                markedQuestionsNumber, 1,
                "If FAIL: NumberOfMarkedCards (" + numberOfQuestionsMarkedBeforeTest
                        + ") does NOT increased by 1 (" + markedQuestionsNumber + ")."
        );
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Tests")
    @TmsLink("rihwv9xhblbu")
    @Description("LMS-1352 Возможность получать результаты. https://app.qase.io/project/LMS?suite=179&case=1352" +
            "   Objective: To confirm that users can access their test results successfully.")
    @Test(description = "TC1352-01 Verify the user is able to retrieve test results.")
    public void testUserIsAbleToRetrieveTestResults() {
        HomePage homePage = new HomePage(getPage()).init();

        final int mainSectionPointsBefore = homePage.getMainSectionPoints();
        final int sideMenuPointsBefore = homePage.getSideMenuPoints();

        Allure.step("Assert that Main Section Points before test (" + mainSectionPointsBefore + ") are equal to " +
                "Side Menu Points before test (" + sideMenuPointsBefore + ").");
        Assert.assertEquals(
                mainSectionPointsBefore, sideMenuPointsBefore,
                "If FAIL: Points on Main Section (" + mainSectionPointsBefore
                        + ") does NOT match points on Side Menu (" + sideMenuPointsBefore + ").\n"
        );

        CongratulationsModal congratulationsModal =
                homePage
                        .clickTestsMenu()
                        .cancelDialogIfVisible()
                        .clickDomainsButton()
                        .clickRandomCheckboxDomain()
                        .inputNumberOfQuestions(TestData.ONE_QUESTION)
                        .clickGenerateAndStartTutorTestButton()
                        .clickCorrectAnswer()
                        .clickConfirmButton()
                        .clickEndButton()
                        .clickYesToCongratulationButton();

        final int pointsOnModalAfter = congratulationsModal.getCongratulationPoints();

        Allure.step("Assert that points on 'Congratulations' modal window (" + pointsOnModalAfter + ") after test " +
                " are greater or equals to Main Section Points before test (" + mainSectionPointsBefore + ").");
        Assert.assertTrue(
                sideMenuPointsBefore <= mainSectionPointsBefore,
                "If FAIL: On Congratulation pop-up, expected points after test does NOT increased."
        );

        congratulationsModal
                .clickNextButton();

        final int secondModalPointsAfter = congratulationsModal.getTestPoints();

        Allure.step("Assert that points on second 'Congratulations' modal window (" + secondModalPointsAfter
                + ") after test " + " are greater or equals to Main Section Points before test ("
                + mainSectionPointsBefore + ").");
        Assert.assertTrue(mainSectionPointsBefore < secondModalPointsAfter,
                "If FAIL: On Congratulation second pop-up, expected points after test does NOT increased.");

        congratulationsModal
                .clickOkButton()
                .clickCloseTheTestButton()
                .clickHomeMenu();

        final int mainSectionPointsAfter = homePage.getMainSectionPoints();
        final int sideMenuPointsAfter = homePage.getSideMenuPoints();

        Allure.step("Assert that Main Section Points after test (" + mainSectionPointsAfter + ") are equal to " +
                "Side Menu Points after test (" + sideMenuPointsAfter + ").");
        Assert.assertEquals(mainSectionPointsAfter, sideMenuPointsAfter,
                "If FAIL: Points on Main Section (" + mainSectionPointsAfter
                        + ") does NOT match points on Side Menu (" + sideMenuPointsAfter + ").\\n"
        );

        Allure.step("Assert that Main Section Points after test (" + mainSectionPointsAfter + ") are greater then " +
                "Main Section Points before test (" + mainSectionPointsBefore + ").");
        Assert.assertTrue(
                mainSectionPointsBefore < mainSectionPointsAfter,
                "If FAIL: Points after running test (" + mainSectionPointsAfter
                        + ") are not greater then points before running test (" + mainSectionPointsBefore + ").\n"
        );
    }
}
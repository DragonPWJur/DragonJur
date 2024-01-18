package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.PreconditionPage;
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
    @TmsLink("l3twyfx5esxv")
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

        waitForPageLoad(TestData.TEST_TUTOR_END_POINT);

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
}

package tests;

import io.qameta.allure.*;
import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.ProjectProperties.BASE_URL;

public class TestListTest extends BaseTest {

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
//        assertThat(testsPage.getTestQuestion()).containsText(TestData.QUESTION_MARK);
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
//        assertThat(testsPage.getTestQuestion()).containsText(TestData.QUESTION_MARK);
        Assert.assertTrue(testTutorPage.countTestRadioButtons() >= 1);
    }

    @Ignore
    @Test
    public void testRunTimedMode() {
        TestTimedPage testTimedPage = new HomePage(getPage(), getPlaywright())
                .clickTestsMenu()
                .cancelDialogIfVisible()
                .clickTimedButton()
                .clickGenerateAndStartButton1()
                .clickStartTestButton()
                .clickStartButton();

        assertThat(getPage()).hasURL(BASE_URL + TestData.TEST_TIMED_END_POINT);
        assertThat(testTimedPage.getTimer()).isVisible();
        // assertThat(testTimedPage.getQuestionMark()).containsText(TestData.QUESTION_MARK);
        Assert.assertTrue(testTimedPage.getAnswersCount() > 0);
    }

    @Ignore
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

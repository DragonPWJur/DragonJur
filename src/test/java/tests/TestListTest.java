package tests;

import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.ProjectProperties.BASE_URL;

public class TestListTest extends BaseTest {

    @Test
    public void testTutorModeWithRandomCheckboxInDomain() {
        TestsPage testsPage = new HomePage(getPage(), getPlaywright())
                .clickTestsMenu()
                .cancelDialogIfVisible()
                .clickDomainsButton()
                .clickRandomCheckbox()
                .clickTutorButton()
                .inputNumberOfQuestions("1")
                .clickGenerateAndStartButton();

        waitForPageLoad(TestData.TEST_TUTOR_END_POINT);

        assertThat(getPage()).hasURL(BASE_URL + TestData.TEST_TUTOR_END_POINT);
//        assertThat(testsPage.getTestQuestion()).containsText("?");
        Assert.assertTrue(testsPage.countTestRadioButtons() >= 1);
    }

    @Ignore
    @Test
    public void testTutorModeWithRandomCheckboxInChapter() {
        TestsPage testsPage = new HomePage(getPage(), getPlaywright())
                .clickTestsMenu()
                .cancelDialogIfVisible()
                .clickChaptersButton()
                .clickRandomCheckbox()
                .clickTutorButton()
                .inputNumberOfQuestions("1")
                .clickGenerateAndStartButton();

        waitForPageLoad(TestData.TEST_TUTOR_END_POINT);

        assertThat(getPage()).hasURL(BASE_URL + TestData.TEST_TUTOR_END_POINT);
//        assertThat(testsPage.getTestQuestion()).containsText("?");
        Assert.assertTrue(testsPage.countTestRadioButtons() >= 1);
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

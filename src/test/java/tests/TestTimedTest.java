package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.TestTimedPage;
import utils.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.ProjectProperties.BASE_URL;

public class TestTimedTest extends BaseTest {

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
        assertThat(testTimedPage.getQuestionMark()).containsText(TestData.QUESTION_MARK);
        Assert.assertTrue(testTimedPage.getAnswersCount() > 0);
    }
}
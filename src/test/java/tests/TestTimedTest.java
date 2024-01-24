package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.TestTimedPage;
import tests.helpers.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.runner.ProjectProperties.BASE_URL;

public final class TestTimedTest extends BaseTest {

//    @Test
//    public void testRunTimedMode() {
//        TestTimedPage testTimedPage = new HomePage(getPage(), getPlaywright())
//                .clickTestsMenu()
//                .cancelDialogIfVisible()
//                .clickTimedButton()
//                .clickGenerateAndStartButton1()
//                .clickStartTestButton()
//                .clickStartButton();
//
//        assertThat(getPage()).hasURL(BASE_URL + TestData.TEST_TIMED_END_POINT);
//        assertThat(testTimedPage.getTimer()).isVisible();
//        assertThat(testTimedPage.getQuestionMark()).containsText(TestData.QUESTION_MARK);
//        Assert.assertTrue(testTimedPage.getAnswersCount() > 0);
//    }
}
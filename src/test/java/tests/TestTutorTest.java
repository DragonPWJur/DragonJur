package tests;

import com.microsoft.playwright.Locator;
import org.testng.annotations.Test;
import pages.PreconditionPage;
import pages.TestTutorPage;
import utils.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TestTutorTest extends BaseTest  {

    @Test
    public void testUserCanMarkTheCard() {
        PreconditionPage preconditionPage = new PreconditionPage(getPage(), getPlaywright());
        preconditionPage.resetCourseResults();
        preconditionPage.startTest(TestData.ONE);

        Locator removeFromMarkedButton = new TestTutorPage(getPage(), getPlaywright())
                .clickMarkForReviewButton()
                .getRemoveFromMarkedButton();

        assertThat(removeFromMarkedButton).isVisible();
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
}

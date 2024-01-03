package tests;

import org.testng.annotations.Test;
import pages.HomePage;
import pages.TestsPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TestListTest extends BaseTest {

    @Test
    public void testTutorModeWithRandomCheckboxInDomain() {
        String expectedNumberOfQuestions = "5";
        String testTutorEndPoint = "/test-tutor";

        TestsPage testsPage = new HomePage(getPage(), getPlaywright())
                .clickTestsButton()
                .handleDialogAndCancel()
                .clickDomainsButton()
                .clickRandomCheckbox()
                .clickTutorButton()
                .fillNumberOfQuestionsInputField(expectedNumberOfQuestions)
                .clickGenerateStartButton();

        waitForPageLoad(testTutorEndPoint);
        assertThat(testsPage.headerQuestionsCount).hasText("/" + expectedNumberOfQuestions);
        assertThat(getPage()).hasURL(getBaseUrl() + testTutorEndPoint);
    }
}

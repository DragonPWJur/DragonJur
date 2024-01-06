package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.TestsPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TestListTest extends BaseTest {

    @Test
    public void testTutorModeWithRandomCheckboxInDomain() {
        String expectedNumberOfQuestions = "1";
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
        assertThat(getPage()).hasURL(getBaseUrl() + testTutorEndPoint);
        assertThat(testsPage.testQuestion).containsText("?");
        Assert.assertTrue(testsPage.getTestRadioButtonsCount() >= 1);
    }
}

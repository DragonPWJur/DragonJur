package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.TestsPage;
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

        waitForPageLoad(TestData.testTutorEndPoint);

        assertThat(getPage()).hasURL(BASE_URL + TestData.testTutorEndPoint);
        assertThat(testsPage.getTestQuestion()).containsText("?");
        Assert.assertTrue(testsPage.countTestRadioButtons() >= 1);
    }
}

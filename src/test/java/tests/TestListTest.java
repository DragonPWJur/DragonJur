package tests;

import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.TestsPage;
import utils.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static java.lang.Integer.parseInt;
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
        assertThat(testsPage.getTestQuestion()).containsText("?");
        Assert.assertTrue(testsPage.countTestRadioButtons() >= 1);
    }

    @Test
    public void testAfterMarkingCardNumberOfMarkedCardsIncreasedBy1() {

        String numberMarked = new HomePage(getPage(), getPlaywright())
                .clickTestsMenu()
                .cancelDialogIfVisible()
                .getNumberMarked().innerText();

        String expectedResult = Integer.toString(parseInt(numberMarked) + 1);

        Locator actual = new HomePage(getPage(), getPlaywright())
                .initiateTest()
                .clickMarkForReviewButtonIfVisible()
                .clickEndButton()
                .endTestIfVisible()
                .clickSkipButton()
                .clickCloseTheTestButton()
                .getNumberMarked();

        assertThat(actual).hasText(expectedResult);
    }
}

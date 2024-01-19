package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.PerformancePage;
import pages.PreconditionPage;
import utils.TestData;
import utils.TestUtils;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.ProjectProperties.BASE_URL;

public class PerformanceTest extends BaseTest {
    @Test
    public void testDisplayingStatisticsForTests() {
        PreconditionPage preconditionPage = new PreconditionPage(getPage(), getPlaywright());
        int numbersOfQuestion = preconditionPage.checkNumberOfQuestions();

        preconditionPage.startTestDomainForStats("History and Civilization for Stats","4");
        preconditionPage.passTestOneAnswersIncorrect(4);
        preconditionPage.startTestDomainForStats("Automation testing for stats","5");
        preconditionPage.passTestAllAnswersCorrect(5);

        PerformancePage performancePage = new HomePage(getPage(), getPlaywright())
                .clickPerformanceMenu()
                .clickOverallDropdown()
                .clickTestsButtonInBanner();

        assertThat(getPage()).hasURL(BASE_URL + TestData.PERFORMANCE_END_POINT);
        assertThat(performancePage.getSettedFilter()).hasText("All tests");

        performancePage.clickDropdown();

        double expectedCorrectPercentage = TestUtils.getPercentageOfNumber(8, numbersOfQuestion);
        double expectedIncorrectPercentage = TestUtils.getPercentageOfNumber(1, numbersOfQuestion);

        Assert.assertEquals(performancePage.getCorrectPercentage(), expectedCorrectPercentage);
        Assert.assertEquals(performancePage.getCorrectNumbers(), 8);
        Assert.assertEquals(performancePage.getIncorrectPercentage(), expectedIncorrectPercentage);
        Assert.assertEquals(performancePage.getIncorrectNumbers(), 1);

        performancePage
                .clickDropdown()
                .setLastTest()
                .clickDropdown();

        double expectedCorrectPercentageForTest = TestUtils.getPercentageOfNumber(5, 5);
        double expectedIncorrectPercentageForTest = TestUtils.getPercentageOfNumber(0, 5);

        Assert.assertEquals(performancePage.getCorrectPercentage(), expectedCorrectPercentageForTest);
        Assert.assertEquals(performancePage.getCorrectNumbers(), 5);
        Assert.assertEquals(performancePage.getIncorrectPercentage(), expectedIncorrectPercentageForTest);
        Assert.assertEquals(performancePage.getIncorrectNumbers(), 0);
    }
}

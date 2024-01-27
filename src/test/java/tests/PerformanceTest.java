package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.PerformancePage;
import pages.PreconditionPage;
import tests.helpers.TestData;
import tests.helpers.TestUtils;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.runner.ProjectProperties.BASE_URL;

public class PerformanceTest extends BaseTest {

    @Test(description = "TC1356-02 - Displaying Statistics for Tests")
    @Description("Objective: To confirm the accurate display of statistics for Tests in the Performance section.")
    @Story("Performance")
    @TmsLink("p0i1q95cgr11")
    public void testDisplayingStatisticsForTests() {
        PreconditionPage precondition = new PreconditionPage(getPage()).init();

        final int numbersOfQuestion = precondition.getNumberOfQuestions();

        precondition
                .startTestDomainForStats(TestData.HISTORY_AND_CIVILIZATION_FOR_STATS, TestData.FOUR_QUESTIONS);
        precondition
                .passTestOneAnswersIncorrect(TestData.FOUR_QUESTIONS);
        precondition
                .startTestDomainForStats(TestData.AUTOMATION_TESTING_FOR_STATS, TestData.FIVE_QUESTIONS);
        precondition
                .passTestAllAnswersCorrect(TestData.FIVE_QUESTIONS);

        PerformancePage performancePage =
                new HomePage(getPage()).init()
                        .clickPerformanceMenu()
                        .clickOverallDropdown()
                        .clickTestsButtonInBanner();

        final String expectedUrl = BASE_URL + TestData.PERFORMANCE_END_POINT;
        final Locator dropDownFilters = performancePage.getSettedFilter();

        assertThat(getPage()).hasURL(expectedUrl);
        assertThat(dropDownFilters).hasText(TestData.ALL_TESTS);

        performancePage
                .clickDropdown();

        final double expectedCorrectPercentage = TestUtils.getPercentageOfNumber(8, numbersOfQuestion);
        final double expectedIncorrectPercentage = TestUtils.getPercentageOfNumber(1, numbersOfQuestion);
        final double actualCorrectPercentage = performancePage.getCorrectPercentage();
        final double actualCorrectNumbers = performancePage.getCorrectNumbers();
        final double actualIncorrectPercentage = performancePage.getIncorrectPercentage();
        final double actualIncorrectNumbers = performancePage.getIncorrectNumbers();

        Assert.assertEquals(actualCorrectPercentage, expectedCorrectPercentage);
        Assert.assertEquals(actualCorrectNumbers, 8);
        Assert.assertEquals(actualIncorrectPercentage, expectedIncorrectPercentage);
        Assert.assertEquals(actualIncorrectNumbers, 1);

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

    @Test(description = "TC1356-01 - The dropdown menu displaying statistics is opened.")
    @Description("Objective: To confirm the display of statistics for Tests in the Performance section.")
    @Story("Performance")
    @TmsLink("nyqyh86yrv3b")
    public void testShowDropdownMenuInPerformanceSection() {
        PerformancePage performancePage =
                new HomePage(getPage()).init()
                        .clickPerformanceMenu();

        final Locator overallButton = performancePage.getOverallButtonInBanner();
        final Locator testsButton = performancePage.getTestsButtonInBanner();
        final Locator allFlashcardsButton = performancePage.getAllFlashcardsButtonInBanner();

        assertThat(overallButton).isHidden();
        assertThat(testsButton).isHidden();
        assertThat(allFlashcardsButton).isHidden();

        performancePage
                .clickOverallDropdown();

        assertThat(overallButton).isVisible();
        assertThat(testsButton).isVisible();
        assertThat(allFlashcardsButton).isVisible();
    }
}


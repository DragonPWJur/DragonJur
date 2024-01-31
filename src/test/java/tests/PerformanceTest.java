package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.PerformancePage;
import pages.PreconditionPage;
import tests.helpers.TestData;
import tests.helpers.TestUtils;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.runner.ProjectProperties.BASE_URL;

public class PerformanceTest extends BaseTest {

    @Test(
            testName = "LMS-1356 Получение статистики по тестам. https://app.qase.io/plan/LMS/1?case=1356",
            description = "ТC1356-02 - Displaying Statistics for Tests."
    )
    @Description("Objective: To confirm the accurate display of statistics for Tests in the Performance section.")
    @Story("Performance")
    @TmsLink("p0i1q95cgr11")
    @Severity(SeverityLevel.NORMAL)
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
                        .clickDropdown()
                        .clickTestsButtonInBanner();

        final String expectedUrl = BASE_URL + TestData.PERFORMANCE_END_POINT;
        final Locator dropDownFilters = performancePage.getDropdownFilter();

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

    @Test(
            testName = "LMS-1356 Получение статистики по тестам. https://app.qase.io/plan/LMS/1?case=1356",
            description = "ТC1356-01 - The dropdown menu displaying statistics is opened."
    )
    @Description("Objective: To confirm the display of statistics for Tests in the Performance section.")
    @Story("Performance")
    @TmsLink("nyqyh86yrv3b")
    @Severity(SeverityLevel.NORMAL)
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
                .clickDropdown();

        assertThat(overallButton).isVisible();
        assertThat(testsButton).isVisible();
        assertThat(allFlashcardsButton).isVisible();
    }

    @Test(
            testName = "LMS-1357 Получение статистики по стопкам. https://app.qase.io/plan/LMS/1?case=1357",
            description = "TC1357-01 - Displaying Statistics for All Flashcards."
    )
    @Description("Objective: To confirm the accurate display of statistics for all flashcards in the Performance section.")
    @Story("Performance")
    @TmsLink("qkc4xrnd166z")
    @Severity(SeverityLevel.NORMAL)
    public void testDisplayingStatisticsForAllFlashcards() {
        new PreconditionPage(getPage())
                .setOptionsYes3No3Kinda3(TestData.STACKS_NAMES);

        PerformancePage performancePage =
                new HomePage(getPage()).init()
                        .clickPerformanceMenu();

        final int numbersOfFlashcards =
                performancePage
                        .getNumberOfFlashcards();

        final Locator overallButton = performancePage.getOverallButtonInBanner();
        final Locator testsButton = performancePage.getTestsButtonInBanner();
        final Locator allFlashcardsButton = performancePage.getAllFlashcardsButtonInBanner();

        assertThat(overallButton).isHidden();
        assertThat(testsButton).isHidden();
        assertThat(allFlashcardsButton).isHidden();

        performancePage
                .clickDropdown();

        assertThat(overallButton).isVisible();
        assertThat(testsButton).isVisible();
        assertThat(allFlashcardsButton).isVisible();

        performancePage
                .clickAllFlashcardsButtonInBanner();

        final double expectedYesPercentage = TestUtils.getPercentageOfNumber(12, numbersOfFlashcards);
        final double expectedNoPercentage = TestUtils.getPercentageOfNumber(12, numbersOfFlashcards);
        final double expectedKindaPercentage = TestUtils.getPercentageOfNumber(12, numbersOfFlashcards);
        final double expectedUnusedPercentage = TestUtils.getPercentageOfNumber(
                numbersOfFlashcards - 36, numbersOfFlashcards
        );

        final double actualYesPercentage = performancePage.getYesPercentageText();
        final double actualYesMarks = performancePage.getYesNumberText();
        final double actualKindaPercentage = performancePage.getKindaPercentageText();
        final double actualKindaMarks = performancePage.getKindaNumberText();
        final double actualNoPercentage = performancePage.getNoPercentageText();
        final double actualNoMarks = performancePage.getNoNumberText();
        final double actualUnusedPercentage = performancePage.getUnusedPercentageText();
        final double actualUnusedMarks = performancePage.getUnusedNumberText();

        Assert.assertEquals(actualYesPercentage, expectedYesPercentage);
        Assert.assertEquals(actualYesMarks, 12);
        Assert.assertEquals(actualKindaPercentage, expectedKindaPercentage);
        Assert.assertEquals(actualKindaMarks, 12);
        Assert.assertEquals(actualNoPercentage, expectedNoPercentage);
        Assert.assertEquals(actualNoMarks, 12);
        Assert.assertEquals(actualUnusedPercentage, expectedUnusedPercentage);
        Assert.assertEquals(actualUnusedMarks, numbersOfFlashcards - 36);

        for (Locator stack : performancePage.getStacks()) {

            List<Integer> stacksStatistic = TestUtils.getStatistic(stack);
            int sum = TestUtils.getSum(stacksStatistic);

            Assert.assertTrue(stacksStatistic.size() == 3 || stacksStatistic.size() == 4);
            Assert.assertEquals(sum, 100);

            assertThat(stack).isVisible();
        }
    }
}


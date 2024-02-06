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

    @Severity(SeverityLevel.NORMAL)
    @Story("Performance")
    @TmsLink("nyqyh86yrv3b")
    @Description("LMS-1356 Получение статистики по тестам. https://app.qase.io/plan/LMS/1?case=1356" +
            "  Objective: To confirm the display of statistics for Tests in the Performance section.")
    @Test(description = "ТC1356-01 - The dropdown filter menu has filter options.")
    public void testShowDropdownMenuInPerformanceSection() {
        PerformancePage performancePage =
                new HomePage(getPage()).init()
                        .clickPerformanceMenu();

        final Locator overallButton = performancePage.getOverallButtonInBanner();
        final Locator testsButton = performancePage.getTestsButtonInBanner();
        final Locator allFlashcardsButton = performancePage.getAllFlashcardsButtonInBanner();

        Allure.step("Assert that the 'Overall' button is hidden.");
        assertThat(overallButton).isHidden();

        Allure.step("Assert that the 'Tests' button is hidden.");
        assertThat(testsButton).isHidden();

        Allure.step("Assert that the 'All Flashcards' button is hidden.");
        assertThat(allFlashcardsButton).isHidden();

        performancePage
                .clickDropdown();

        Allure.step("Assert that the 'Overall' button is visible.");
        assertThat(overallButton).isVisible();

        Allure.step("Assert that the 'Tests' button is visible.");
        assertThat(testsButton).isVisible();

        Allure.step("Assert that the 'All Flashcards' button is visible.");
        assertThat(allFlashcardsButton).isVisible();
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Performance")
    @TmsLink("p0i1q95cgr11")
    @Description("LMS-1356 Получение статистики по тестам. https://app.qase.io/plan/LMS/1?case=1356"
            + "   Objective: To confirm the accurate display of statistics for Tests in the Performance section.")
    @Test(description = "ТC1356-02 - Displaying Statistics for Tests.")
    public void testDisplayingStatisticsForTests() {
        PreconditionPage precondition = new PreconditionPage(getPage()).init();

        final int numberOfQuestions = precondition.getNumberOfQuestions();

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

        Allure.step("Assert that the page url part as expected (" + TestData.PERFORMANCE_END_POINT + ").");
        assertThat(getPage()).hasURL(expectedUrl);

        Allure.step("Assert that the dropdown filter menu has '" + TestData.ALL_TESTS + "' option.");
        assertThat(dropDownFilters).hasText(TestData.ALL_TESTS);

        performancePage
                .clickDropdown();

        Allure.step("Collect statistics.");
        final double expectedCorrectPercentage = TestUtils.getPercentageOfNumber(8, numberOfQuestions);
        final double expectedIncorrectPercentage = TestUtils.getPercentageOfNumber(1, numberOfQuestions);
        final double actualCorrectPercentage = performancePage.getCorrectPercentage();
        final double actualCorrectNumbers = performancePage.getCorrectNumbers();
        final double actualIncorrectPercentage = performancePage.getIncorrectPercentage();
        final double actualIncorrectNumbers = performancePage.getIncorrectNumbers();

        Allure.step("Assert that " + actualCorrectPercentage + " equals " + expectedCorrectPercentage + ".");
        Assert.assertEquals(actualCorrectPercentage, expectedCorrectPercentage);

        Allure.step("Assert that " + actualCorrectNumbers + " equals " + 8 + ".");
        Assert.assertEquals(actualCorrectNumbers, 8);

        Allure.step("Assert that " + actualIncorrectPercentage + " equals " + expectedIncorrectPercentage + ".");
        Assert.assertEquals(actualIncorrectPercentage, expectedIncorrectPercentage);

        Allure.step("Assert that " + actualIncorrectNumbers + " equals " + 1 + ".");
        Assert.assertEquals(actualIncorrectNumbers, 1);

        performancePage
                .clickDropdown()
                .setLastTest()
                .clickDropdown();

        Allure.step("Collect statistics.");
        double expectedCorrectPercentageForTest = TestUtils.getPercentageOfNumber(5, 5);
        double expectedIncorrectPercentageForTest = TestUtils.getPercentageOfNumber(0, 5);
        final double aCorrectPercentage = performancePage.getCorrectPercentage();
        final double aCorrectNumbers = performancePage.getCorrectNumbers();
        final double aIncorrectPercentageForTest = performancePage.getIncorrectPercentage();
        final double aIncorrectNumbers = performancePage.getIncorrectNumbers();

        Allure.step("Assert that aCorrectPercentage equals expectedCorrectPercentageForTest");
        Assert.assertEquals(aCorrectPercentage, expectedCorrectPercentageForTest);

        Allure.step("Assert that aCorrectNumbers equals 5");
        Assert.assertEquals(aCorrectNumbers, 5);

        Allure.step("Assert that aIncorrectPercentageForTest equals expectedIncorrectPercentageForTest");
        Assert.assertEquals(aIncorrectPercentageForTest, expectedIncorrectPercentageForTest);

        Allure.step("Assert that aIncorrectNumbers equals 0");
        Assert.assertEquals(aIncorrectNumbers, 0);
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Performance")
    @TmsLink("qkc4xrnd166z")
    @Description("LMS-1357 Получение статистики по стопкам. https://app.qase.io/plan/LMS/1?case=1357"
            + "   Objective: To confirm the accurate display of statistics for all flashcards in the Performance section.")
    @Test(description = "TC1357-01 - Displaying Statistics for All Flashcards.")
    public void testDisplayingStatisticsForAllFlashcards() {
        new PreconditionPage(getPage())
                .setOptionsYes3No3Kinda3(TestData.STACKS_NAMES);

        PerformancePage performancePage =
                new HomePage(getPage()).init()
                        .clickPerformanceMenu();

        final int numbersOfFlashcards = performancePage.getNumberOfFlashcards();

        performancePage
                .clickDropdown()
                .clickAllFlashcardsButtonInBanner();

        final double expectedYesPercentage = TestUtils.getPercentageOfNumber(12, numbersOfFlashcards);
        final double expectedNoPercentage = TestUtils.getPercentageOfNumber(12, numbersOfFlashcards);
        final double expectedKindaPercentage = TestUtils.getPercentageOfNumber(12, numbersOfFlashcards);
        final double expectedUnusedPercentage = TestUtils.getPercentageOfNumber(numbersOfFlashcards - 36, numbersOfFlashcards);
        final double actualYesPercentage = performancePage.getYesPercentageText();
        final double actualYesMarks = performancePage.getYesNumberText();
        final double actualKindaPercentage = performancePage.getKindaPercentageText();
        final double actualKindaMarks = performancePage.getKindaNumberText();
        final double actualNoPercentage = performancePage.getNoPercentageText();
        final double actualNoMarks = performancePage.getNoNumberText();
        final double actualUnusedPercentage = performancePage.getUnusedPercentageText();
        final double actualUnusedMarks = performancePage.getUnusedNumberText();

        Allure.step("Assert that actual 'Yes' cards percentage (" + actualYesPercentage
                + ") is equals to expected 'Yes' cards percentage (" + expectedYesPercentage + ").");
        Assert.assertEquals(actualYesPercentage, expectedYesPercentage);

        Allure.step("Assert that amount of marked 'Yes' cards (" + actualYesMarks + ") equals 12");
        Assert.assertEquals(actualYesMarks, 12);

        Allure.step("Assert that actual 'Kinda' cards percentage (" + actualKindaPercentage
                + ") is equals to expected 'Kinda' cards percentage (" + expectedKindaPercentage + ").");
        Assert.assertEquals(actualKindaPercentage, expectedKindaPercentage);

        Allure.step("Assert that amount of marked 'Kinda' cards (" + actualKindaMarks + ") equals 12");
        Assert.assertEquals(actualKindaMarks, 12);

        Allure.step("Assert that actual 'No' cards percentage (" + actualNoPercentage
                + ") is equals to expected 'No' cards percentage (" + expectedNoPercentage + ").");
        Assert.assertEquals(actualNoPercentage, expectedNoPercentage);

        Allure.step("Assert that amount of marked 'No' cards (" + actualNoMarks + ") equals 12");
        Assert.assertEquals(actualNoMarks, 12);

        Allure.step("Assert that actual 'Unused' cards percentage (" + actualUnusedPercentage
                + ") is equals to expected 'Unused' cards percentage (" + expectedUnusedPercentage + ").");
        Assert.assertEquals(actualUnusedPercentage, expectedUnusedPercentage);

        Allure.step("Assert that amount of 'Unused' cards (" + actualUnusedMarks + ") equals 36");
        Assert.assertEquals(actualUnusedMarks, numbersOfFlashcards - 36);

        for (Locator pack : performancePage.getPacks()) {
            Allure.step("Assert that statistics for pack is visible");
            assertThat(pack).isVisible();

            List<Integer> packStatistic = TestUtils.getStatistic(pack);
            final int sum = TestUtils.getSum(packStatistic);

            Allure.step("Assert that statistics parts for pack (" + packStatistic.size() + ") equals 4.");
            Assert.assertEquals(packStatistic.size(), 4);

            Allure.step("Assert that sum of  parts (" + sum + ") equals 100%");
            Assert.assertEquals(sum, 100);
        }
    }
}


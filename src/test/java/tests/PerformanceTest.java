package tests;


import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PerformanceTest extends BaseTest {
//    @Test(description = "TC1356-02 - Displaying Statistics for Tests")
//    @Description("Objective: To confirm the accurate display of statistics for Tests in the Performance section.")
//    @Story("Performance")
//    @TmsLink("p0i1q95cgr11")
//    public void testDisplayingStatisticsForTests() {
//        PreconditionPage preconditionPage = new PreconditionPage(getPage(), getPlaywright());
//        int numbersOfQuestion = preconditionPage.checkNumberOfQuestions();
//
//        preconditionPage.startTestDomainForStats("History and Civilization for Stats","4");
//        preconditionPage.passTestOneAnswersIncorrect(4);
//        preconditionPage.startTestDomainForStats("Automation testing for stats","5");
//        preconditionPage.passTestAllAnswersCorrect(5);
//
//        PerformancePage performancePage = new HomePage(getPage(), getPlaywright())
//                .clickPerformanceMenu()
//                .clickOverallDropdown()
//                .clickTestsButtonInBanner();
//
//        assertThat(getPage()).hasURL(BASE_URL + TestData.PERFORMANCE_END_POINT);
//        assertThat(performancePage.getSettedFilter()).hasText("All tests");
//
//        performancePage.clickDropdown();
//
//        double expectedCorrectPercentage = TestUtils.getPercentageOfNumber(8, numbersOfQuestion);
//        double expectedIncorrectPercentage = TestUtils.getPercentageOfNumber(1, numbersOfQuestion);
//
//        Assert.assertEquals(performancePage.getCorrectPercentage(), expectedCorrectPercentage);
//        Assert.assertEquals(performancePage.getCorrectNumbers(), 8);
//        Assert.assertEquals(performancePage.getIncorrectPercentage(), expectedIncorrectPercentage);
//        Assert.assertEquals(performancePage.getIncorrectNumbers(), 1);
//
//        performancePage
//                .clickDropdown()
//                .setLastTest()
//                .clickDropdown();
//
//        double expectedCorrectPercentageForTest = TestUtils.getPercentageOfNumber(5, 5);
//        double expectedIncorrectPercentageForTest = TestUtils.getPercentageOfNumber(0, 5);
//
//        Assert.assertEquals(performancePage.getCorrectPercentage(), expectedCorrectPercentageForTest);
//        Assert.assertEquals(performancePage.getCorrectNumbers(), 5);
//        Assert.assertEquals(performancePage.getIncorrectPercentage(), expectedIncorrectPercentageForTest);
//        Assert.assertEquals(performancePage.getIncorrectNumbers(), 0);
//    }
//
//    @Test(description = "TC1356-01 - The dropdown menu displaying statistics is opened.")
//    @Description("Objective: To confirm the display of statistics for Tests in the Performance section.")
//    @Story("Performance")
//    @TmsLink("nyqyh86yrv3b")
//
//    public void testShowDropdownMenuInPerformanceSection() {
//        PerformancePage performancePage = new PerformancePage(getPage(), getPlaywright()).clickPerformanceMenu();
//
//        assertThat(performancePage.getOverallButtonInBanner()).isHidden();
//        assertThat(performancePage.getTestsButtonInBanner()).isHidden();
//        assertThat(performancePage.getAllFlashcardsButtonInBanner()).isHidden();
//
//        performancePage.clickOverallDropdown();
//
//        assertThat(performancePage.getOverallButtonInBanner()).isVisible();
//        assertThat(performancePage.getTestsButtonInBanner()).isVisible();
//        assertThat(performancePage.getAllFlashcardsButtonInBanner()).isVisible();
//    }
}


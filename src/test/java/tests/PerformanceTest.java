package tests;

import org.testng.annotations.Test;
import pages.HomePage;
import pages.PerformancePage;
import utils.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.ProjectProperties.BASE_URL;

public class PerformanceTest extends BaseTest {
    @Test
    //add precondition for this test
    public void testDisplayingStatisticsInDropdownMenu() {
        PerformancePage performancePage = new HomePage(getPage(), getPlaywright())
                .clickPerformanceMenu()
                .clickOverallDropdown();

        assertThat(getPage()).hasURL(BASE_URL + TestData.PERFORMANCE_END_POINT);
    }
}

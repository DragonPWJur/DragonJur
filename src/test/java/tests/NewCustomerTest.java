package tests;

import io.qameta.allure.*;

import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;
import tests.helpers.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class NewCustomerTest extends BaseTest {

    @Test(
            testName = "LMS-1343 Отработка streaks. https://app.qase.io/plan/LMS/1?case=1343",
            description = "TC1343-02 - Verification of Text in the 'Streaks' Modal Window")
    @Description("Objective: To confirm that the user can view points greater than 0 and the text indicating the number of streak days in the modal window.")
    @Story("Home Page")
    @TmsLink("j0y70alubidi")
    @Severity(SeverityLevel.NORMAL)
    public void testStreaksModalWindowTextVerification() {
        HomePage homePage =
                new HomePage(getPage()).init()
                        .clickHomeMenu();

        assertThat(homePage.getStreaksButton()).hasText("0");

        final int randomIndex = homePage.getRandomIndex();

        homePage
                .clickNthCheckbox(randomIndex);

        assertThat(homePage.getNthCheckbox(randomIndex)).isChecked();
        assertThat(homePage.getStreaksButton()).hasText("1");

        homePage
                .clickStreaksButton();

        assertThat(homePage.getStreakDaysModalWindowTextLocator()).hasText(TestData.ONE_DAY_STUDY_STREAK_MESSAGE);
        Assert.assertTrue(
                homePage.getMainSectionPoints() > 0,
                "If FAIL: Expected result 'Upon opening the modal window, points greater than 0 are visible' is not reached.");
    }
}

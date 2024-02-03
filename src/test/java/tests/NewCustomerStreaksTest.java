package tests;

import io.qameta.allure.*;

import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;
import tests.helpers.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class NewCustomerStreaksTest extends BaseTest {

    @Severity(SeverityLevel.NORMAL)
    @Story("Streaks")
    @TmsLink("j0y70alubidi")
    @Description("LMS-1343 Отработка streaks. https://app.qase.io/plan/LMS/1?case=1343" +
            "Objective: To confirm that the user can view points greater than 0 and the " +
            "text indicating the number of streak days in the modal window.")
    @Test(description = "TC1343-02 - Verification of Text in the 'Streaks' Modal Window")
    public void testStreaksModalWindowTextVerification() {
        HomePage homePage =
                new HomePage(getPage()).init()
                        .clickHomeMenu();

        Allure.step("Assert that 'Streaks' button has text '0'.");
        assertThat(homePage.getStreaksButton()).hasText("0");

        final int randomIndex = homePage.getRandomIndex();

        homePage
                .clickNthCheckbox(randomIndex);

        Allure.step("Assert that random checkbox is checked.");
        assertThat(homePage.getNthCheckbox(randomIndex)).isChecked();

        Allure.step("Assert that 'Streaks' button has text '1'.");
        assertThat(homePage.getStreaksButton()).hasText("1");

        homePage
                .clickStreaksButton();

        final int mainSectionPoints = homePage.getMainSectionPoints();

        Allure.step("Assert that 'Streaks days' modal has text '" + TestData.ONE_DAY_STUDY_STREAK_MESSAGE + "'.");
        assertThat(homePage.getStreakDaysModalWindowTextLocator()).hasText(TestData.ONE_DAY_STUDY_STREAK_MESSAGE);

        Allure.step("Assert that points on the main section are greater then 0.");
        Assert.assertTrue(mainSectionPoints > 0,
                "If FAIL: Expected result 'Upon opening the modal window, points greater than 0 are visible' is not reached.");
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Streaks")
    @TmsLink("9rsd8ecq6x5y")
    @Description("LMS-1343 Отработка streaks. https://app.qase.io/plan/LMS/1?case=1343"
            + "   Objective: To verify that the modal window will be opened after clicking on the “Streaks” button.")
    @Test(description = "TC1343-01 - Verification “Streaks” modal window appears.")
    public void testStreaksModalWindowIsAppeared() {
        HomePage homePage =
                new HomePage(getPage()).init()
                        .clickHomeMenu()
                        .clickStreaksButton();

        Allure.step("Assert that the modal window is opened");
        assertThat(homePage.getStreaksModalWindow()).isVisible();
    }
}

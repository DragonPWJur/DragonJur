package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.TestTimedPage;
import tests.helpers.TestData;
import utils.runner.ProjectProperties;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public final class TestTimedTest extends BaseTest {

    @Severity(SeverityLevel.NORMAL)
    @Story("Tests")
    @TmsLink("k0bfbra127ay")
    @Description("LMS-1345 Запуск timed. https://app.qase.io/case/LMS-1345"
            + "  Objective: To verify that the user can select section Tests and activate the 'Timed' mode and run the test.")
    @Test(description = "TC1345-01 - Execute Timed Mode with a randomly selected test")
    public void testRunTimedMode() {
        TestTimedPage testTimedPage =
                new HomePage(getPage()).init()
                        .clickTestsMenu()
                        .cancelDialogIfVisible()
                        .clickTimedButton()
                        .clickGenerateAndStartTimedTestButton()
                        .clickStartTestButton()
                        .clickStartButton();

        final String expectedUrl = ProjectProperties.BASE_URL + TestData.TEST_TIMED_END_POINT;
        final Locator timer = testTimedPage.getTimer();
        final Locator testQuestion = testTimedPage.getTestQuestion();
        final List<Locator> testAnswers = testTimedPage.getRadioButtons();

        Allure.step("Assert that page url has part of expected url '" + TestData.TEST_TIMED_END_POINT + "'.");
        assertThat(getPage()).hasURL(expectedUrl);

        Allure.step("Assert that Timer element is visible.");
        assertThat(timer).isVisible();

        Allure.step("Assert that " + testQuestion + " contains the '?'.");
        assertThat(testQuestion).containsText(TestData.QUESTION_MARK);

        Allure.step("Assert that the timed test has at least the 1 question.");
        Assert.assertTrue(
                testAnswers.size() >= 1,
                "If FAIL: The multiple-choice test should contain at least one answer.\n"
        );
    }
}
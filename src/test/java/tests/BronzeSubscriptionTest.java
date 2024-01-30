package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;
import pages.TestListPage;
import pages.TestTutorPage;
import tests.helpers.TestData;
import utils.api.APIUtils;

import java.lang.reflect.Method;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import static utils.runner.ProjectProperties.BASE_URL;

public class BronzeSubscriptionTest extends BaseTest {

    @BeforeMethod
    void activateRequiredCourse(Method method) {
        APIUtils.activateBronzeSubscriptionCourse(getPlaywright());
        getPage().reload();
    }

    @Test(
            testName = "Verify the active course is on the Bronze plan."
    )
    @Description("To ensure that a user account has an active Bronze subscription course.")
    @Story("Tests")
    @Severity(SeverityLevel.BLOCKER)
    public void testBronzeSubscriptionCourseShouldBeActive() {
        Assert.assertEquals(
                APIUtils.getActiveCourseId(getPlaywright()), APIUtils.BRONZE_SUBSCRIPTION_ID,
                "If FAIL: Active course id does NOT match the Bronze subscription id.\n"
        );
    }

    @Test(
            testName = "LMS-1369 Доступность для юзера доменов в тестах. Valid. https://app.qase.io/case/LMS-1369",
            description = "TC1369-01 - Running Tests with Available Questions Amount for Domain Under Partial Test Access",
            dependsOnMethods = {"testBronzeSubscriptionCourseShouldBeActive"})
    @Description("To ensure the user can start a test only if the number of questions they enter " +
            "is within the available question count.")
    @Story("Tests")
    @TmsLink("th3tah60ic6k")
    @Severity(SeverityLevel.NORMAL)
    public void testRunDomainsTestsUnderPartialAccess() {
        TestTutorPage testTutorPage =
                new HomePage(getPage()).init()
                        .clickTestsMenu()
                        .clickDomainsButtonIfNotActive()
                        .clickRandomAvailableCheckbox()
                        .clickTutorButton()
                        .inputRandomNumberOfQuestions()
                        .clickGenerateAndStartTutorTestButton();

        final Locator testQuestion = testTutorPage.getTestQuestion();
        final int testAnswersCount = testTutorPage.countAnswers();

        assertThat(getPage()).hasURL(BASE_URL + TestData.TEST_TUTOR_END_POINT);
        assertThat(testQuestion).isVisible();
        assertThat(testQuestion).containsText(TestData.QUESTION_MARK);
        Assert.assertTrue(testAnswersCount >= 1);
    }

    @Test(
            testName = "LMS-1371 Доступность для юзера чаптеров в тестах. Valid. https://app.qase.io/case/LMS-1371",
            description = "TC1371-01 - Running Tests with Available Question Amount for Chapter Under Partial Test Access",
            dependsOnMethods = {"testBronzeSubscriptionCourseShouldBeActive"})
    @Description("To verify the user's ability to start a test when the entered number of questions does not exceed "
            + "the available question count in the test")
    @Story("Tests")
    @TmsLink("os387mfeov9")
    @Severity(SeverityLevel.NORMAL)
    public void testRunChapterTestsUnderPartialAccess() {
        TestTutorPage testTutorPage =
                new HomePage(getPage()).init()
                        .clickTestsMenu()
                        .clickChaptersButton()
                        .clickRandomAvailableCheckbox()
                        .clickTutorButton()
                        .inputRandomNumberOfQuestions()
                        .clickGenerateAndStartTutorTestButton();

        final Locator testQuestion = testTutorPage.getTestQuestion();
        final int testAnswersCount = testTutorPage.countAnswers();

        assertThat(getPage()).hasURL(BASE_URL + TestData.TEST_TUTOR_END_POINT);
        assertThat(testQuestion).isVisible();
        assertThat(testQuestion).containsText(TestData.QUESTION_MARK);
        Assert.assertTrue(testAnswersCount >= 1);
    }

    @Test(testName = "LMS-1370 Доступность для юзера чаптеров в тестах. Invalid. https://app.qase.io/case/LMS-1370",
            description = "TC1370-01 - User can’t run tests for Chapter if the entered amount of questions exceeds the actual amount of questions in the test",
            dependsOnMethods = {"testBronzeSubscriptionCourseShouldBeActive"})
    @Description("To verify that the user cannot run tests if the entered amount of questions exceeds the actual number of questions in the test section.")
    @Story("Tests")
    @TmsLink(".y3phtxjw1pu")
    @Severity(SeverityLevel.NORMAL)
    public void testChapterTestLimitValidation() {
        TestListPage testListPage =
                new HomePage(getPage()).init()
                        .clickTestsMenu()
                        .clickChaptersButton()
                        .clickRandomAvailableCheckbox()
                        .clickTutorButton()
                        .inputGreaterBy1NumberOfQuestions()
                        .clickGenerateAndStartTimedTestButton();

        final Locator alertNotEnoughQuestions = testListPage.getTostifyAlert();
        final String alertMessage = testListPage.getTestifyAlertMessage();

        assertThat(getPage()).hasURL(BASE_URL + TestData.TEST_LIST_END_POINT);
        assertThat(alertNotEnoughQuestions).isVisible();
        Assert.assertEquals(alertMessage,TestData.ALERT_NOT_ENOUGH_QUESTIONS);
    }
}

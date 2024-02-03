package tests;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.TestListPage;
import pages.TestTutorPage;
import tests.helpers.TestData;
import utils.api.APIData;
import utils.api.APIUtils;

import java.lang.reflect.Method;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.runner.ProjectProperties.BASE_URL;

public class BronzeSubscriptionTest extends BaseTest {

    @BeforeMethod
    void activateBronzeSubscriptionCourse(Method method) {
        APIUtils.activateBronzeSubscriptionCourse();
        getPage().reload();
    }

    @Severity(SeverityLevel.BLOCKER)
    @Story("Tests Bronze")
    @TmsLink("1qc5vbaf2frd")
    @Description("LMS-1369, LMS-1370, LMS-1371, LMS-1346 Pretest for Bronze subscription. " +
            "To verify that a user account has an active Bronze subscription course.\n")
    @Test(description = "TC1369-00 - Verify if the active course is on the Bronze plan.")
    public void testBronzeSubscriptionCourseShouldBeActive() {

        Allure.step("Assert that Active course id equals Bronze subscription course id.");
        Assert.assertEquals(
                APIUtils.getActiveCourseId(), APIData.BRONZE_SUBSCRIPTION_ID,
                "If FAIL: Active course id does NOT match the Bronze subscription id.\n"
        );
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Tests Bronze")
    @TmsLink("th3tah60ic6k")
    @Description("LMS-1369 Доступность для юзера доменов в тестах. Valid. https://app.qase.io/case/LMS-1369 ." +
            "To ensure the user can start a domains tests only if the number of questions they enter " +
            "is within the available question count.")
    @Test(description = "TC1369-01 - Running Domains tests under a Bronze subscription with partial test access. Valid.",
            dependsOnMethods = {"testBronzeSubscriptionCourseShouldBeActive"})
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

        Allure.step("Assert that the user is redirected to the page with the endpoint “/test-tutor”.");
        assertThat(getPage()).hasURL(BASE_URL + TestData.TEST_TUTOR_END_POINT);

        Allure.step("Assert that the question text is visible.");
        assertThat(testQuestion).isVisible();

        Allure.step("Assert that the question text contains the '?' mark.");
        assertThat(testQuestion).containsText(TestData.QUESTION_MARK);

        Allure.step("Assert that the test has started, displaying at least one radio button (answer) on the page.");
        Assert.assertTrue(testAnswersCount >= 1);
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Tests Bronze")
    @TmsLink("os387mfeov9")
    @Description("LMS-1371 Доступность для юзера чаптеров в тестах. Valid. https://app.qase.io/case/LMS-1371 . " +
            "To verify the user's ability to start a chapters tests when the entered number of questions does not exceed "
            + "the available questions in the test")
    @Test(description = "TC1371-01 - Running Chapters tests under a Bronze subscription with partial test access. Valid.",
            dependsOnMethods = {"testBronzeSubscriptionCourseShouldBeActive"})
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

        Allure.step("Assert that the user is redirected to the page with the endpoint “/test-tutor”.");
        assertThat(getPage()).hasURL(BASE_URL + TestData.TEST_TUTOR_END_POINT);

        Allure.step("Assert that the question text is visible.");
        assertThat(testQuestion).isVisible();

        Allure.step("Assert that the question text contains the '?' mark.\n.");
        assertThat(testQuestion).containsText(TestData.QUESTION_MARK);

        Allure.step("Assert that the test has started, displaying at least one radio button (answer) on the page.");
        Assert.assertTrue(testAnswersCount >= 1);
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Tests Bronze")
    @TmsLink("y3phtxjw1pu")
    @Description("LMS-1370 Доступность для юзера чаптеров в тестах. Invalid. https://app.qase.io/case/LMS-1370 " +
            "To verify that the user cannot run tests if the entered amount of questions exceeds the actual " +
            "number of questions in the test section.")
    @Test(description = "TC1370-01 - User can’t run Chapters tests if the entered amount of questions " +
            "exceeds the available amount of questions.",
            dependsOnMethods = {"testBronzeSubscriptionCourseShouldBeActive"})
    public void testChapterTestLimitValidation() {
        TestListPage testListPage =
                new HomePage(getPage()).init()
                        .clickTestsMenu()
                        .clickChaptersButton()
                        .clickRandomAvailableCheckbox()
                        .clickTutorButton()
                        .inputGreaterBy1NumberOfQuestions()
                        .clickGenerateAndStartTestButton();

        final Locator alertNotEnoughQuestions = testListPage.getTostifyAlert();
        final String alertMessage = testListPage.getTestifyAlertMessage();

        Allure.step("Assert that the user stays on the page with the endpoint '/test-tutor'.");
        assertThat(getPage()).hasURL(BASE_URL + TestData.TEST_LIST_END_POINT);

        Allure.step("Assert that a toaster message is displayed.");
        assertThat(alertNotEnoughQuestions).isVisible();

        Allure.step("Assert a toaster message should display: " +
                "'There are not enough questions in the sections that you have chosen.'");
        Assert.assertEquals(alertMessage, TestData.ALERT_NOT_ENOUGH_QUESTIONS);

        Allure.step("Assert that tests were not initiated. No answers are shown on the page.");
        assertThat(getPage().getByRole(AriaRole.RADIO)).not().isVisible();
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Tests Bronze")
    @TmsLink("hdrouzq57h1v")
    @Description("LMS-1346 Доступность для юзера доменов в тестах. Invalid. https://app.qase.io/case/LMS-1346  " +
            "To verify that the user cannot run tests if the entered amount of questions exceeds the actual number " +
            "of questions in the test section.")
    @Test(description = "TC1346-01 - User can’t run Domains tests if requested amount of questions exceeds the " +
            "available amount of questions in the test",
            dependsOnMethods = {"testBronzeSubscriptionCourseShouldBeActive"})
    public void testNotRunDomainsTestsLimitValidation() {
        TestListPage testListPage =
                new HomePage(getPage()).init()
                        .clickTestsMenu()
                        .clickDomainsButtonIfNotActive()
                        .clickRandomAvailableCheckbox()
                        .clickTutorButton()
                        .inputGreaterBy1NumberOfQuestions()
                        .clickGenerateAndStartTestButton();

        final Locator alertNotEnoughQuestions = testListPage.getTostifyAlert();
        final String alertMessage = testListPage.getTestifyAlertMessage();

        Allure.step("Assert that the user stays on the page with the endpoint '/test-tutor'.");
        assertThat(getPage()).hasURL(BASE_URL + TestData.TEST_LIST_END_POINT);

        Allure.step("Assert that a toaster message is displayed.");
        assertThat(alertNotEnoughQuestions).isVisible();

        Allure.step("Assert a toaster message should display: " +
                "'There are not enough questions in the sections that you have chosen.'");
        Assert.assertEquals(alertMessage, TestData.ALERT_NOT_ENOUGH_QUESTIONS);

        Allure.step("Assert that tests were not initiated. No answers are shown on the page.");
        assertThat(getPage().getByRole(AriaRole.RADIO)).not().isVisible();
    }
}

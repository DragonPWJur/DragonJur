package tests;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.HomePage;
import pages.TestListPage;
import pages.TestTutorPage;
import tests.helpers.TestData;
import utils.api.APIServices;
import utils.reports.ReportUtils;
import utils.reports.TracingUtils;
import utils.runner.BrowserManager;
import utils.runner.ProjectProperties;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.reports.LoggerUtils.logInfo;
import static utils.runner.ProjectProperties.BASE_URL;

public class BronzeSubscriptionTest extends BaseTest {

    private Page classPage;
    private BrowserContext context;

    @Override
    protected Page getPage() {
        return classPage;
    }

    @Override
    @BeforeMethod
    protected void createContextAndPage(Method method) {
        APIServices.bronzeCourseSubscription(getPlaywright());
        logInfo("Run " + ReportUtils.getTestMethodName(method));

        APIServices.cleanData(getPlaywright());
        logInfo("API: Course data cleared");

        context = BrowserManager.createContextWithCookies(getBrowser());
        logInfo("Context created");

        TracingUtils.startTracing(context);
        logInfo("Tracing started");

        classPage = context.newPage();
        logInfo("Page created");

        classPage.navigate(ProjectProperties.BASE_URL);
        logInfo("Base URL opened");
    }

    @Override
    @AfterMethod
    protected void closeContext(Method method, ITestResult testResult) throws IOException {
        ReportUtils.logTestStatistic(method, testResult);
        ReportUtils.addScreenshotToAllureReportForCIFailure(classPage, testResult);

        classPage.close();
        logInfo("Page closed");

        TracingUtils.stopTracing(classPage, context, method, testResult);
        logInfo("Tracing stopped");

        ReportUtils.addVideoAndTracingToAllureReportForCIFailure(method, testResult);

        context.close();
        logInfo("Context closed");

        APIServices.setActiveCourse(getPlaywright(), APIServices.GOLD_SUBSCRIPTION);
        logInfo("The End" + ReportUtils.getEndLine());
    }

    @Test
    public void testIsBronzeActiveCourse() {
        Assert.assertEquals(APIServices.getIdActiveCourse(getPlaywright()), APIServices.BRONZE_SUBSCRIPTION);
    }

    @Test(
            testName = "LMS-1369 Доступность для юзера доменов в тестах. Valid. https://app.qase.io/case/LMS-1369",
            description = "TC1369-01 - Running Tests with Available Question Amount for Domain Under Partial Test Access",
            dependsOnMethods = {"testIsBronzeActiveCourse"})
    @Description("To verify the user's ability to start a test when the entered number of questions does not exceed "
            + "the available question count in the test.")
    @Story("Tests")
    @TmsLink("th3tah60ic6k")
    public void testRunTestsForDomainUnderPartialTestAccess() {
        TestListPage testListPage = new HomePage(getPage()).init()
                .clickTestsMenu()
                .clickDomainsButtonIfNotActive();

        int maxNumberOfQuestions = testListPage.clickRandomActiveCheckboxAndReturnNumberOfQuestions();

        TestTutorPage testTutorPage = testListPage
                .clickTutorButton()
                .inputRandomNumberOfQuestions(maxNumberOfQuestions)
                .clickGenerateAndStartButtonTutor();

        assertThat(getPage()).hasURL(BASE_URL + TestData.TEST_TUTOR_END_POINT);
        assertThat(testTutorPage.getTestQuestion()).containsText(TestData.QUESTION_MARK);
        Assert.assertTrue(testTutorPage.countAnswersRadioButtons() >= 1);
    }

    @Test(
            testName = "LMS-1371 Доступность для юзера чаптеров в тестах. Valid. https://app.qase.io/case/LMS-1371",
            description = "TC1371-01 - Running Tests with Available Question Amount for Chapter Under Partial Test Access",
            dependsOnMethods = {"testIsBronzeActiveCourse"})
    @Description("To verify the user's ability to start a test when the entered number of questions does not exceed "
            + "the available question count in the test")
    @Story("Tests")
    @TmsLink("os387mfeov9")
    public void testRunTestsForChapterUnderPartialTestAccess() {
        TestListPage testListPage = new HomePage(getPage()).init()
                .clickTestsMenu()
                .clickChaptersButton();

        int maxNumberOfQuestions = testListPage
                .clickRandomActiveCheckboxAndReturnNumberOfQuestions();

        TestTutorPage testTutorPage = testListPage
                .clickTutorButton()
                .inputRandomNumberOfQuestions(maxNumberOfQuestions)
                .clickGenerateAndStartButtonTutor();

        assertThat(getPage()).hasURL(BASE_URL + TestData.TEST_TUTOR_END_POINT);
        assertThat(testTutorPage.getTestQuestion()).containsText(TestData.QUESTION_MARK);
        Assert.assertTrue(testTutorPage.countAnswersRadioButtons() >= 1);
    }
}

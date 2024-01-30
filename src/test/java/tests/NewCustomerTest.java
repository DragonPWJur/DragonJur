package tests;

import com.microsoft.playwright.*;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.HomePage;
import pages.PreconditionPage;
import tests.helpers.TestData;
import utils.api.APIServices;
import utils.reports.LoggerInfo;
import utils.reports.ReportUtils;
import utils.reports.TracingUtils;
import utils.runner.BrowserManager;
import utils.runner.LoginUtils;
import utils.runner.ProjectProperties;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.reports.LoggerUtils.*;

public class NewCustomerTest extends BaseTest {
    private Page classPage;
    private BrowserContext context;

    @Override
    protected Page getPage() {
        return classPage;
    }

    @Override
    @BeforeSuite
    void launchBrowser(ITestContext testContext) {

        APIServices.sendCustomerInvite(getPlaywright());
        logInfo("API: New customer invitation was sent");

        APIServices.confirmCustomerInvite(getPlaywright());
        logInfo("API: Invite confirmation received");

        LoginUtils.loginAndCollectCookies(LoginUtils.NEW_CUSTOMER_COOKIES_FILE_PATH, APIServices.getNewCustomerUsername(), APIServices.getNewCustomerPassword());

        logInfo(ReportUtils.getReportHeader());

        if (getPlaywright() != null) {
            logInfo("Playwright " + LoggerInfo.getPlaywrightId(getPlaywright()) + " created.");
        } else {
            logFatal("FATAL: Playwright is NOT created\n");
            System.exit(1);
        }

        if (getBrowser().isConnected()) {
            logInfo("Browser " + getBrowser().browserType().name().toUpperCase() + " "
                    + LoggerInfo.getBrowserId(getBrowser()) + " launched.\n");
        } else {
            logFatal("FATAL: Browser " + getBrowser().browserType().name().toUpperCase() + " is NOT connected\n");
            System.exit(1);
        }

        logInfo(ReportUtils.getEndLine());
    }

    @Override
    @BeforeMethod
    void createContextAndPage(Method method) {
        logInfo("Run " + ReportUtils.getTestMethodName(method));

        context = BrowserManager.createContextWithCookies(getBrowser(), LoginUtils.NEW_CUSTOMER_COOKIES_FILE_PATH);
        logInfo("Context created");

        TracingUtils.startTracing(context);
        logInfo("Tracing started");

        classPage = context.newPage();
        logInfo("Page created");

        classPage.navigate(ProjectProperties.BASE_URL);
        if(isOnHomePage()) {
            getPage().onLoad(p -> classPage.content());
            if (!classPage.content().isEmpty()) {
                logInfo("Open Home page");
            }
            logInfo("Testing....");
        } else {
            logError("HomePage is NOT opened");
        }
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
    }

    @Override
    @AfterSuite
    void closeBrowser() {
        APIServices.deleteNewCustomer(getPlaywright());
        logInfo("API: New customer deleted successfully");

        if (getBrowser() != null) {
            getBrowser().close();
            logInfo("Browser closed");
        }
        if (getPlaywright() != null) {
            getPlaywright().close();
            logInfo("Playwright closed");
            logInfo(ReportUtils.getEndLine() + "\n");
        }
    }

    @Test(
            testName = "LMS-1343 https://app.qase.io/plan/LMS/1?case=1343",
            description = "TC1343-02 - Verification of Text in the 'Streaks' Modal Window"
    )
    @Description("Objective: To confirm that the user can view points greater than 0 and the text indicating the number of streak days in the modal window.")
    @Story("Home Page")
    @TmsLink("j0y70alubidi")
    public void testStreaksModalWindowTextVerification() {
        PreconditionPage precondition = new PreconditionPage(getPage()).init();
        HomePage homePage = new HomePage(getPage());

                precondition
                        .getAllCheckboxesInA2WeeksPlan();


        Assert.assertTrue(
                precondition.areAllCheckboxesUnchecked(),
                "If FAIL: Precondition is not reached. NOT All Checkboxes are unchecked.\n"
        );

        homePage.init();

        final int randomIndex = homePage.getRandomIndex();

        assertThat(homePage.getNthCheckbox(randomIndex)).not().isChecked();

        assertThat(homePage.getStreaksButton()).hasText("0");

        homePage
                .clickNthCheckbox(randomIndex);

        assertThat(homePage.getStreaksButton()).hasText("1");

        homePage
                .clickStreaksButton();

        assertThat(homePage.getStreakDaysModalWindowTextLocator()).hasText(TestData.ONE_DAY_STUDY_STREAK_MESSAGE);
        Assert.assertTrue(homePage.getMainSectionPoints() > 0);
    }
}


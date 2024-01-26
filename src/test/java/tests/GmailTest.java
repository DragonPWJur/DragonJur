package tests;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.reports.ExceptionListener;
import utils.reports.ReportUtils;
import utils.runner.GmailUtils;

import java.lang.reflect.Method;

import static utils.reports.LoggerUtils.logInfo;
import static utils.runner.ProjectProperties.COMMON_EMAIL_PART;

@Listeners(ExceptionListener.class)
public final class GmailTest {

    @BeforeMethod
    void beforeMethod(Method method) {
        logInfo("Run " + ReportUtils.getTestMethodName(method));
    }

    @AfterMethod
    void afterMethod(Method method, ITestResult testResult) {
        ReportUtils.logTestStatistic(method, testResult);
        logInfo(ReportUtils.getEndLine());
    }

    @Test(priority = -2)
    public void testExtractGmailPasswordOauth2() throws Exception {
        String email = COMMON_EMAIL_PART + "1021";
        String expectedPassword = "MjXQ350#@&";

        String actualPassword = GmailUtils
                .extractPasswordFromEmail(GmailUtils.getGmailService(), email);

        Assert.assertEquals(actualPassword, expectedPassword);
    }
}
package utils.reports;

import com.google.common.collect.ImmutableMap;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import org.testng.ITestResult;
import org.testng.annotations.Test;
import utils.runner.ProjectProperties;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;
import static utils.reports.LoggerUtils.*;

public final class ReportUtils {
    private final static String END_LINE = String.format("%n%s", "—".repeat(90));
    private final static String H_LINE = String.format("%s%n", "=".repeat(90));

    public static String getEndLine() {

        return END_LINE;
    }

    public static String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd, hh:mma");

        return now.format(dateTimeFormat);
    }

    public static String getTestStatus(ITestResult testResult) {
        int status = testResult.getStatus();

        return switch (status) {
            case 1 -> "PASS";
            case 2 -> "FAIL";
            case 3 -> "SKIP";
            default -> "UNDEFINED";
        };
    }

    public static String getTestRunTime(ITestResult testResult) {
        final long time = testResult.getEndMillis() - testResult.getStartMillis();
        Duration duration = Duration.ofMillis(time);

        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();
        long milliseconds = duration.toMillis() % 1000;

        return String.format("%d min %d sec %d ms", minutes, seconds, milliseconds);
    }

    public static String getReportHeader() {
        String headerAndDate = """
                T E S T     R E P O R T 
                Date: %s 
                """.formatted(getCurrentDateTime());

        return "\n" + H_LINE + headerAndDate + H_LINE;
    }

    public static String getTestStatistics(Method method, ITestResult testResult) {

        return "\n" + getTestMethodNameWithInvocationCount(method, testResult)
                + "   ----   " + getTestStatus(testResult) + "\t Run Time: " + getTestRunTime(testResult) + "\n";
    }

    public static String getTestMethodName(Method method) {

        return method.getDeclaringClass().getName() + "." + method.getName();
    }

    public static String getTestMethodNameWithInvocationCount(Method method, ITestResult testResult) {
        String testMethodName = getTestMethodName(method);
        if (!method.getAnnotation(Test.class).dataProvider().isEmpty()) {
            testMethodName += "(" + testResult.getMethod().getCurrentInvocationCount() + ")";
        }

        return testMethodName;
    }

    public static void logTestStatistic(Method method, ITestResult testResult) {
        if (getTestStatus(testResult).equals("PASS")) {
            logSuccess(getTestStatistics(method, testResult));
        } else if (getTestStatus(testResult).equals("FAIL")) {
            logError(ReportUtils.getTestStatistics(method, testResult));
        } else {
            logWarning(ReportUtils.getTestStatistics(method, testResult));
        }
    }

    public static void addScreenshotToAllureReportForCIFailure(Page page, ITestResult testResult) {
        if (!testResult.isSuccess() && ProjectProperties.isServerRun()) {
            Allure.getLifecycle().addAttachment(
                    "screenshot", "image/png", "png",
                    page.screenshot(new Page.ScreenshotOptions()
                            .setFullPage(true)));
            logInfo("Screenshot added to Allure report");
        }
    }

    public static void addVideoAndTracingToAllureReportForCIFailure(Method testMethod, ITestResult testResult) throws IOException {
        if (!testResult.isSuccess() && ProjectProperties.isServerRun()) {
            Allure.getLifecycle().addAttachment("video", "videos/webm", "webm",
                    Files.readAllBytes(Paths.get("target/videos/" + testMethod.getName() + ".webm")));
            logInfo("Video added to Allure report");

            Allure.getLifecycle().addAttachment("tracing", "archive/zip", "zip",
                    Files.readAllBytes(Paths.get("target/testTracing/" + testMethod.getName() + ".zip")));
            logInfo("Tracing added to Allure report");
        }
    }

    public static void getEnvironmentForAllureReport(Browser browser) {
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("OS", System.getProperty("os.name"))
                        .put("OS.version", System.getProperty("os.version"))
                        .put("Browser", browser.browserType().name())
                        .put("Browser.version", browser.version())
                        .put("Java.version", System.getProperty("java.version"))
                        .build());
    }
}
package utils;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportUtils {

    public final static String END_LINE =
            "\n_________________________________________________________________________________________\n";
    private final static String H_LINE =
            " ==========================================================================================\n";

    public static String getCurrentDateTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd, hh:mma");

        return dateFormat.format(date);
    }

    private static String getTestStatus(ITestResult testResult) {
        int status = testResult.getStatus();

        return switch (status) {
            case 1 -> "PASS";
            case 2 -> "FAIL";
            case 3 -> "SKIP";
            default -> "UNDEFINED";
        };
    }

    private static String getTestRunTime(ITestResult testResult) {
        final long time = testResult.getEndMillis() - testResult.getStartMillis();
        int minutes = (int) ((time / 1000) / 60);
        int seconds = (int) (time / 1000) % 60;
        int milliseconds = (int) (time % 1000);

        return minutes + " min " + seconds + " sec " + milliseconds + " ms";
    }

    public static String getReportHeader(ITestContext testContext) {
        String header = "\tT E S T     R E P O R T\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "\n";
        String currentDate = "\tDate:" + getCurrentDateTime() + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "\n";

        return "\n" + H_LINE + header + currentDate + H_LINE;
    }

    public static String getTestStatistics(Method method, ITestResult testResult) {
        return "\n\n" + getTestMethodNameWithInvocationCount(method, testResult)
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
}
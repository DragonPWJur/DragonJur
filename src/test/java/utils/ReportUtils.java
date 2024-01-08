package utils;

import org.testng.ITestContext;
import org.testng.ITestResult;

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

    private static String getTestStatus(ITestResult result) {
        int status = result.getStatus();

        return switch (status) {
            case 1 -> "PASS";
            case 2 -> "FAIL";
            case 3 -> "SKIP";
            default -> "UNDEFINED";
        };
    }

    private static String getTestRunTime(ITestResult result) {
        final long time = result.getEndMillis() - result.getStartMillis();
        int minutes = (int) ((time / 1000) / 60);
        int seconds = (int) (time / 1000) % 60;
        int milliseconds = (int) (time % 1000);

        return minutes + " min " + seconds + " sec " + milliseconds + " ms";
    }

    public static String getReportHeader(ITestContext context) {
        String header = "\tT E S T     R E P O R T\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "\n";
        String currentDate = "\tDate:" + getCurrentDateTime() + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "\n";

        return "\n" + H_LINE + header + currentDate + H_LINE;
    }

    public static String getClassNameTestName(Method method, ITestResult result) {
        String className = result.getTestClass().toString();
        String testName = method.getName();

        return className.substring(22, className.length() - 1) + "/" + testName;
    }

    public static String getTestStatistics(Method method, ITestResult result) {

        return "\n\n" + getClassNameTestName(method, result)
                + "   ----   " + getTestStatus(result) + "\t Run Time: " + getTestRunTime(result) + "\n";
    }
}
package utils;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExceptionListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Throwable exception = result.getThrowable();
        LoggerUtils.logError("Error caused by " + exception.getClass().getSimpleName());
        LoggerUtils.logError("Exception message: " + exception.getMessage());
    }
}

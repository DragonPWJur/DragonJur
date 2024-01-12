package utils;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExceptionListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Throwable exception = result.getThrowable();
        LoggerUtils.logException("Error caused by " + exception.getClass().getSimpleName());
        LoggerUtils.logException("Exception message: " + exception.getMessage());
    }
}

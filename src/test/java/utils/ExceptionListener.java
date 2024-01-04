package utils;

import org.testng.ITestListener;
import org.testng.ITestResult;
import tests.BaseTest;

public class ExceptionListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        Throwable exception = result.getThrowable();
        BaseTest.log.error("Error caused by " + exception.getClass().getSimpleName());
        BaseTest.log.error("Exception message: " + exception.getMessage());
    }
}

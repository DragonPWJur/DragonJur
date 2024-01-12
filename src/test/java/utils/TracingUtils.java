package utils;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import org.testng.ITestResult;

import java.lang.reflect.Method;
import java.nio.file.Paths;

public class TracingUtils {

    public static void startTracing(BrowserContext context) {
        context.tracing().start(
                new Tracing.StartOptions()
                        .setScreenshots(true)
                        .setSnapshots(true)
                        .setSources(true)
        );
    }

    public static void stopTracing(Page page, BrowserContext context, Method method, ITestResult result) {
        final String testMethodName = ReportUtils.getTestMethodNameWithInvocationCount(method, result);
        Tracing.StopOptions tracingStopOptions = null;

        if (!result.isSuccess()) {
            if (PropertyType.Tracing.TRACING_MODE) {
                tracingStopOptions = new Tracing.StopOptions()
                        .setPath(Paths.get( PropertyType.Tracing.TRACING_PATH + testMethodName + ".zip"));
                LoggerUtils.log("Tracing saved");
            }
            if (PropertyType.Tracing.VIDEO_MODE) {
                page.video().saveAs(Paths.get(PropertyType.Tracing.VIDEO_PATH + testMethodName + ".webm"));
                LoggerUtils.log("Video saved");
            }
        } else {
            page.video().delete();
        }

        context.tracing().stop(tracingStopOptions);
    }
}

package utils;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import org.testng.ITestResult;

import java.lang.reflect.Method;
import java.nio.file.Paths;

import static utils.LoggerUtils.log;

public final class TracingUtils {

    public static void startTracing(BrowserContext context) {
        context.tracing().start(
                new Tracing.StartOptions()
                        .setScreenshots(true)
                        .setSnapshots(true)
                        .setSources(true)
        );
    }

    public static void stopTracing(Page page, BrowserContext context, Method testMethod, ITestResult testResult) {
        Tracing.StopOptions tracingStopOptions = null;

        if (!testResult.isSuccess()) {
            if (ProjectProperties.TRACING_MODE) {
                tracingStopOptions = new Tracing.StopOptions()
                        .setPath(Paths.get("testTracing/" + testMethod.getName() + ".zip"));
                log("Tracing saved");
            }
            if (ProjectProperties.VIDEO_MODE) {
                page.video().saveAs(Paths.get("videos/" + testMethod.getName() + ".webm"));
                log("Video saved");
                page.video().delete();
            }
        } else {
            page.video().delete();
        }

        context.tracing().stop(tracingStopOptions);
    }
}

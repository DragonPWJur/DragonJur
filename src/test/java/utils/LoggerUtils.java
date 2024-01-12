package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tests.BaseTest;

public class LoggerUtils {

    private static final String error = "❌\n";
    private static final String success = "✅\n";
    private static final String warning = "⚠️";
    private static final String exception = "❗";

    private static final Logger logger = LogManager.getLogger(BaseTest.class.getName());

    public static void log(String message) {
        logger.info(message);
    }

    public static void logError(String message) {
        logger.error(error + message);
    }

    public static void logSuccess(String message) {
        logger.info(success + message);
    }

    public static void logWarning(String message) {
        logger.info(warning + message);
    }

    public static void logException(String message) {
        logger.error(exception + message);
    }

    public static void logFatal(String message) {
        logger.fatal(error + exception + message);
    }
}

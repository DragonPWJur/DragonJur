package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtils {
    private static final Logger logger = LogManager.getLogger();

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";


    public static void log(String message) {
        logger.info(message);
    }

    public static void logError(String message) {
        logger.error(ANSI_RED + message + ANSI_RESET);
    }

    public static void logGreen(String message) {
        logger.info(ANSI_GREEN + message + ANSI_RESET);
    }
}

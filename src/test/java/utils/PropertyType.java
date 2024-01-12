package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public abstract class PropertyType {

    private static Properties projectProperties = initProjectProperties();
    private static Properties environmentProperties = initEnvironmentProperties();
    private static Properties tracingProperties = initTracingProperties();

    private static final String CONFIG_PROPERTIES_PATH = "./src/test/resources/config.properties";
    private static final String TRACING_PROPERTIES_PATH = "./src/test/resources/tracing.properties";

    public abstract static class Browser {
        public static final String BROWSER_TYPE_NAME = projectProperties.getProperty("browserType").trim();
        public static final String BASE_URL = projectProperties.getProperty("baseUrl").trim();
        public static final boolean IS_HEADLESS = Boolean.parseBoolean(projectProperties.getProperty("headless").trim());
        public static final double IS_SLOW = Double.parseDouble(projectProperties.getProperty("slowMo").trim());
        public static final int SCREEN_SIZE_WIDTH = Integer.parseInt(projectProperties.getProperty("width").trim());
        public static final int SCREEN_SIZE_HEIGHT = Integer.parseInt(projectProperties.getProperty("height").trim());
    }

    public abstract static class Auth {
        public static final String USERNAME = projectProperties.getProperty("username").trim();
        public static final String PASSWORD = projectProperties.getProperty("password").trim();
    }

    public abstract static class Gmail {
        public static final String COMMON_EMAIL_PART = projectProperties.getProperty("commonEmailPart").trim();
    }

    public abstract static class Environment {
        private static final String ENV_WEB_OPTIONS = "WEB_OPTIONS";
        private static final String ENV_BROWSER_OPTIONS = "BROWSER_OPTIONS";
    }

    public abstract static class Tracing {
        public static final boolean TRACING_MODE = Boolean.parseBoolean(tracingProperties.getProperty("tracing").trim());
        public static final boolean VIDEO_MODE = Boolean.parseBoolean(tracingProperties.getProperty("video").trim());
        public static final String TRACING_PATH = tracingProperties.getProperty("tracingPath").trim();
        public static final String VIDEO_PATH = tracingProperties.getProperty("videoPath").trim();
        public static final int VIDEO_WIDTH = Integer.parseInt(tracingProperties.getProperty("videoWidth"));
        public static final int VIDEO_HEIGHT = Integer.parseInt(tracingProperties.getProperty("videoHeight"));
    }

    private static boolean isServerRun() {
        return System.getenv("CI_RUN") != null;
    }

    private static Properties initProjectProperties() {
        if (projectProperties == null) {
            projectProperties = new Properties();
            if (!isServerRun()) {
                try (FileInputStream fileInputStream = new FileInputStream(CONFIG_PROPERTIES_PATH)) {
                    projectProperties.load(fileInputStream);
                } catch (FileNotFoundException e) {
                    LoggerUtils.logError("ERROR: The \u001B[31mconfig.properties\u001B[0m file not found.");
                    LoggerUtils.log("You need to create it from config.properties.TEMPLATE file.");
                    System.exit(1);
                } catch (IOException ignore) {
                }
            }
        }
        return projectProperties;
    }

    private static Properties initEnvironmentProperties() {
        if (environmentProperties == null) {
            environmentProperties = new Properties();
            if (isServerRun()) {

                if (System.getenv(PropertyType.Environment.ENV_BROWSER_OPTIONS) != null) {
                    for (String option : System.getenv(PropertyType.Environment.ENV_BROWSER_OPTIONS).split(";")) {
                        String[] browserOptionArr = option.split("=");
                        environmentProperties.setProperty(browserOptionArr[0], browserOptionArr[1]);
                    }
                }

                if (System.getenv(PropertyType.Environment.ENV_WEB_OPTIONS) != null) {
                    for (String option : System.getenv(PropertyType.Environment.ENV_WEB_OPTIONS).split(";")) {
                        String[] webOptionArr = option.split("=");
                        environmentProperties.setProperty(webOptionArr[0], webOptionArr[1]);
                    }
                }
            }
        }

        return environmentProperties;
    }

    private static Properties initTracingProperties() {
        if (tracingProperties == null) {
            tracingProperties = new Properties();
            try (FileInputStream fileInputStream = new FileInputStream(TRACING_PROPERTIES_PATH)) {
                tracingProperties.load(fileInputStream);
            } catch (FileNotFoundException e) {
                LoggerUtils.log("ERROR: The \u001B[31mtracing.properties\u001B[0m file not found.");
                System.exit(1);
            } catch (IOException e) {
                throw new ExceptionInInitializerError(e);
            }
        }

        return tracingProperties;
    }
}
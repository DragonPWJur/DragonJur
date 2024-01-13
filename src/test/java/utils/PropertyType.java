package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public abstract class PropertyType {

    /*Local
    ______________________
    Browser
    BROWSER_TYPE_NAME
    BASE_URL
    IS_HEADLESS
    SLOW_MO
    SCREEN_SIZE_WIDTH
    SCREEN_SIZE_HEIGHT
    ______________________
    Login
    USERNAME
    PASSWORD
    ______________________
    Emqil
    COMMON_EMAIL_PART

    ______________________

    Tracing
    TRACING_MODE
    VIDEO_MODE
    TRACING_PATH
    VIDEO_PATH
    VIDEO_WIDTH
    VIDEO_HEIGHT

    */

    private static final String BROWSER_PROPERTIES_PATH = "./src/test/resources/browser.properties";
    private static final String LOGIN_PROPERTIES_PATH = "./src/test/resources/login.properties";
    private static final String TRACING_PROPERTIES_PATH = "./src/test/resources/tracing.properties";

    private static Properties browserProperties = initBrowserProperties();
    private static Properties loginProperties = initLoginProperties();
    private static Properties tracingProperties = initTracingProperties();
    private static Properties envBrowserProperties = initEnvBrowserProperties();
    private static Properties envWebProperties = initEnvWebProperties();
    private static final Properties envTracingProperties = initEnvTracingProperties();

    public abstract static class Browser {
        public static final String BROWSER_TYPE_NAME = browserProperties.getProperty("browserType").trim();
        public static final String BASE_URL = browserProperties.getProperty("baseUrl").trim();
        public static final boolean IS_HEADLESS = Boolean.parseBoolean(browserProperties.getProperty("headless").trim());
        public static final double SLOW_MO = Double.parseDouble(browserProperties.getProperty("slowMo").trim());
        public static final int SCREEN_SIZE_WIDTH = Integer.parseInt(browserProperties.getProperty("width").trim());
        public static final int SCREEN_SIZE_HEIGHT = Integer.parseInt(browserProperties.getProperty("height").trim());
    }

    public abstract static class Login {
        public static final String USERNAME = loginProperties.getProperty("username").trim();
        public static final String PASSWORD = loginProperties.getProperty("password").trim();
    }

    public abstract static class Email {
        public static final String COMMON_EMAIL_PART = loginProperties.getProperty("commonEmailPart").trim();
    }

    public abstract static class Tracing {
        public static final boolean TRACING_MODE = Boolean.parseBoolean(tracingProperties.getProperty("tracing").trim());
        public static final boolean VIDEO_MODE = Boolean.parseBoolean(tracingProperties.getProperty("video").trim());
        public static final String TRACING_PATH = tracingProperties.getProperty("tracingPath").trim();
        public static final String VIDEO_PATH = tracingProperties.getProperty("videoPath").trim();
        public static final int VIDEO_WIDTH = Integer.parseInt(tracingProperties.getProperty("videoWidth"));
        public static final int VIDEO_HEIGHT = Integer.parseInt(tracingProperties.getProperty("videoHeight"));
    }

    /*
          BROWSER_OPTIONS: browserType=chromium;width=1920;height=1080;headless=true;slowMo=0
          WEB_OPTIONS: username=${{ secrets.USERNAME }};password=${{ secrets.PASSWORD }};baseUrl=${{ secrets.BASE_URL }};
          commonEmailPart=${{ secrets.COMMON_EMAIL_PART }}
          TRACING_OPTIONS: tracing=true;video=true
     */

    public abstract static class Environment {
        private static final String ENV_BROWSER_OPTIONS = "BROWSER_OPTIONS";
        private static final String ENV_WEB_OPTIONS = "WEB_OPTIONS";
        private static final String ENV_TRACING_OPTIONS = "TRACING_OPTIONS";
    }

    private static boolean isCIRun() {
        return System.getenv("CI_RUN") != null;
    }
                                                                                                                                                               m
    private static Properties initBrowserProperties() {
        if (browserProperties == null) {
            LoggerUtils.log("browserProperties == null");
            browserProperties = new Properties();
            if (!isCIRun()) {
                LoggerUtils.log("Local Run()");
                try (FileInputStream fileInputStream = new FileInputStream(BROWSER_PROPERTIES_PATH)) {
                    browserProperties.load(fileInputStream);
                } catch (FileNotFoundException e) {
                    LoggerUtils.logError("ERROR: The \u001B[31mbrowser.properties\u001B[0m file not found.");
                    LoggerUtils.log("You need to create it from browser.properties.TEMPLATE file.");
                    System.exit(1);
                } catch (IOException ignore) {
                }
            }
        }
        if (browserProperties != null) {
            LoggerUtils.log("local browserProperties = " + browserProperties.toString());
        }

        return browserProperties;
    }

    private static Properties initLoginProperties() {
        if (loginProperties == null) {
            LoggerUtils.log("loginProperties == null");
            loginProperties = new Properties();
            if (!isCIRun()) {
                LoggerUtils.log("Local Run()");
                try (FileInputStream fileInputStream = new FileInputStream(LOGIN_PROPERTIES_PATH)) {
                    loginProperties.load(fileInputStream);
                } catch (FileNotFoundException e) {
                    LoggerUtils.logError("ERROR: The \u001B[31mlogin.properties\u001B[0m file not found.");
                    LoggerUtils.log("You need to create it from login.properties.TEMPLATE file.");
                    System.exit(1);
                } catch (IOException ignore) {
                }
            }
        }
        if (loginProperties != null) {
            LoggerUtils.log("local loginProperties = " + loginProperties.toString());
        }

        return loginProperties;
    }

    private static Properties initTracingProperties() {
        if (tracingProperties == null) {
            LoggerUtils.log("tracingProperties == null");
            tracingProperties = new Properties();
            if (!isCIRun()) {
                LoggerUtils.log("Local Run()");
                try (FileInputStream fileInputStream = new FileInputStream(TRACING_PROPERTIES_PATH)) {
                    tracingProperties.load(fileInputStream);
                } catch (FileNotFoundException e) {
                    LoggerUtils.logError("ERROR: The \u001B[31mtracingProperties\u001B[0m file not found.");
                    LoggerUtils.log("You need to copy this file from github repo.");
                    System.exit(1);
                } catch (IOException ignore) {
                }
            }
        }
        if (tracingProperties != null) {
            LoggerUtils.log("local tracingProperties = " + tracingProperties.toString());
        }

        return tracingProperties;
    }

    private static Properties initEnvBrowserProperties() {
        if (envBrowserProperties == null) {
            LoggerUtils.log("envBrowserProperties == null");

            envBrowserProperties = new Properties();

            if (isCIRun()) {
                LoggerUtils.log("CI run");
                if (System.getenv(PropertyType.Environment.ENV_BROWSER_OPTIONS) != null) {
                    LoggerUtils.log("ENV_BROWSER_OPTIONS != null");
                    LoggerUtils.log("ENV_BROWSER_OPTIONS = " + System.getenv(PropertyType.Environment.ENV_BROWSER_OPTIONS));
                    for (String option : System.getenv(PropertyType.Environment.ENV_BROWSER_OPTIONS).split(";")) {
                        String[] browserOptionArr = option.split("=");
                        LoggerUtils.log("browserOptionArr = " + Arrays.toString(browserOptionArr));
                        envBrowserProperties.setProperty(browserOptionArr[0], browserOptionArr[1]);
                    }
                }
            }
            logEnvBrowserProperties();
        }
                                                                                                                                                                                                                                                                                                                                                                                                                   }

    private static Properties initEnvWebProperties() {
        if (envWebProperties == null) {
            LoggerUtils.log("envWebProperties == null");

            envWebProperties = new Properties();

            if (isCIRun()) {
                LoggerUtils.log("CI run");

                if (System.getenv(PropertyType.Environment.ENV_WEB_OPTIONS) != null) {
                    LoggerUtils.log("ENV_WEB_OPTIONS != null");
                    LoggerUtils.log("ENV_WEB_OPTIONS = " + System.getenv(PropertyType.Environment.ENV_WEB_OPTIONS));
                    for (String option : System.getenv(PropertyType.Environment.ENV_WEB_OPTIONS).split(";")) {
                        String[] webOptionArr = option.split("=");
                        LoggerUtils.log(Arrays.toString(webOptionArr));
                        envWebProperties.setProperty(webOptionArr[0], webOptionArr[1]);
                    }
                }
            }
        }
        return envWebProperties;
    }

    private static Properties initEnvTracingProperties() {
        if (System.getenv(Environment.ENV_TRACING_OPTIONS) != null) {
            LoggerUtils.log("ENV_TRACING_OPTIONS != null");
            LoggerUtils.log("ENV_TRACING_OPTIONS = " + System.getenv(PropertyType.Environment.ENV_TRACING_OPTIONS));
            for (String option : System.getenv(PropertyType.Environment.ENV_TRACING_OPTIONS).split(";")) {
                String[] tracingOptionArr = option.split("=");
                LoggerUtils.log(Arrays.toString(tracingOptionArr));
                if (envTracingProperties != null) {
                    envTracingProperties.setProperty(tracingOptionArr[0], tracingOptionArr[1]);
                }
            }
        }
        if (envTracingProperties != null) {
            LoggerUtils.log("envTracingProperties = " + envTracingProperties.toString());
            LoggerUtils.log("ENV_TRACING_OPTIONS - success");
        }

        return envTracingProperties;
    }

    public static void logEnvBrowserProperties() {
        if (envBrowserProperties != null) {
            LoggerUtils.log("envBrowserProperties = " + envBrowserProperties.toString());
            LoggerUtils.log("ENV_BROWSER_OPTIONS - success");
        }
    }

    public static void logEnvWebProperties() {
        if (envWebProperties != null) {
            LoggerUtils.log("envWebProperties = " + envWebProperties.toString());
            LoggerUtils.log("ENV_WEB_OPTIONS - success");
        }
    }

    public static void logEnvTracingProperties() {
        if (envTracingProperties != null) {
            LoggerUtils.log("envTracingProperties = " + envTracingProperties.toString());
            LoggerUtils.log("ENV_TRACING_OPTIONS - success");
        }
    }


}



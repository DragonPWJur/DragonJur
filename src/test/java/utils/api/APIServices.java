package utils.api;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.json.JSONObject;
import utils.reports.LoggerUtils;
import utils.runner.LoginUtils;
import utils.runner.ProjectProperties;

import java.util.HashMap;
import java.util.Map;

public final class APIServices {
    private static final String AUTH_CUSTOMER_SIGN_IN_END_POINT = "/auth/customer/signIn";
    private static final String RESET_COURSE_RESULTS_END_POINT = "/courses/results";
    public static final String BRONZE_SUBSCRIPTION = "f64edfa6-1aca-4d9a-9c49-8f29970790af";
    public static final String GOLD_SUBSCRIPTION = "bcf37a9f-af5f-47b0-b9aa-c8e36bbd8278";

    private static APIRequestContext apiRequestContext;
    private static final String userToken = LoginUtils.getUserToken();

    public static void createApiRequestContext(Playwright playwright) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        apiRequestContext = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(ProjectProperties.API_BASE_URL)
                .setExtraHTTPHeaders(headers));
    }

    public static void cleanData(Playwright playwright) {
        createApiRequestContext(playwright);

        apiRequestContext
                .delete(
                        RESET_COURSE_RESULTS_END_POINT,
                        RequestOptions
                                .create()
                                .setHeader("Authorization", "Bearer " + userToken)
                );
    }

    private static APIResponse courseSubscribe(String courseId) {
        Map<String, String> courseData = new HashMap<>();
        courseData.put("period", "monthly");
        courseData.put("type", "bronze");

        return apiRequestContext
                .post(
                        "/courses/" + courseId + "/subscribe",
                        RequestOptions
                                .create()
                                .setHeader("Authorization", "Bearer " + userToken)
                                .setData(courseData)
                );
    }

    private static APIResponse setActive(String courseId) {

        return apiRequestContext
                .post(
                        "/courses/" + courseId + "/setActive",
                        RequestOptions
                                .create()
                                .setHeader("Authorization", "Bearer " + userToken)
                );
    }

    private static APIResponse activeCourse() {

        return apiRequestContext
                .get(
                        "/courses/active",
                        RequestOptions
                                .create()
                                .setHeader("Authorization", "Bearer " + userToken));
    }

    public static String getCourseName(String courseId) {
        if (GOLD_SUBSCRIPTION.equals(courseId)) {
            return "GOLD_SUBSCRIPTION";
        } else if (BRONZE_SUBSCRIPTION.equals(courseId)) {
            return "BRONZE_SUBSCRIPTION";
        } else {
            return "Unknown Subscription";
        }
    }

    public static void bronzeCourseSubscription(Playwright playwright) {
        createApiRequestContext(playwright);
        APIResponse apiResponse = courseSubscribe(BRONZE_SUBSCRIPTION);

        if (apiResponse.status() == 201) {
            LoggerUtils.logInfo("API: Successfully subscribed to the BRONZE subscription");
        }
        if (apiResponse.status() == 422) {
            LoggerUtils.logInfo("API: You already have a BRONZE subscription");
            setActiveCourse(playwright, BRONZE_SUBSCRIPTION);
        }
        if (apiResponse.status() != 201 && apiResponse.status() != 422) {
            LoggerUtils.logError("ERROR: " + apiResponse.status() + " - " + apiResponse.statusText());
        }
    }

    public static void setActiveCourse(Playwright playwright, String courseId) {
        createApiRequestContext(playwright);
        APIResponse apiResponse = setActive(courseId);

        JSONObject object = new JSONObject(apiResponse.text());

        if (apiResponse.status() != 201) {
            LoggerUtils.logError(apiResponse.status() + " - " + object.getString("message"));
            System.exit(1);
        } else {
            LoggerUtils.logInfo("API: Switched to " + getCourseName(courseId) + " subscription");
        }
    }

    public static String getIdActiveCourse(Playwright playwright) {
        createApiRequestContext(playwright);
        APIResponse apiResponse =  activeCourse();

        JSONObject object = new JSONObject(apiResponse.text());

        return object.getString("id");
    }

    public static void checkIfGoldIsActive(Playwright playwright) {

        if (!GOLD_SUBSCRIPTION.equals(getIdActiveCourse(playwright))) {
            setActiveCourse(playwright,GOLD_SUBSCRIPTION);
        }
        LoggerUtils.logInfo("API: The user currently has a Gold subscription");
    }
}

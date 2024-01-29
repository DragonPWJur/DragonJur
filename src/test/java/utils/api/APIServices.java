package utils.api;

import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import utils.runner.LoginUtils;
import utils.runner.ProjectProperties;

import java.util.HashMap;
import java.util.Map;

import static utils.api.APIUtils.*;

public final class APIServices {
    private static final String COURSES_RESULTS = "/courses/results";
    private static final String AUTH_ADMIN_SIGN_IN = "/auth/admin/signIn";
    private static final String COURSES_ACTIVE = "/courses/active";
    private static final String GUIDES_COURSES = "/guides/courses/";
    private static final String GUIDES = "/guides/";
    private static final String CHAPTERS = "/chapters/";
    private static final String TABLE_OF_CONTENT = "/table-of-content";
    private static final String ADMIN_GUIDES_UNITS = "/admin/guides/units/";
    private static final String PLANS = "/plans/";
    private static final String PHASES = "/phases";
    private static final String PLANS_CURRENT = "/plans/current";
    private static final String TASKS = "/tasks/";
    private static final String MARK = "/mark";
    private static final String COURSES = "/courses/";
    private static final String SUBSCRIBE = "/subscribe";
    private static final String SET_ACTIVE = "/setActive";
    private static final String PAYMENT_METHOD = "/customer/paymentMethod";

    private static final String userToken = LoginUtils.getUserToken();
    private static String adminToken;

    public static void cleanData(Playwright playwright) {
        APIRequest request = playwright.request();
        APIRequestContext requestContext = request.newContext();

        APIResponse apiResponse = requestContext
                .delete(
                        ProjectProperties.API_BASE_URL + COURSES_RESULTS,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + userToken)
                );

        checkStatus(apiResponse, "cleanData");
        requestContext.dispose();
    }

    public static APIResponse deleteCustomerPaymentMethod(Playwright playwright) {
        APIRequest request = playwright.request();
        APIRequestContext requestContext = request.newContext();

         return requestContext
                .delete(
                        ProjectProperties.API_BASE_URL + PAYMENT_METHOD,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + userToken)
                );
    }

    private static String getAdminToken(APIRequestContext requestContext) {
        if (adminToken == null || adminToken.isEmpty()) {
            Map<String, String> data = new HashMap<>();
            data.put("email", ProjectProperties.ADMIN_USERNAME);
            data.put("password", ProjectProperties.ADMIN_PASSWORD);

            APIResponse apiResponse = requestContext
                    .post(
                            ProjectProperties.API_BASE_URL + AUTH_ADMIN_SIGN_IN,
                            RequestOptions.create()
                                    .setData(data)
                    );

            checkStatus(apiResponse, "getAdminToken");

            JsonObject admin = initJsonObject(apiResponse.text());

            adminToken = admin.get("token").getAsString();
        }

        return adminToken;
    }

    public static JsonObject getActiveCourse(APIRequestContext requestContext) {
        APIResponse apiResponse = requestContext
                .get(
                        ProjectProperties.API_BASE_URL + COURSES_ACTIVE,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + userToken)
                );

        checkStatus(apiResponse, "getActiveCourse");

        return initJsonObject(apiResponse.text());
    }

    public static JsonObject getStudyGuide(APIRequestContext requestContext, String courseId) {
        APIResponse apiResponse = requestContext
                .get(
                        ProjectProperties.API_BASE_URL + GUIDES_COURSES + courseId,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + userToken)
                );

        checkStatus(apiResponse, "getStudyGuide");

        return initJsonObject(apiResponse.text());
    }

    public static JsonObject getStudyGuideTable(APIRequestContext requestContext, String guideId) {
        APIResponse apiResponse = requestContext
                .get(
                        ProjectProperties.API_BASE_URL + GUIDES + guideId + TABLE_OF_CONTENT,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + userToken)
                );

        checkStatus(apiResponse, "getStudyGuideTable");

        return initJsonObject(apiResponse.text());
    }

    public static JsonObject getChapter(APIRequestContext requestContext, String guideId, String chapterid) {
        APIResponse apiResponse = requestContext
                .get(
                        ProjectProperties.API_BASE_URL + GUIDES + guideId + CHAPTERS + chapterid,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + userToken)
                );

        checkStatus(apiResponse, "getChapter");

        return initJsonObject(apiResponse.text());
    }

    public static void changeChapterText(APIRequestContext requestContext, JsonObject unit) {
        APIResponse apiResponse = requestContext
                .patch(ProjectProperties.API_BASE_URL + ADMIN_GUIDES_UNITS + unit.get("id").getAsString(),
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + getAdminToken(requestContext))
                                .setData(unit)
                );

        checkStatus(apiResponse, "changeChapterText");
    }

    public static JsonObject getPlans(APIRequestContext requestContext) {

        APIResponse apiResponse = requestContext
                .get(
                        ProjectProperties.API_BASE_URL + PLANS,
                        RequestOptions
                                .create()
                                .setHeader("accept", "application/json")
                                .setHeader("Authorization", "Bearer " + userToken)
                );

        checkStatus(apiResponse, "getPlans");

        return initJsonObject(apiResponse.text());
    }

    public static void changeCurrentPlan(APIRequestContext requestContext, String _2WeeksPlanId) {

        Map<String, Object> data = new HashMap<>();
        data.put("newPlanId", _2WeeksPlanId);

        APIResponse apiResponse = requestContext
                .post(
                        ProjectProperties.API_BASE_URL + PLANS_CURRENT,
                        RequestOptions
                                .create()
                                .setHeader("accept", "*/*")
                                .setHeader("Content-Type", "application/json")
                                .setHeader("Authorization", "Bearer " + userToken)
                                .setData(data)
                );

        checkStatus(apiResponse, "changeCurrentPlan");
    }

    public static JsonObject getPlanPhases(APIRequestContext requestContext, String currentPlanId) {

        APIResponse apiResponse = requestContext
                .get(
                        ProjectProperties.API_BASE_URL + PLANS + currentPlanId + PHASES,
                        RequestOptions
                                .create()
                                .setHeader("accept", "application/json")
                                .setHeader("Authorization", "Bearer " + userToken)
                );

        checkStatus(apiResponse, "planPhases");

        return initJsonObject(apiResponse.text());
    }

    public static void markCheckboxesById(APIRequestContext requestContext, String markId) {

        APIResponse apiResponse = requestContext
                .post(
                        ProjectProperties.API_BASE_URL + TASKS + markId + MARK,
                        RequestOptions
                                .create()
                                .setHeader("accept", "application/json")
                                .setHeader("Authorization", "Bearer " + userToken)
                );

        checkStatus(apiResponse, "markId");
    }
    public static APIResponse courseSubscribe(APIRequestContext apiRequestContext, String courseId, String period, String type) {
        Map<String, String> courseData = new HashMap<>();
        courseData.put("period", period);
        courseData.put("type", type);

        return apiRequestContext
                .post(
                        COURSES + courseId + SUBSCRIBE,
                        RequestOptions
                                .create()
                                .setHeader("Authorization", "Bearer " + userToken)
                                .setData(courseData)
                );
    }

    public static APIResponse setActive(APIRequestContext apiRequestContext, String courseId) {

        return apiRequestContext
                .post(
                        COURSES + courseId + SET_ACTIVE,
                        RequestOptions
                                .create()
                                .setHeader("Authorization", "Bearer " + userToken)
                );
    }

    public static APIResponse activeCourse(APIRequestContext apiRequestContext) {

        return apiRequestContext
                .get(
                        COURSES_ACTIVE,
                        RequestOptions
                                .create()
                                .setHeader("Authorization", "Bearer " + userToken));
    }
}
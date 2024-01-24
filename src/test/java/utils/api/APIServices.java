package utils.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import utils.reports.LoggerUtils;
import utils.runner.LoginUtils;
import utils.runner.ProjectProperties;

import java.util.HashMap;
import java.util.Map;

public final class APIServices {
    private static final String AUTH_CUSTOMER_SIGN_IN_END_POINT = "/auth/customer/signIn";
    private static final String RESET_COURSE_RESULTS_END_POINT = "/courses/results";

    private static final String userToken = LoginUtils.getUserToken();
    private static String adminToken;

    public static void cleanData(Playwright playwright) {
        APIRequest request = playwright.request();
        APIRequestContext requestContext = request.newContext();

        APIResponse apiResponse = requestContext
                .delete(
                        ProjectProperties.API_BASE_URL + RESET_COURSE_RESULTS_END_POINT,
                        RequestOptions.create().setHeader("Authorization", "Bearer " + userToken)
                );
        checkStatus(apiResponse);
    }

    private static JsonObject initJsonObject(String apiResponseBody) {
        JsonObject object = new JsonObject();
        try {
            return object = new Gson().fromJson(apiResponseBody, JsonObject.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }

    private static String getAdminToken(APIRequestContext requestContext) {
        if (adminToken == null || adminToken.isEmpty()) {
            Map<String, String> data = new HashMap<>();
            data.put("email", ProjectProperties.ADMIN_EMAIL);
            data.put("password", ProjectProperties.ADMIN_PASSWORD);

            APIResponse apiResponse = requestContext
                    .post(ProjectProperties.API_BASE_URL + "/auth/admin/signIn",
                            RequestOptions.create().setData(data));
            checkStatus(apiResponse);

            JsonObject admin = initJsonObject(apiResponse.text());

            adminToken = admin.get("token").getAsString();
        }

        return adminToken;
    }

    private static String getAPIResponseBody(APIRequestContext requestContext, String endPoint) {
        APIResponse apiResponse = requestContext
                .get(ProjectProperties.API_BASE_URL + endPoint,
                        RequestOptions
                                .create()
                                .setHeader("Authorization", "Bearer " + userToken));
        checkStatus(apiResponse);

        return apiResponse.text();
    }

    public static JsonObject getActiveCourse(APIRequestContext requestContext) {
        String course = getAPIResponseBody(requestContext, "/courses/active");

        return initJsonObject(course);
    }

    public static JsonObject getStudyGuideByCourseId(APIRequestContext requestContext, String courseId) {
        String studyGuide = getAPIResponseBody(requestContext, "/guides/courses/" + courseId);

        return initJsonObject(studyGuide);
    }

    public static JsonObject getStudyGuideTable(APIRequestContext requestContext, String guideId) {
        String studyGuideTable = getAPIResponseBody(requestContext,
                "/guides/" + guideId + "/table-of-content");

        return initJsonObject(studyGuideTable);
    }

    public static JsonObject getCharterByGuideIdAndChapterId(APIRequestContext requestContext, String guideId, String chapterid) {
        String chapter = getAPIResponseBody(
                requestContext,
                "/guides/" + guideId + "/chapters/" + chapterid);

        return initJsonObject(chapter);
    }

    public static void changeChapter1Unit1TextViaAPIGSON(APIRequestContext requestContext, JsonObject unit) {
        APIResponse apiResponse = requestContext
                .patch(ProjectProperties.API_BASE_URL + "/admin/guides/units/" + unit.get("id").getAsString(),
                        RequestOptions
                                .create()
                                .setHeader("Authorization", "Bearer " + getAdminToken(requestContext))
                                .setData(unit));
        checkStatus(apiResponse);
    }

    private static void checkStatus(APIResponse apiResponse) {
        if (apiResponse.status() < 200 || apiResponse.status() >= 300) {
            LoggerUtils.logException("EXCEPTION: API request FAILED with response status "
                    + apiResponse.status() + ", url: " + apiResponse.url() + " and body: " + apiResponse.text());
        } else {
            LoggerUtils.logInfo("INFO: API request with response status "
                    + apiResponse.status() + " , url: " + apiResponse.url() + ".");
        }
    }
}

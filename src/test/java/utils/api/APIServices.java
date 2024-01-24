package utils.api;

import com.google.gson.Gson;
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

public final class APIServices {
    private static final String AUTH_CUSTOMER_SIGN_IN_END_POINT = "/auth/customer/signIn";
    private static final String RESET_COURSE_RESULTS_END_POINT = "/courses/results";

    private static final String userToken = LoginUtils.getUserToken();
    private static String adminToken;
    public static void cleanData(Playwright playwright) {
        APIRequest request = playwright.request();
        APIRequestContext requestContext = request.newContext();

        requestContext
                .delete(
                        ProjectProperties.API_BASE_URL + RESET_COURSE_RESULTS_END_POINT,
                        RequestOptions.create().setHeader("Authorization", "Bearer " + userToken)
                );
    }

    private static String getAdminToken(APIRequestContext requestContext) {
        if (adminToken == null || adminToken.isEmpty()) {
            Map<String, String> data = new HashMap<>();
            data.put("email", ProjectProperties.ADMINUSERNAME);
            data.put("password", ProjectProperties.ADMINPASSWORD);

            APIResponse apiResponse = requestContext
                    .post(ProjectProperties.API_BASE_URL + "/auth/admin/signIn",
                            RequestOptions.create().setData(data));
            checkStatus(apiResponse);

            JsonObject admin = new Gson().fromJson(apiResponse.text(), JsonObject.class);

            adminToken = admin.get("token").getAsString();
        }
        return adminToken;
    }

    private static APIResponse getAPIResponse(APIRequestContext requestContext, String endPoint) {
        APIResponse apiResponse = requestContext
                .get(ProjectProperties.API_BASE_URL + endPoint,
                        RequestOptions
                                .create()
                                .setHeader("Authorization", "Bearer " + userToken));
        checkStatus(apiResponse);

        return apiResponse;
    }

    public static JsonObject getActiveCourse(APIRequestContext requestContext) {
        String apiResponseJSON = getAPIResponse(requestContext, "/courses/active")
                .text();

        return new Gson().fromJson(apiResponseJSON, JsonObject.class);
    }

    public static JsonObject getStudyGuideByActiveCourse(APIRequestContext requestContext) {
        String courseId = getActiveCourse(requestContext)
                .get("id")
                .getAsString();
        String apiResponseJSON = getAPIResponse(requestContext, "/guides/courses/" + courseId)
                .text();

        return new Gson().fromJson(apiResponseJSON, JsonObject.class);
    }

    public static JsonObject getStudyGuideTable(APIRequestContext requestContext) {
        String guideId = getStudyGuideByActiveCourse(requestContext)
                .get("id")
                .getAsString();
        String apiResponseJSON = getAPIResponse(requestContext,
                "/guides/" + guideId + "/table-of-content")
                .text();

        return new Gson().fromJson(apiResponseJSON, JsonObject.class);
    }

    public static JsonObject getUnitByGuideIdAndChapterId(APIRequestContext requestContext, String guideId, String chapterid) {
        String apiResponseJSON = getAPIResponse(
                requestContext,
                "/guides/" + guideId + "/chapters/" + chapterid)
                .text();

        return new Gson().fromJson(apiResponseJSON, JsonObject.class);
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
            try {
                throw new RuntimeException(
                        "API request failed with response status " + apiResponse.status() + " with body: " + apiResponse.text());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

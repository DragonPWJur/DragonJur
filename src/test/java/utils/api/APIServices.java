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

}
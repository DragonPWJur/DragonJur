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

import static utils.api.APIData.*;

public final class APIServices {
    private static final String USER_TOKEN = LoginUtils.getUserToken();

    private static APIRequestContext createAPIRequestContext(Playwright playwright) {
        return playwright
                .request()
                .newContext(new APIRequest.NewContextOptions()
                        .setBaseURL(ProjectProperties.BASE_URL)
                );
    }

    private static void disposeAPIRequestContext(APIRequestContext requestContext) {
        if (requestContext != null) {
            requestContext.dispose();
        }
    }

    public static void closeAdminPlaywright() {
        if (ADMIN_PLAYWRIGHT != null) {
            ADMIN_PLAYWRIGHT.close();
        }
    }

    public static Playwright getAdminPlaywright() {
        return ADMIN_PLAYWRIGHT;
    }

    private String getAdminToken() {
        APIResponse authAdminSignIn = authAdminSignIn();
        APIUtils.checkStatus(authAdminSignIn, "Auth Admin Sign In");

        JsonObject adminData = APIUtils.initJsonObject(authAdminSignIn.text());

        return adminData.get("token").getAsString();
    }

    private APIResponse authAdminSignIn() {
        Map<String, String> data = new HashMap<>();
        data.put("email", ProjectProperties.ADMIN_USERNAME);
        data.put("password", ProjectProperties.ADMIN_PASSWORD);

        createAdminAPIRequestContext();

        APIResponse apiResponse =
                requestContext
                        .post(
                                AUTH_ADMIN_SIGN_IN,
                                RequestOptions.create()
                                        .setData(data)
                        );

        disposeAdminAPIRequestContext();

        return apiResponse;
    }

    static APIResponse deleteCoursesResults(Playwright userPlaywright) {
        APIRequestContext context = createAPIRequestContext(userPlaywright);

        APIResponse response = context
                .delete(
                        COURSES_RESULTS,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );

        disposeAPIRequestContext(context);

        return response;
    }

    public static APIResponse deleteCustomerPaymentMethod(Playwright userPlaywright) {
        APIRequestContext context = createAPIRequestContext(userPlaywright);

        APIResponse response = context
                .delete(
                        CUSTOMER_PAYMENT_METHOD,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );

        disposeAPIRequestContext(context);

        return response;
    }


    public static JsonObject getActiveCourse(APIRequestContext requestContext) {
        APIResponse apiResponse = requestContext
                .get(
                        ProjectProperties.API_BASE_URL + COURSES_ACTIVE,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );

        checkStatus(apiResponse, "getActiveCourse");

        return initJsonObject(apiResponse.text());
    }

    public static JsonObject getStudyGuide(APIRequestContext requestContext, String courseId) {
        APIResponse apiResponse = requestContext
                .get(
                        ProjectProperties.API_BASE_URL + GUIDES_COURSES + "/" + courseId,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );

        checkStatus(apiResponse, "getStudyGuide");

        return initJsonObject(apiResponse.text());
    }

    public static JsonObject getStudyGuideTable(APIRequestContext requestContext, String guideId) {
        APIResponse apiResponse = requestContext
                .get(
                        ProjectProperties.API_BASE_URL + GUIDES + "/" + guideId + TABLE_OF_CONTENT,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );

        checkStatus(apiResponse, "getStudyGuideTable");

        return initJsonObject(apiResponse.text());
    }

    public static JsonObject getChapter(APIRequestContext requestContext, String guideId, String chapterid) {
        APIResponse apiResponse = requestContext
                .get(
                        ProjectProperties.API_BASE_URL + GUIDES + "/" + guideId + CHAPTERS + "/" + chapterid,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );

        checkStatus(apiResponse, "getChapter");

        return initJsonObject(apiResponse.text());
    }

    public static void changeChapterText(APIRequestContext requestContext, JsonObject unit) {
        APIResponse apiResponse = requestContext
                .patch(ProjectProperties.API_BASE_URL + ADMIN_GUIDES_UNITS + "/" + unit.get("id").getAsString(),
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + getAdminToken(requestContext))
                                .setData(unit)
                );

        checkStatus(apiResponse, "changeChapterText");
    }

    public static JsonObject getPlans(APIRequestContext requestContext) {

        APIResponse apiResponse = requestContext
                .get(
                        ProjectProperties.API_BASE_URL + PLANS + "/",
                        RequestOptions
                                .create()
                                .setHeader("accept", "application/json")
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
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
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                                .setData(data)
                );

        checkStatus(apiResponse, "changeCurrentPlan");
    }

    public static JsonObject getPlanPhases(APIRequestContext requestContext, String currentPlanId) {

        APIResponse apiResponse = requestContext
                .get(
                        ProjectProperties.API_BASE_URL + PLANS + "/" + currentPlanId + PHASES,
                        RequestOptions
                                .create()
                                .setHeader("accept", "application/json")
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );

        checkStatus(apiResponse, "planPhases");

        return initJsonObject(apiResponse.text());
    }

    public static void markCheckboxesById(APIRequestContext requestContext, String markId) {

        APIResponse apiResponse = requestContext
                .post(
                        ProjectProperties.API_BASE_URL + TASKS + "/" + markId + MARK,
                        RequestOptions
                                .create()
                                .setHeader("accept", "application/json")
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );

        checkStatus(apiResponse, "markId");
    }

    public static APIResponse courseSubscribe(APIRequestContext apiRequestContext, String courseId, String period, String type) {
        Map<String, String> courseData = new HashMap<>();
        courseData.put("period", period);
        courseData.put("type", type);

        return apiRequestContext
                .post(
                        COURSES + '/' + courseId + SUBSCRIBE,
                        RequestOptions
                                .create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                                .setData(courseData)
                );
    }

    public static APIResponse setActive(APIRequestContext apiRequestContext, String courseId) {

        return apiRequestContext
                .post(
                        COURSES + '/' + courseId + SET_ACTIVE,
                        RequestOptions
                                .create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );
    }

    public static APIResponse activeCourse(APIRequestContext apiRequestContext) {

        return apiRequestContext
                .get(
                        COURSES_ACTIVE,
                        RequestOptions
                                .create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN));
    }

    public static JsonObject getFlashcardsPacks(APIRequestContext requestContext) {
        APIResponse apiResponse = requestContext
                .get(
                        ProjectProperties.API_BASE_URL + "/flashcards/packs?limit=20",
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );

        checkStatus(apiResponse, "getFlashcardsPacks");

        return initJsonObject(apiResponse.text());
    }

    public static JsonObject getFlashcardsByPack(APIRequestContext requestContext, String packId) {
        APIResponse apiResponse = requestContext
                .get(
                        ProjectProperties.API_BASE_URL + "/flashcards/packs/cards?packType=cards&packId=" + packId,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );

        checkStatus(apiResponse, "getFlashcardsByPack");

        return initJsonObject(apiResponse.text());
    }

    public static void saveFlashcardAnswer(APIRequestContext requestContext, String flashcardId, String answer) {
        Map<String, String> data = new HashMap<>();
        data.put("packType", "cards");
        data.put("answer", answer);

        APIResponse apiResponse = requestContext
                .post(
                        ProjectProperties.API_BASE_URL + "/flashcards/" + flashcardId + "/answers",
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                                .setData(data));

        checkStatus(apiResponse, "saveFlashcardAnswer");

    }

    public static void completePack(APIRequestContext requestContext, String packId) {
        APIResponse apiResponse = requestContext
                .post(
                        ProjectProperties.API_BASE_URL + "/flashcards/packs/" + packId + "/complete",
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN));

        checkStatus(apiResponse, "completePack");
    }

    public static void resetFlashCardPacks(APIRequestContext requestContext) {
        APIResponse apiResponse = requestContext
                .delete(
                        ProjectProperties.API_BASE_URL + "/flashcards/results",
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN));

        checkStatus(apiResponse, "resetFlashCardPacks");
    }
}
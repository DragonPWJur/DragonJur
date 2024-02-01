package utils.api;

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

import static utils.api.APIData.*;

public final class APIUserServices {
    public static Playwright playwrightUser;
    private static APIRequestContext requestContext;
    private static final String USER_TOKEN = LoginUtils.getUserToken();

    private static APIRequestContext createAPIUserRequestContext() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        LoggerUtils.logInfo("API: RequestContext created");

        return playwrightUser
                .request()
                .newContext(new APIRequest.NewContextOptions()
                        .setBaseURL(ProjectProperties.API_BASE_URL)
                        .setExtraHTTPHeaders(headers)
                );
    }

     static void disposeAPIUserRequestContext() {
        if (requestContext != null) {
            requestContext.dispose();
            LoggerUtils.logInfo("API: RequestContext disposed");
        }
        LoggerUtils.logInfo("API: RequestContext NOT found");
    }

    static APIResponse deleteCoursesResults() {
        requestContext = createAPIUserRequestContext();

        APIResponse response = requestContext
                .delete(
                        COURSES_RESULTS,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );

        disposeAPIUserRequestContext();

        return response;
    }

    static APIResponse deleteCustomerPaymentMethod() {
        requestContext = createAPIUserRequestContext();

        APIResponse response = requestContext
                .delete(
                        CUSTOMER_PAYMENT_METHOD,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );

        disposeAPIUserRequestContext();

        return response;
    }

    static APIResponse getCoursesActive() {
        requestContext = createAPIUserRequestContext();

        return requestContext
                .get(
                        COURSES_ACTIVE,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );
    }

    static APIResponse getGuidesCoursesId(String courseId) {
        APIRequestContext context = createAPIUserRequestContext();

        APIResponse response = context
                .get(
                        GUIDES_COURSES + "/" + courseId,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );

        disposeAPIUserRequestContext();

        return response;
    }

    static APIResponse getGuidesIdTableOfContent(String guideId) {
        APIRequestContext context = createAPIUserRequestContext();

        APIResponse response = context
                .get(
                        GUIDES + "/" + guideId + TABLE_OF_CONTENT,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );

        disposeAPIUserRequestContext();

        return response;
    }

    static APIResponse getGuidesIdChaptersId(String guideTableId, String chapterId) {
        APIRequestContext context = createAPIUserRequestContext();

        APIResponse response = context
                .get(
                        GUIDES + "/" + guideTableId + CHAPTERS + "/" + chapterId,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );

        disposeAPIUserRequestContext();

        return response;
    }

    static APIResponse getPlans() {
        APIRequestContext context = createAPIUserRequestContext();

        APIResponse response = context
                .get(
                        PLANS + "/",
                        RequestOptions.create()
                                .setHeader("accept", "application/json")
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );

        disposeAPIUserRequestContext();

        return response;
    }

    static APIResponse postPlansCurrent(String planId) {
        Map<String, Object> data = new HashMap<>();
        data.put("newPlanId", planId);

        APIRequestContext context = createAPIUserRequestContext();

        APIResponse response = context
                .post(
                        PLANS_CURRENT,
                        RequestOptions.create()
                                .setHeader("accept", "*/*")
                                .setHeader("Content-Type", "application/json")
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                                .setData(data)
                );

        disposeAPIUserRequestContext();

        return response;
    }

    static APIResponse getPlansIdPhases(String currentPlanId) {
        APIRequestContext context = createAPIUserRequestContext();

        APIResponse response = context
                .get(
                        PLANS + "/" + currentPlanId + PHASES,
                        RequestOptions.create()
                                .setHeader("accept", "application/json")
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );

        disposeAPIUserRequestContext();

        return response;
    }

    static APIResponse postTasksIdMark(String markId) {
        APIRequestContext context = createAPIUserRequestContext();

        APIResponse response = context
                .post(
                        TASKS + "/" + markId + MARK,
                        RequestOptions.create()
                                .setHeader("accept", "application/json")
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );

        disposeAPIUserRequestContext();

        return response;
    }

    static APIResponse postCoursesIdSubscribe(String courseId, String period, String type) {
        Map<String, String> courseData = new HashMap<>();
        courseData.put("period", period);
        courseData.put("type", type);

        APIRequestContext context = createAPIUserRequestContext();

        APIResponse response = context
                .post(
                        COURSES + '/' + courseId + SUBSCRIBE,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                                .setData(courseData)
                );

        disposeAPIUserRequestContext();

        return response;
    }

    static APIResponse postCoursesIdSetActive(String courseId) {
        APIRequestContext context = createAPIUserRequestContext();

        APIResponse response = context
                .post(
                        COURSES + '/' + courseId + SET_ACTIVE,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );

        disposeAPIUserRequestContext();

        return response;
    }

    static APIResponse getFlashcardsPacks(int limit) {
        APIRequestContext context = createAPIUserRequestContext();

        APIResponse response = context
                .get(
                        FLASHCARDS_PACKS + "?limit=" + limit,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );

        disposeAPIUserRequestContext();

        return response;
    }

    static APIResponse getFlashcardsPacksCardsPackTypeId(String packId) {
        APIRequestContext context = createAPIUserRequestContext();

        APIResponse response = context
                .get(
                        FLASHCARDS_PACKS + CARDS + "?packType=cards&packId=" + packId,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );

        disposeAPIUserRequestContext();

        return response;
    }

    static APIResponse postFlashcardsIDAnswers(String flashcardId, String answer) {
        Map<String, String> data = new HashMap<>();
        data.put("packType", "cards");
        data.put("answer", answer);

        APIRequestContext context = createAPIUserRequestContext();

        APIResponse response = context
                .post(
                        FLASHCARDS + "/" + flashcardId + ANSWERS,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                                .setData(data));

        disposeAPIUserRequestContext();

        return response;
    }

    static APIResponse postFlashcardsPacksIdComplete(String packId) {
        APIRequestContext context = createAPIUserRequestContext();

        APIResponse response = context
                .post(
                        FLASHCARDS_PACKS + "/" + packId + COMPLETE,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN));

        disposeAPIUserRequestContext();

        return response;
    }

    static APIResponse deleteFlashcardsResults() {
        APIRequestContext context = createAPIUserRequestContext();

        APIResponse response = context
                .delete(
                        FLASHCARDS_RESULTS,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN));

        disposeAPIUserRequestContext();

        return response;
    }
}
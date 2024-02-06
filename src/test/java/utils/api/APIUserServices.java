package utils.api;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import utils.reports.LoggerUtils;
import utils.runner.LoginUtils;
import utils.runner.ProjectProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.api.APIData.*;

public final class APIUserServices {
    public static Playwright playwrightUser;
    private static APIRequestContext requestContext;
    private static final String USER_TOKEN = LoginUtils.getUserToken();

    private static APIRequestContext createAPIUserRequestContext() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        if (requestContext != null) {
            requestContext.dispose();
        }

        return playwrightUser
                .request()
                .newContext(new APIRequest.NewContextOptions()
                        .setBaseURL(ProjectProperties.API_BASE_URL)
                        .setExtraHTTPHeaders(headers)
                );
    }

    static APIResponse deleteCoursesResults() {
        requestContext = createAPIUserRequestContext();

        return requestContext
                .delete(
                        COURSES_RESULTS,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );
    }

    static APIResponse deleteCustomerPaymentMethod() {
        requestContext = createAPIUserRequestContext();

        return requestContext
                .delete(
                        CUSTOMER_PAYMENT_METHOD,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );
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
        requestContext = createAPIUserRequestContext();

        return requestContext
                .get(
                        GUIDES_COURSES + "/" + courseId,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );
    }

    static APIResponse getGuidesIdTableOfContent(String guideId) {
        requestContext = createAPIUserRequestContext();

        return requestContext
                .get(
                        GUIDES + "/" + guideId + TABLE_OF_CONTENT,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );
    }

    static APIResponse getGuidesIdChaptersId(String guideTableId, String chapterId) {
        requestContext = createAPIUserRequestContext();

        return requestContext
                .get(
                        GUIDES + "/" + guideTableId + CHAPTERS + "/" + chapterId,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );
    }

    static APIResponse getPlans() {
        requestContext = createAPIUserRequestContext();

        return requestContext
                .get(
                        PLANS + "/",
                        RequestOptions.create()
                                .setHeader("accept", "application/json")
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );
    }

    static APIResponse postPlansCurrent(String planId) {
        Map<String, Object> data = new HashMap<>();
        data.put("newPlanId", planId);

        requestContext = createAPIUserRequestContext();

        return requestContext
                .post(
                        PLANS_CURRENT,
                        RequestOptions.create()
                                .setHeader("accept", "*/*")
                                .setHeader("Content-Type", "application/json")
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                                .setData(data)
                );
    }

    static APIResponse getPlansIdPhases(String currentPlanId) {
        requestContext = createAPIUserRequestContext();

        return requestContext
                .get(
                        PLANS + "/" + currentPlanId + PHASES,
                        RequestOptions.create()
                                .setHeader("accept", "application/json")
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );
    }

    static APIResponse postTasksIdMark(String markId) {
        requestContext = createAPIUserRequestContext();

        return requestContext
                .post(
                        TASKS + "/" + markId + MARK,
                        RequestOptions.create()
                                .setHeader("accept", "application/json")
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );
    }

    static APIResponse postCoursesIdSubscribe(String courseId, String period, String type) {
        Map<String, String> data = new HashMap<>();
        data.put("period", period);
        data.put("type", type);

        requestContext = createAPIUserRequestContext();

        return requestContext
                .post(
                        COURSES + '/' + courseId + SUBSCRIBE,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                                .setData(data)
                );
    }

    static APIResponse postCoursesIdSetActive(String courseId) {
        requestContext = createAPIUserRequestContext();

        return requestContext
                .post(
                        COURSES + '/' + courseId + SET_ACTIVE,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );
    }

    static APIResponse getFlashcardsPacks(int limit) {
        requestContext = createAPIUserRequestContext();

        return requestContext
                .get(
                        FLASHCARDS_PACKS + "?limit=" + limit,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );
    }

    static APIResponse getFlashcardsPacksCardsPackTypeId(String packId, int limit) {
        requestContext = createAPIUserRequestContext();

        return requestContext
                .get(
                        FLASHCARDS_PACKS + CARDS + "?page=1&limit=" + limit + "&packType=cards&packId=" + packId,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );
    }

    static APIResponse postFlashcardsIDAnswers(String flashcardId, String answer) {
        Map<String, String> data = new HashMap<>();
        data.put("packType", "cards");
        data.put("answer", answer);

        requestContext = createAPIUserRequestContext();

        return requestContext
                .post(
                        FLASHCARDS + "/" + flashcardId + ANSWERS,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                                .setData(data)
                );
    }

    static APIResponse postFlashcardsPacksIdComplete(String packId) {
        requestContext = createAPIUserRequestContext();

        return requestContext
                .post(
                        FLASHCARDS_PACKS + "/" + packId + COMPLETE,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );
    }

    static APIResponse deleteFlashcardsResults() {
        requestContext = createAPIUserRequestContext();

        return requestContext
                .delete(
                        FLASHCARDS_RESULTS,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );
    }

    public static APIResponse postPassagesIdFinish(String testId) {
        requestContext = createAPIUserRequestContext();

        return requestContext
                .post(
                        PASSAGES + "/" + testId + FINISH,
                        RequestOptions
                                .create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                );
    }

    public static APIResponse postPassagesIDAnswer(String testId, String optionId, String questionId) {
        Map<String, String> data = new HashMap<>();
        data.put("optionId", optionId);
        data.put("questionId", questionId);

        requestContext = createAPIUserRequestContext();

        return requestContext
                .post(
                        PASSAGES + "/" + testId + ANSWER,
                        RequestOptions
                                .create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                                .setData(data)
                );
    }

    public static APIResponse postTutorTestStart(List<String> id, int questionsAmount) {
        Map<String, Object> data = new HashMap<>();
        data.put("domainIds", id);
        data.put("questionsAmount", questionsAmount);

        requestContext = createAPIUserRequestContext();

        return requestContext
                .post(
                        TUTOR_TESTS_START,
                        RequestOptions
                                .create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                                .setData(data)
                );
    }

    public static APIResponse postQuestionDomains(String courseId) {
        Map<String, String> data = new HashMap<>();
        data.put("courseId", courseId);

        requestContext = createAPIUserRequestContext();

        return requestContext
                .post(
                        QUESTION_DOMAINS,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + USER_TOKEN)
                                .setData(data)
                );
    }
}
package utils.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.json.JSONObject;
import utils.reports.LoggerUtils;
import utils.reports.ReportUtils;
import utils.runner.ProjectProperties;

import java.util.*;

public final class APIUtils {
    private static final String _2_WEEK_PLAN = "2 Weeks";

    public static final String BRONZE_SUBSCRIPTION_ID = "f64edfa6-1aca-4d9a-9c49-8f29970790af";
    public static final String GOLD_SUBSCRIPTION_ID = "bcf37a9f-af5f-47b0-b9aa-c8e36bbd8278";
    public static final String MONTHLY = "monthly";
    public static final String BRONZE = "bronze";
    private static Playwright playwright;

    private enum answerStatus {YES, NO, KINDA}

    ;

    private static APIRequestContext createAdminAPIRequestContext() {
        playwright = Playwright.create();
        APIRequestContext APIcontext = playwright.request()
                .newContext();
        LoggerUtils.logInfo("Playwright Admin API created");

        return APIcontext;
    }

    private static void closeAdminAPIRequestContext() {
        if (playwright != null) {
            playwright.close();
            playwright = null;
            LoggerUtils.logInfo("Playwright Admin API closed");
        }
    }

    static JsonObject initJsonObject(String apiResponseBody) {
        JsonObject object = new JsonObject();
        try {
            return new Gson().fromJson(apiResponseBody, JsonObject.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }

    static void checkStatus(APIResponse apiResponse, String methodName) {
        if (apiResponse.status() < 200 || apiResponse.status() >= 300) {
            LoggerUtils.logException("EXCEPTION: API request FAILED. \n" +
                    "response status: " + apiResponse.status()
                    + "\n url: " + apiResponse.url()
                    + "\n body: " + apiResponse.text());
            Assert.fail();
        } else {
            LoggerUtils.logInfo("API: " + methodName + " " + apiResponse.status());
        }
    }

    public static void goToAdminAndChangeChapter1Unit1Text(String word, String action, APIRequestContext requestContext) {
        final String courseId = APIServices.getActiveCourse(requestContext).get("id").getAsString();
        final String studyGuideId = APIServices.getStudyGuide(requestContext, courseId).get("id").getAsString();
        final JsonObject guideTable = APIServices.getStudyGuideTable(requestContext, studyGuideId);

        JsonObject chapter1 = guideTable.getAsJsonArray("chapters").get(0).getAsJsonObject();

        chapter1 = APIServices.getChapter(requestContext, guideTable.get("id").getAsString(), chapter1.get("id").getAsString());

        JsonObject unit1 = chapter1.getAsJsonArray("units").get(0).getAsJsonObject();

        String unit1Text = unit1.getAsJsonObject("content").getAsJsonArray("blocks")
                .get(0).getAsJsonObject().getAsJsonObject("data").get("text").getAsString();

        switch (action) {
            case "add" -> unit1Text = word + unit1Text;
            case "remove" -> unit1Text = unit1Text.substring(unit1Text.indexOf(word) + unit1Text.length());
        }

        unit1.getAsJsonObject("content").getAsJsonArray("blocks").get(0)
                .getAsJsonObject().getAsJsonObject("data")
                .addProperty("text", unit1Text);

        APIRequestContext adminRequestContext = createAdminAPIRequestContext();
        APIServices.changeChapterText(adminRequestContext, unit1);
        closeAdminAPIRequestContext();
    }

    public static void deletePaymentMethod(Playwright playwright) {
        final int status = APIServices.deleteCustomerPaymentMethod(playwright).status();
        switch (status) {
            case 401 -> LoggerUtils.logError("API: ERROR: Unauthorized " + status);
            case 422 -> LoggerUtils.logInfo("API: Payment method NOT found " + status);
            case 204 -> LoggerUtils.logInfo("API: deleteCustomerPaymentMethod " + status);
            default -> {
                LoggerUtils.logException("EXCEPTION: API request FAILED " + status);
            }
        }
    }

    private static String get2WeekId(JsonObject plans) {

        try {
            JsonArray jArrayPlans = plans.getAsJsonArray("plans");

            for (int i = 0; i < jArrayPlans.size(); i++) {
                JsonObject object = jArrayPlans.get(i).getAsJsonObject();

                if (Objects.equals(object.get("name").getAsString(), _2_WEEK_PLAN)) {
                    return object.get("id").getAsString();
                }
            }
        } catch (Exception e) {
            LoggerUtils.logException("EXCEPTION: API response body, can not extract '2 Weeks' plan id.");
        }

        return "";
    }

    private static List<String> getPlanPhasesId(JsonObject planPhases) {

        List<String> checkBoxIds = new ArrayList<>();

        try {

            JsonArray jArrayItems = planPhases.getAsJsonArray("items");

            for (int i = 0; i < jArrayItems.size(); i++) {
                JsonObject object = jArrayItems.get(i).getAsJsonObject();
                JsonArray tasks = object.getAsJsonArray("tasks");

                for (int j = 0; j < tasks.size(); j++) {
                    String id = tasks.get(j).getAsJsonObject().get("id").getAsString();
                    checkBoxIds.add(id);
                }
            }

            return checkBoxIds;

        } catch (Exception e) {
            LoggerUtils.logException("EXCEPTION: FAILED to extract IDs from tasks of " + _2_WEEK_PLAN + " plan.");
        }

        return checkBoxIds;
    }

    private static void markCheckBoxes(APIRequestContext requestContext, List<String> checkBoxIds) {

        for (String markId : checkBoxIds) {
            APIServices.markCheckboxesById(requestContext, markId);
        }
    }

    @Step("Mark check boxes.")
    public static void markCheckBoxes(APIRequestContext request) {

        JsonObject plans = APIServices.getPlans(request);
        String _2WeeksPlanId = get2WeekId(plans);

        APIServices.changeCurrentPlan(request, _2WeeksPlanId);
        JsonObject planPhases = APIServices.getPlanPhases(request, _2WeeksPlanId);

        List<String> checkboxesIds = getPlanPhasesId(planPhases);

        if (checkboxesIds.isEmpty()) {
            LoggerUtils.logError("[ERROR] checkboxesIds list is empty.");
            Assert.fail();
        }

        markCheckBoxes(request, checkboxesIds);
    }

    public static APIRequestContext createApiRequestContext(Playwright playwright) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(ProjectProperties.API_BASE_URL)
                .setExtraHTTPHeaders(headers));
    }

    public static void activateBronzeSubscriptionCourse(Playwright playwright) {
        APIRequestContext apiRequestContext = createApiRequestContext(playwright);
        APIResponse apiResponse = APIServices.courseSubscribe(apiRequestContext, BRONZE_SUBSCRIPTION_ID, MONTHLY, BRONZE);

        if (apiResponse.status() == 201) {
            LoggerUtils.logInfo("API: Successfully subscribed to 'TEST AUTOMATION _DO NOT DELETE_BRONZE' course with Bronze level");
        }
        if (apiResponse.status() == 422) {
            LoggerUtils.logInfo("API: 'TEST AUTOMATION _DO NOT DELETE_BRONZE' course is exist");
            setActiveCourse(playwright, BRONZE_SUBSCRIPTION_ID);
        }
        if (apiResponse.status() != 201 && apiResponse.status() != 422) {
            LoggerUtils.logError("ERROR: " + apiResponse.status() + " - " + apiResponse.statusText());
        }
    }

    public static void setActiveCourse(Playwright playwright, String courseId) {
        APIRequestContext apiRequestContext = createApiRequestContext(playwright);
        APIResponse apiResponse = APIServices.setActive(apiRequestContext, courseId);

        JSONObject object = new JSONObject(apiResponse.text());

        if (apiResponse.status() != 201) {
            LoggerUtils.logError(apiResponse.status() + " - " + object.getString("message"));
            System.exit(1);
        } else {
            LoggerUtils.logInfo("API: Switched to " + getCourseName(courseId) + " subscription");
        }
    }

    public static String getActiveCourseId(Playwright playwright) {
        APIRequestContext apiRequestContext = createApiRequestContext(playwright);
        APIResponse apiResponse = APIServices.activeCourse(apiRequestContext);

        JSONObject object = new JSONObject(apiResponse.text());

        return object.getString("id");
    }

    public static void isGoldSubscriptionActive(Playwright playwright) {
        if (!GOLD_SUBSCRIPTION_ID.equals(getActiveCourseId(playwright))) {
            setActiveCourse(playwright, GOLD_SUBSCRIPTION_ID);
        }
        LoggerUtils.logInfo("API: The active course is on the GOLD plan." + ReportUtils.getEndLine());

    }

    public static String getCourseName(String courseId) {
        if (courseId.equals(GOLD_SUBSCRIPTION_ID)) {
            return "GOLD";
        } else if (courseId.equals(BRONZE_SUBSCRIPTION_ID)) {
            return "BRONZE";
        } else {
            return "Unknown Subscription";
        }
    }

    public static void setAnswerOptionsForFlashcardPacks(APIRequestContext requestContext, String[] stacksNames) {
        final JsonArray allPacks = APIServices.getFlashcardsPacks(requestContext).getAsJsonArray("items");

        for (JsonElement packElm : allPacks) {
            JsonObject pack = packElm.getAsJsonObject();

            if (
                    stacksNames.length != 0
                            && Arrays.asList(stacksNames).contains(pack.get("name").getAsString())
            ) {

                JsonArray flashcards = APIServices.getFlashcardsByPack(requestContext, pack.get("id").getAsString()).getAsJsonArray("items");
                for (int i = 0; i <= 2; i++) {
                    APIServices.saveFlashcardAnswer(requestContext, flashcards.get(i).getAsJsonObject().get("id").getAsString(), answerStatus.YES.name());
                }
                for (int i = 3; i <= 5; i++) {
                    APIServices.saveFlashcardAnswer(requestContext, flashcards.get(i).getAsJsonObject().get("id").getAsString(), answerStatus.NO.name());
                }
                for (int i = 6; i <= 8; i++) {
                    APIServices.saveFlashcardAnswer(requestContext, flashcards.get(i).getAsJsonObject().get("id").getAsString(), answerStatus.KINDA.name());
                }

                APIServices.completePack(requestContext, pack.get("id").getAsString());
            }
        }
    }
}
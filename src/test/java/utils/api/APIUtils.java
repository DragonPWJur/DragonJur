package utils.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import org.testng.Assert;
import utils.reports.LoggerUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class APIUtils {
    private static final String _2_WEEK_PLAN = "2 Weeks";
    private static Playwright playwright;

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

    public static void markCheckBoxes(APIRequestContext request) {

        JsonObject plans = APIServices.getPlans(request);
        String _2WeekPlanId = get2WeekId(plans);

        APIServices.changeCurrentPlan(request, _2WeekPlanId);
        JsonObject planPhases = APIServices.getPlanPhases(request, _2WeekPlanId);

        List<String> checkboxesIds = getPlanPhasesId(planPhases);

        if (checkboxesIds.isEmpty()) {
            LoggerUtils.logError("[ERROR] checkboxesIds list is empty.");
            Assert.fail();
        }

        markCheckBoxes(request, checkboxesIds);
    }
}
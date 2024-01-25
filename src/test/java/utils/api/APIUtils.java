package utils.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import utils.reports.LoggerUtils;

public final class APIUtils {

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















    //    private static List<String> getPlanPhasesId(String planPhases) {
//
//        List<String> checkBoxIds = new ArrayList<>();
//
//        try {
//
//            JSONArray jArray = new JSONObject(planPhases).getJSONArray("items");
//
//            for (int i = 0; i < jArray.length(); i++) {
//
//                JSONObject jObj = jArray.getJSONObject(i);
//                JSONArray tasks = jObj.getJSONArray("tasks");
//
//                for (int j = 0; j < tasks.length(); j++) {
//                    String id = tasks.getJSONObject(j).get("id").toString();
//                    checkBoxIds.add(id);
//                }
//            }
//
//            return checkBoxIds;
//
//        } catch (Exception e) {
//            LoggerUtils.logException("EXCEPTION: FAILED to extract IDs from tasks of " + _2_WEEK_PLAN + " plan.");
//        }
//
//        return checkBoxIds;
//    }
//
    private static String get2WeekId(JsonObject APIBody) {


//        try {
//            JSONArray jArrayPlans = new JSONObject(APIBody.text()).getJSONArray("plans");
//
//            for (int i = 0; i < jArrayPlans.length(); i++) {
//                JSONObject object = jArrayPlans.getJSONObject(i);
//                if (Objects.equals(object.get("name").toString(), _2_WEEK_PLAN)) {
//                    return object.get("id").toString();
//                }
//            }
//        } catch (Exception e) {
//            LoggerUtils.logException("EXCEPTION: API response body, can not extract '2 Weeks' plan id.");
//        }

        return "";

    }

//
//    private static boolean clickCheckBoxesById(APIRequestContext requestContext, List<String> checkBoxIds) {
//
//        String url_tasks_markId_mark;
//        boolean allPostStatus200 = true;
//
//        for(String markId : checkBoxIds) {
//
//            url_tasks_markId_mark = "/tasks/" + markId + "/mark";
//
//            APIResponse apiResponse = requestContext
//                    .post(
//                            ProjectProperties.API_BASE_URL + url_tasks_markId_mark,
//                            RequestOptions
//                                    .create()
//                                    .setHeader("accept", "application/json")
//                                    .setHeader("Authorization", "Bearer " + userToken)
//                    );
//
//            checkStatus(apiResponse);
//            if (!apiResponse.ok()) {
//                allPostStatus200 = false;
//            }
//        }
//        return allPostStatus200;
//    }












    public static int clickAllCheckBoxes(APIRequestContext request) {

        JsonObject plans = APIServices.getPlans(request);

        String _2WeekPlanId = get2WeekId(plans);
//
//        postAPICurrentPlan(requestContext, _2WeekPlanId);
//
//        APIResponse planPhases = getPlanPhases(requestContext, _2WeekPlanId);
//
//        List<String> checkboxIds = getPlanPhasesId(planPhases.text());
//
//        if (checkboxIds.isEmpty()) {
//            LoggerUtils.logError("[ERROR] checkboxId list is empty.");
//        }
//
//        if (!clickCheckBoxesById(requestContext, checkboxIds)) {
//            LoggerUtils.logError("[ERROR] checkboxes are not checked.");
//
//            return 0;
//        }

//        return checkboxIds.size();
        return 1;

    }
}
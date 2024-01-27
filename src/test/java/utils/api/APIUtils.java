package utils.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import org.json.JSONObject;
import utils.reports.LoggerUtils;
import utils.runner.ProjectProperties;

import java.util.HashMap;
import java.util.Map;

public final class APIUtils {

    public static final String BRONZE_SUBSCRIPTION = "f64edfa6-1aca-4d9a-9c49-8f29970790af";
    public static final String GOLD_SUBSCRIPTION = "bcf37a9f-af5f-47b0-b9aa-c8e36bbd8278";
    public static final String MONTHLY = "monthly";
    public static final String BRONZE = "bronze";
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

    public static APIRequestContext createApiRequestContext(Playwright playwright) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(ProjectProperties.API_BASE_URL)
                .setExtraHTTPHeaders(headers));
    }

    public static void bronzeCourseSubscription(Playwright playwright) {
        APIRequestContext apiRequestContext = createApiRequestContext(playwright);
        APIResponse apiResponse = APIServices.courseSubscribe(apiRequestContext, BRONZE_SUBSCRIPTION, MONTHLY, BRONZE);

        if (apiResponse.status() == 201) {
            LoggerUtils.logInfo("API: Successfully subscribed to 'TEST AUTOMATION _DO NOT DELETE_BRONZE' course with Bronze level");
        }
        if (apiResponse.status() == 422) {
            LoggerUtils.logInfo("API: 'TEST AUTOMATION _DO NOT DELETE_BRONZE' course is exist");
            setActiveCourse(playwright, BRONZE_SUBSCRIPTION);
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

    public static String getIdActiveCourse(Playwright playwright) {
        APIRequestContext apiRequestContext = createApiRequestContext(playwright);
        APIResponse apiResponse = APIServices.activeCourse(apiRequestContext);

        JSONObject object = new JSONObject(apiResponse.text());

        return object.getString("id");
    }

    public static void checkIfGoldIsActive(Playwright playwright) {

        if (!GOLD_SUBSCRIPTION.equals(getIdActiveCourse(playwright))) {
            setActiveCourse(playwright, GOLD_SUBSCRIPTION);
        }
        LoggerUtils.logInfo("API: The user currently has a Gold subscription");
    }

    public static String getCourseName(String courseId) {
        if (courseId.equals(GOLD_SUBSCRIPTION)) {
            return "GOLD";
        } else if (courseId.equals(BRONZE_SUBSCRIPTION)) {
            return "BRONZE";
        } else {
            return "Unknown Subscription";
        }
    }
}
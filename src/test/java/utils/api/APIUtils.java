package utils.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import utils.reports.LoggerUtils;

public final class APIUtils {

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

        APIServices.changeChapterText(requestContext, unit1);
    }
}
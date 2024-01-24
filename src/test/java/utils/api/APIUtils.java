package utils.api;

import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequestContext;

public class APIUtils {

    public static void changeChapter1Unit1TextViaAPI(String testText, boolean isAdd, APIRequestContext requestContext) {

        JsonObject guideTable =
                APIServices
                        .getStudyGuideTable(requestContext);

        JsonObject chapter1 =
                guideTable
                        .getAsJsonArray("chapters")
                        .get(0).getAsJsonObject();

        chapter1 = APIServices
                .getUnitByGuideIdAndChapterId(
                        requestContext,
                        guideTable.get("id").getAsString(),
                        chapter1.get("id").getAsString());

        JsonObject unit1 = chapter1
                .getAsJsonArray("units")
                .get(0).getAsJsonObject();

        String text = unit1
                .getAsJsonObject("content")
                .getAsJsonArray("blocks")
                .get(0).getAsJsonObject()
                .getAsJsonObject("data")
                .get("text").getAsString();

        if (isAdd) {
            text = testText + text;
        } else {
            text = text.substring(text.indexOf(testText) + testText.length());
        }

        unit1
                .getAsJsonObject("content")
                .getAsJsonArray("blocks")
                .get(0).getAsJsonObject()
                .getAsJsonObject("data")
                .addProperty("text", text);

        APIServices.changeChapter1Unit1TextViaAPIGSON(requestContext, unit1);
    }
}
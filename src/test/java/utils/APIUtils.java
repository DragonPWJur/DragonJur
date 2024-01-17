package utils;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class APIUtils {
    private static APIRequest request;
    private static APIRequestContext  requestContext;
    private static String userToken;

    public static void parseUserToken() {
        String relative = "tokens/user.json";
        String jsonString;

        try {
            jsonString = new String(Files.readAllBytes(Paths.get(relative)));
            JSONObject apiLoginResponseJSON = new JSONObject(jsonString);
            String jsonValue = apiLoginResponseJSON.getJSONArray("origins").getJSONObject(0).getJSONArray("localStorage").getJSONObject(2).getString("value");
            userToken = new JSONObject((new JSONObject(jsonValue)).getString("auth")).getString("accessToken");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cleanData(Playwright playwright) {
        request = playwright.request();
        requestContext = request.newContext();
        requestContext.delete(ProjectProperties.API_BASE_URL + TestData.RESET_COURSE_RESULTS_END_POINT,
                RequestOptions.create()
                        .setHeader("Authorization","Bearer " + userToken));
    }
}

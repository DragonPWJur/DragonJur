package utils;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class APIUtils {
    private static APIRequest request;
    private static APIRequestContext  requestContext;
    private static String userToken;

    private static final String DIRECTION_ID = "da57d06c-d744-43ba-bb74-df4e4d02b8a9";

    public static void customerSignIn(Playwright playwright) {
        request = playwright.request();
        requestContext = request.newContext();
        Map<String, String> requestBody = new HashMap();
        requestBody.put("directionId", DIRECTION_ID);
        requestBody.put("email", ProjectProperties.USERNAME);
        requestBody.put("password", ProjectProperties.PASSWORD);

        String apiLoginResponse = requestContext.post(ProjectProperties.API_BASE_URL + TestData.AUTH_CUSTOMER_SIGN_IN_END_POINT,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setData(requestBody)).text();
        JSONObject apiLoginResponseJSON = new JSONObject(apiLoginResponse);
        userToken = apiLoginResponseJSON.getString("token");
    }

    public static void cleanData() {
        requestContext.delete(ProjectProperties.API_BASE_URL + TestData.RESET_COURSE_RESULTS_END_POINT,
                RequestOptions.create()
                        .setHeader("Authorization","Bearer " + userToken));
    }
}

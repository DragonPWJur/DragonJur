package utils.api;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.reports.LoggerUtils;
import utils.runner.LoginUtils;
import utils.runner.ProjectProperties;

import java.util.ArrayList;
import java.util.List;

public final class APIServices {
    private static final String AUTH_CUSTOMER_SIGN_IN_END_POINT = "/auth/customer/signIn";
    private static final String RESET_COURSE_RESULTS_END_POINT = "/courses/results";
    private static final String PLANS_End_Point = "/plans";

    private static final String userToken = LoginUtils.getUserToken();

    public static void cleanData(Playwright playwright) {
        APIRequest request = playwright.request();
        APIRequestContext requestContext = request.newContext();

        requestContext
                .delete(
                        ProjectProperties.API_BASE_URL + RESET_COURSE_RESULTS_END_POINT,
                        RequestOptions.create().setHeader("Authorization", "Bearer " + userToken)
                );
    }

    private static String getCurrentPlanId(APIRequestContext requestContext) {

        APIResponse apiResponse = requestContext
                .get(
                        ProjectProperties.API_BASE_URL + PLANS_End_Point,
                                RequestOptions
                                        .create()
                                        .setHeader("accept", "application/json")
                                        .setHeader("Authorization", "Bearer " + userToken)
                );

        checkStatus(apiResponse);

//        return new JSONObject(
//                requestContext
//                        .get(
//                                ProjectProperties.API_BASE_URL + PLANS_End_Point,
//                                RequestOptions
//                                        .create()
//                                        .setHeader("accept", "application/json")
//                                        .setHeader("Authorization", "Bearer " + userToken)
//                        )
//                        .text()
//        )
//                .get("currentPlanId")
//                .toString();
    }

    private static void checkStatus(APIResponse apiResponse) {
        if (apiResponse.status() < 200 || apiResponse.status() >= 300) {
            LoggerUtils.logException("EXCEPTION: API request FAILED with response status "
                    + apiResponse.status() + ", url: " + apiResponse.url() + " and body: " + apiResponse.text());
        } else {
            LoggerUtils.logInfo("INFO: API request with response status "
                    + apiResponse.status() + " , url: " + apiResponse.url() + ".");
        }
    }

    private static String getPlanPhases(APIRequestContext requestContext, String currentPlanId) {

        String url = "/plans/" + currentPlanId + "/phases";

        APIResponse response = requestContext
                .get(
                        ProjectProperties.API_BASE_URL + url,
                        RequestOptions
                                .create()
                                .setHeader("accept", "application/json")
                                .setHeader("Authorization", "Bearer " + userToken)
                );

        return response.text();
    }

    private static List<String> getPlanPhasesId(String planPhases) {

        JSONArray jArray = new JSONObject(planPhases).getJSONArray("items");

        List<String> checkBoxIds = new ArrayList<>();

        for (int i = 0; i < jArray.length(); i++) {

            JSONObject jObj = jArray.getJSONObject(i);
            JSONArray tasks = jObj.getJSONArray("tasks");

            for (int j = 0; j < tasks.length(); j++) {
                String id = tasks.getJSONObject(j).get("id").toString();
                checkBoxIds.add(id);
            }
        }
//
//        if (checkBoxIds.isEmpty()) {
//            log("API: List of getPlanPhasesId is empty");
//        }
        return checkBoxIds;
    }

    private static void clickCheckBoxesById(APIRequestContext requestContext, List<String> checkBoxIds) {

        String url;

        for(String markId : checkBoxIds) {

            url = "/tasks/" + markId + "/mark";

            APIResponse response = requestContext
                    .post(
                            ProjectProperties.API_BASE_URL + url,
                            RequestOptions
                                    .create()
                                    .setHeader("accept", "application/json")
                                    .setHeader("Authorization", "Bearer " + userToken)
                    );
        }
    }

    public static int clickAllCheckBoxes(APIRequestContext requestContext) {

        String currentPlanId = getCurrentPlanId(requestContext);

        String planPhases = getPlanPhases(requestContext, currentPlanId);

        List<String> checkboxIds = getPlanPhasesId(planPhases);
        if (checkboxIds.isEmpty()) {
            LoggerUtils.logError("ERROR: checkboxId list is empty.");
        }
        clickCheckBoxesById(requestContext, checkboxIds);

        return checkboxIds.size();
    }
}

package utils;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static utils.LoggerUtils.log;
import static utils.LoginUtils.getUserToken;

public class APIServises {

    public static void cleanData(Playwright playwright) {
        APIRequest request = playwright.request();
        APIRequestContext requestContext = request.newContext();
        requestContext.delete(ProjectProperties.API_BASE_URL + TestData.RESET_COURSE_RESULTS_END_POINT,
                RequestOptions.create()
                        .setHeader("Authorization", "Bearer " + getUserToken()));
    }

    public static String getCoursePlans(Playwright playwright) {

        String url = "/plans";

        APIRequest request = playwright.request();
        APIRequestContext requestContext = request.newContext();

        return new JSONObject(
                requestContext
                        .get(
                                ProjectProperties.API_BASE_URL + url,
                                RequestOptions
                                        .create()
                                        .setHeader("accept", "application/json")
                                        .setHeader("Authorization", "Bearer " + getUserToken())
                        )
                        .text()
        )
                .get("currentPlanId")
                .toString();
    }

    public static String getPlanPhases(Playwright playwright, String currentPlan) {

        String url = "/plans/" + currentPlan + "/phases";

        APIRequest request = playwright.request();
        APIRequestContext requestContext = request.newContext();

        APIResponse response = requestContext
                .get(
                        ProjectProperties.API_BASE_URL + url,
                        RequestOptions
                                .create()
                                .setHeader("accept", "application/json")
                                .setHeader("Authorization", "Bearer " + getUserToken())
                );

        return response.text();
    }

    public static List<String> getPlanPhasesId(Playwright playwright, String currentPlan) {

        JSONObject jsonObj = new JSONObject(getPlanPhases(playwright, currentPlan));
        JSONArray jArray = jsonObj.getJSONArray("items");

        List<String> checkBoxIds = new ArrayList<>();

        for (int i = 0; i < jArray.length(); i++) {

            JSONObject jObj = jArray.getJSONObject(i);
            JSONArray tasks = jObj.getJSONArray("tasks");

            for (int j = 0; j < tasks.length(); j++) {
                String id = tasks.getJSONObject(j).get("id").toString();
                checkBoxIds.add(id);
            }
        }

        if (checkBoxIds.isEmpty()) {
            log("API: List of getPlanPhasesId is empty");
        }

        return checkBoxIds;
    }

    public static void clickAllCheckBoxes(Playwright playwright, String currentPlan) {

        APIRequest request;
        APIRequestContext requestContext;
        List<String> checkBoxIds = getPlanPhasesId(playwright, currentPlan);

        String url;

        for(String markId : checkBoxIds) {

            url = "/tasks/" + markId + "/mark";

            request = playwright.request();
            requestContext = request.newContext();
            APIResponse response = requestContext
                    .post(
                            ProjectProperties.API_BASE_URL + url,
                            RequestOptions
                                    .create()
                                    .setHeader("accept", "application/json")
                                    .setHeader("Authorization", "Bearer " + getUserToken())
                    );
        }
    }
}
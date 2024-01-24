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

    protected static String getCurrentPlanId(Playwright playwright) {

        final String plansEndPoint = "/plans";

        APIRequest request = playwright.request();
        APIRequestContext requestContext = request.newContext();

        return new JSONObject(
                requestContext
                        .get(
                                ProjectProperties.API_BASE_URL + plansEndPoint,
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

    protected static String getPlanPhases(Playwright playwright, String currentPlanId) {

        String url = "/plans/" + currentPlanId + "/phases";

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

    protected static List<String> getPlanPhasesId(String planPhases) {

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

        if (checkBoxIds.isEmpty()) {
            log("API: List of getPlanPhasesId is empty");
        }
        return checkBoxIds;
    }

    protected static void clickCheckBoxesById(Playwright playwright, List<String> checkBoxIds) {

        APIRequest request;
        APIRequestContext requestContext;

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

    public static void clickAllCheckBoxes(Playwright playwright) {

        String currentPlanId = getCurrentPlanId(playwright);
        System.out.println("********* coursePlans ************" + currentPlanId);

        String planPhases = getPlanPhases(playwright, currentPlanId);
        System.out.println("********* planPhases ************" + planPhases);

        List<String> checkBoxIds = getPlanPhasesId(planPhases);
        clickCheckBoxesById(playwright, checkBoxIds);
    }
}


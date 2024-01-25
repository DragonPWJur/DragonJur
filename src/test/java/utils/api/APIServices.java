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

import java.util.*;

public final class APIServices {
    private static final String AUTH_CUSTOMER_SIGN_IN_END_POINT = "/auth/customer/signIn";
    private static final String RESET_COURSE_RESULTS_END_POINT = "/courses/results";
    private static final String PLANS_END_POINT = "/plans";
    private static final String CURRENT_PLAN_END_POINT = "/plans/current";
    private static final String _2_WEEK_PLAN = "2 Weeks";

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

    private static void checkStatus(APIResponse apiResponse) {
        if (apiResponse.status() < 200 || apiResponse.status() >= 300) {
            LoggerUtils.logException("EXCEPTION: API request FAILED with response status "
                    + apiResponse.status() + ", url: " + apiResponse.url() + " and body: " + apiResponse.text());
        } else {
            LoggerUtils.logInfo("INFO: API request with response status "
                    + apiResponse.status() + " , url: " + apiResponse.url() + ".");
        }
    }

    private static APIResponse getAPIResponseBodyPlans(APIRequestContext requestContext) {

        APIResponse apiResponse = requestContext
                    .get(
                            ProjectProperties.API_BASE_URL + PLANS_END_POINT,
                            RequestOptions
                                    .create()
                                    .setHeader("accept", "application/json")
                                    .setHeader("Authorization", "Bearer " + userToken)
                    );

        checkStatus(apiResponse);

        return apiResponse;
    }

    private static String get2WeekId(APIResponse APIBody) {

        try {
            JSONArray jArrayPlans = new JSONObject(APIBody.text()).getJSONArray("plans");

            for (int i = 0; i < jArrayPlans.length(); i++) {
                JSONObject object = jArrayPlans.getJSONObject(i);
                if (Objects.equals(object.get("name").toString(), _2_WEEK_PLAN)) {
                    return object.get("id").toString();
                }
            }
        } catch (Exception e) {
            LoggerUtils.logException("EXCEPTION: API response body, can not extract '2 Weeks' plan id.");
        }

        return "";
    }

    private static void postAPICurrentPlan(APIRequestContext requestContext, String _2WeeksPlanId) {

        Map<String, Object> data = new HashMap();
        data.put("newPlanId", _2WeeksPlanId);

        APIResponse apiResponse = requestContext
                .post(
                        ProjectProperties.API_BASE_URL + CURRENT_PLAN_END_POINT,
                        RequestOptions
                                .create()
                                .setHeader("accept", "*/*")
                                .setHeader("Content-Type", "application/json")
                                .setHeader("Authorization", "Bearer " + userToken)
                                .setData(data)
                );

        checkStatus(apiResponse);
    }

    private static APIResponse getPlanPhases(APIRequestContext requestContext, String currentPlanId) {

        final String URL_CURRENT_PLAN_PHASES = "/plans/" + currentPlanId + "/phases";

        APIResponse apiResponse = requestContext
                .get(
                        ProjectProperties.API_BASE_URL + URL_CURRENT_PLAN_PHASES,
                        RequestOptions
                                .create()
                                .setHeader("accept", "application/json")
                                .setHeader("Authorization", "Bearer " + userToken)
                );

        checkStatus(apiResponse);

        return apiResponse;
    }

    private static List<String> getPlanPhasesId(String planPhases) {

        List<String> checkBoxIds = new ArrayList<>();

        try {

            JSONArray jArray = new JSONObject(planPhases).getJSONArray("items");

            for (int i = 0; i < jArray.length(); i++) {

                JSONObject jObj = jArray.getJSONObject(i);
                JSONArray tasks = jObj.getJSONArray("tasks");

                for (int j = 0; j < tasks.length(); j++) {
                    String id = tasks.getJSONObject(j).get("id").toString();
                    checkBoxIds.add(id);
                }
            }

            return checkBoxIds;

        } catch (Exception e) {
            LoggerUtils.logException("EXCEPTION: FAILED to extract IDs from tasks of " + _2_WEEK_PLAN + " plan.");
        }

        return checkBoxIds;
    }

    private static boolean clickCheckBoxesById(APIRequestContext requestContext, List<String> checkBoxIds) {

        String url_tasks_markId_mark;
        boolean allPostStatus200 = true;

        for(String markId : checkBoxIds) {

            url_tasks_markId_mark = "/tasks/" + markId + "/mark";

            APIResponse apiResponse = requestContext
                    .post(
                            ProjectProperties.API_BASE_URL + url_tasks_markId_mark,
                            RequestOptions
                                    .create()
                                    .setHeader("accept", "application/json")
                                    .setHeader("Authorization", "Bearer " + userToken)
                    );

            checkStatus(apiResponse);
            if (!apiResponse.ok()) {
                allPostStatus200 = false;
            }
        }
        return allPostStatus200;
    }

    public static int clickAllCheckBoxes(APIRequestContext requestContext) {

        APIResponse apiResponse = getAPIResponseBodyPlans(requestContext);

        String _2WeekPlanId = get2WeekId(apiResponse);

        postAPICurrentPlan(requestContext, _2WeekPlanId);

        APIResponse planPhases = getPlanPhases(requestContext, _2WeekPlanId);

        List<String> checkboxIds = getPlanPhasesId(planPhases.text());

        if (checkboxIds.isEmpty()) {
            LoggerUtils.logError("[ERROR] checkboxId list is empty.");
        }

        if (!clickCheckBoxesById(requestContext, checkboxIds)) {
            LoggerUtils.logError("[ERROR] checkboxes are not checked.");

            return 0;
        }

        return checkboxIds.size();

    }
}

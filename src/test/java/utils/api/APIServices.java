package utils.api;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import utils.runner.LoginUtils;
import utils.runner.ProjectProperties;

public final class APIServices {
    private static final String AUTH_CUSTOMER_SIGN_IN_END_POINT = "/auth/customer/signIn";
    private static final String RESET_COURSE_RESULTS_END_POINT = "/courses/results";

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
}

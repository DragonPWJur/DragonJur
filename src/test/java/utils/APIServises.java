package utils;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

import static utils.LoginUtils.getUserToken;

public class APIServises {
    public static void cleanData(Playwright playwright) {
        APIRequest request = playwright.request();
        APIRequestContext requestContext = request.newContext();
        requestContext.delete(ProjectProperties.API_BASE_URL + TestData.RESET_COURSE_RESULTS_END_POINT,
                RequestOptions.create()
                        .setHeader("Authorization", "Bearer " + getUserToken()));
    }
}

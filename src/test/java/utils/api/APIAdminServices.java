package utils.api;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import utils.runner.ProjectProperties;

public final class APIAdminServices {
    private static final Playwright ADMIN_PLAYWRIGHT = Playwright.create();
    private static APIRequestContext requestContext;

    private static String adminToken;

    private void createAdminAPIRequestContext() {
        requestContext = ADMIN_PLAYWRIGHT.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(ProjectProperties.BASE_URL)
        );
    }

    private static void disposeAdminAPIRequestContext() {
        if (requestContext != null) {
            requestContext.dispose();
            requestContext = null;
        }
    }
}

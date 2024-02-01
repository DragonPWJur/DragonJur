package utils.api;

import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import utils.reports.LoggerUtils;
import utils.runner.ProjectProperties;

import java.util.HashMap;
import java.util.Map;

import static utils.api.APIData.ADMIN_GUIDES_UNITS;
import static utils.api.APIData.AUTH_ADMIN_SIGN_IN;

public final class APIAdminServices {
    private static final Playwright playwrightAdmin = createPlaywrightAdmin();
    private static APIRequestContext requestContext;
    private static final String adminToken = getAdminToken();

    private static Playwright createPlaywrightAdmin() {
        LoggerUtils.logInfo("API: playwrightAdmin created");

        return Playwright.create();
    }

    private static void createAdminAPIRequestContext() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        requestContext = playwrightAdmin
                .request()
                .newContext(new APIRequest.NewContextOptions()
                        .setBaseURL(ProjectProperties.API_BASE_URL)
                        .setExtraHTTPHeaders(headers)
        );
        LoggerUtils.logInfo("API: AdminAPIRequestContext created");
    }

    private static void disposeAdminAPIRequestContext() {
        if (requestContext != null) {
            requestContext.dispose();
            requestContext = null;
            LoggerUtils.logInfo("API: AdminAPIRequestContext disposed");
        }
    }

    public static void closePlaywrightAdmin() {
        if (playwrightAdmin != null) {
            playwrightAdmin.close();
            LoggerUtils.logInfo("API: playwrightAdmin closed");
        }
    }

    public static Playwright getPlaywrightAdmin() {

        return playwrightAdmin;
    }

    private static APIResponse authAdminSignIn() {
        Map<String, String> data = new HashMap<>();
        data.put("email", ProjectProperties.ADMIN_USERNAME);
        data.put("password", ProjectProperties.ADMIN_PASSWORD);

        createAdminAPIRequestContext();

        APIResponse apiResponse = requestContext
                        .post(
                                AUTH_ADMIN_SIGN_IN,
                                RequestOptions.create()
                                        .setData(data)
                        );

        disposeAdminAPIRequestContext();

        return apiResponse;
    }

     static APIResponse patchAdminGuidesUnitsId(String unitId, JsonObject unit) {
        createAdminAPIRequestContext();

        APIResponse response = requestContext
                .patch(ADMIN_GUIDES_UNITS + "/" + unitId,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + adminToken)
                                .setData(unit)
                );

        disposeAdminAPIRequestContext();

        return response;
    }

    private static String getAdminToken() {
        APIResponse authAdminSignIn = authAdminSignIn();
        APIUtils.checkStatus(authAdminSignIn, "Auth Admin Sign In");

        JsonObject adminData = APIUtils.initJsonObject(authAdminSignIn.text());

        return adminData.get("token").getAsString();
    }
}

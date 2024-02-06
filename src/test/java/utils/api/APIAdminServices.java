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
    private static String adminToken = getAdminToken();

    private static Playwright createPlaywrightAdmin() {
        LoggerUtils.logInfo("API: playwrightAdmin created");

        return Playwright.create();
    }

    private static APIRequestContext createAdminAPIRequestContext() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        if (requestContext != null) {
            requestContext.dispose();
            LoggerUtils.logInfo("API: Admin APIRequestContext disposed");
        }

        return playwrightAdmin
                .request()
                .newContext(new APIRequest.NewContextOptions()
                        .setBaseURL(ProjectProperties.API_BASE_URL)
                        .setExtraHTTPHeaders(headers)
                );
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

    private static APIResponse postAuthAdminSignIn() {
        Map<String, String> data = new HashMap<>();
        data.put("email", ProjectProperties.ADMIN_USERNAME);
        data.put("password", ProjectProperties.ADMIN_PASSWORD);

        requestContext = createAdminAPIRequestContext();

        return requestContext
                .post(
                        AUTH_ADMIN_SIGN_IN,
                        RequestOptions.create()
                                .setData(data)
                );
    }

    static APIResponse patchAdminGuidesUnitsId(String unitId, JsonObject unit) {
        requestContext = createAdminAPIRequestContext();

        return requestContext
                .patch(ADMIN_GUIDES_UNITS + "/" + unitId,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + adminToken)
                                .setData(unit)
                );
    }

    private static String getAdminToken() {
        APIResponse postAuthAdminSignIn = postAuthAdminSignIn();
        APIUtils.checkStatus(postAuthAdminSignIn, "postAuthAdminSignIn");

        JsonObject adminData = APIUtils.initJsonObject(postAuthAdminSignIn.text());

        return adminData.get("token").getAsString();
    }
}

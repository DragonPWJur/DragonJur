package utils.api;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import org.json.JSONObject;
import utils.runner.GmailUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static utils.api.APIServicesForNewCustomer.*;
import static utils.api.APIUtils.GOLD_SUBSCRIPTION_ID;
import static utils.reports.LoggerUtils.logError;
import static utils.reports.LoggerUtils.logInfo;
import static utils.runner.ProjectProperties.*;

public class APINewCustomerUtils {
    public static final String EMAIL_END_PART = "@gmail.com";
    private static final String username = COMMON_EMAIL_PART + getNumberFromDateAndTime() + EMAIL_END_PART;
    private static String adminToken;
    private static String inviteCode;
    private static String password;
    private static String customerId;

    public static String getPassword() {
        return password;
    }

    public static String getUsername() {
        return username;
    }

    public static String getNumberFromDateAndTime() {
        String number = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
        logInfo("Customer number is " + number);

        return number;
    }

    private static APIRequestContext apiRequestContext(Playwright playwright) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return playwright
                .request()
                .newContext(
                        new APIRequest.NewContextOptions()
                                .setBaseURL(API_BASE_URL)
                                .setExtraHTTPHeaders(headers)
                );
    }

    private static void signInAdminAPI(APIRequestContext apiRequestContext) {
        APIResponse response = signInAdmin(apiRequestContext, ADMIN_USERNAME, ADMIN_PASSWORD);

        JSONObject object = new JSONObject(response.text());

        if (response.status() != 201) {
            logError("API: " + object.getInt("statusCode") + " - " + object.getString("message"));
        } else {
            adminToken = object.getString("token");
            logInfo("API: Admin Signed In successfully");
        }
    }

    private static void inviteCustomerAPI(APIRequestContext apiRequestContext, String adminToken) {
        inviteCode = null;

        APIResponse response = inviteCustomer(apiRequestContext, username, GOLD_SUBSCRIPTION_ID, adminToken);

        JSONObject object = new JSONObject(response.text());

        if (response.status() != 201) {
            logError("ERROR API: " + object.getInt("statusCode") + " - " + object.getString("message"));
        } else {
            inviteCode = object.getString("code");
            logInfo("API: Invite was sent to customer");
            logInfo("API: Invite code was saved");
        }
    }

    private static void signUpPromoAPI(APIRequestContext apiRequestContext, String inviteCode) throws Exception {
        Thread.sleep(5000);
        password = GmailUtils.extractPasswordFromEmail(GmailUtils.getGmailService(), username);

        APIResponse response = signUpPromo(apiRequestContext, username, password, inviteCode);

        JSONObject object = new JSONObject(response.text());

        if (response.status() != 201) {
            logError("ERROR API: " + object.getInt("statusCode") + " - " + object.getString("message"));
        } else {
            customerId = object.getJSONObject("customer").getString("id");
            logInfo("API: Customer was successfully Signed Up");
            logInfo("API: Customer ID saved");
        }
    }

    public static void deleteCustomerAPI(APIRequestContext apiRequestContext, String customerId, String adminToken) {
        APIResponse response = deleteCustomer(apiRequestContext, customerId, adminToken);

        if (response.status() != 204) {
            JSONObject object = new JSONObject(response.text());
            logError("ERROR API: " + object.getInt("statusCode") + " - " + object.getString("message") + " - Customer was not deleted");
        } else {
            logInfo("API: Customer was successfully deleted");
        }
    }

    public static void createNewCustomerIsServerRun() throws Exception {
        if(isServerRun()) {
            Playwright playwright = Playwright.create();
            APIRequestContext apiRequestContext = apiRequestContext(playwright);

            signInAdminAPI(apiRequestContext);
            inviteCustomerAPI(apiRequestContext, adminToken);
            signUpPromoAPI(apiRequestContext, inviteCode);

            apiRequestContext.dispose();
            playwright.close();
        }
    }

    public static void deleteNewCustomerIsServerRun() {
        if (isServerRun()) {
            Playwright playwright = Playwright.create();
            APIRequestContext apiRequestContext = apiRequestContext(playwright);

            deleteCustomerAPI(apiRequestContext, customerId, adminToken);

            apiRequestContext.dispose();
            playwright.close();
        }
    }
}

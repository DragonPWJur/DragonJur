package utils.api;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import utils.runner.LoginUtils;
import utils.runner.ProjectProperties;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static utils.reports.LoggerUtils.logInfo;
import static utils.runner.ProjectProperties.*;

public final class APIServices {
    private static final String AUTH_CUSTOMER_SIGN_IN_END_POINT = "/auth/customer/signIn";
    private static final String RESET_COURSE_RESULTS_END_POINT = "/courses/results";
    private static final String EMAIL_END_PART = "@gmail.com";
    public static final String COURSE_ID = "bcf37a9f-af5f-47b0-b9aa-c8e36bbd8278";
    public static final String DIRECTION_ID = "da57d06c-d744-43ba-bb74-df4e4d02b8a9";
    private static final String numericPart = getNumberFromDateAndTime();
    public static final String username = COMMON_EMAIL_PART + numericPart + EMAIL_END_PART;
    private static final String userToken = LoginUtils.getUserToken();

    public static String getNumericPart() {
        return numericPart;
    }

    public static String getNumberFromDateAndTime() {
        String number = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        logInfo("Customer number is " + number);

        return number;
    }

    public static void cleanData(Playwright playwright) {
        APIRequest request = playwright.request();
        APIRequestContext requestContext = request.newContext();

        requestContext
                .delete(
                        ProjectProperties.API_BASE_URL + RESET_COURSE_RESULTS_END_POINT,
                        RequestOptions
                                .create()
                                .setHeader("Authorization", "Bearer " + userToken)
                );
    }

    public static APIResponse signInAdmin(APIRequestContext apiRequestContext) {
        Map<String, String> signInAdminData = new HashMap<>();
        signInAdminData.put("email", ADMIN_USERNAME);
        signInAdminData.put("password", ADMIN_PASSWORD);

        return apiRequestContext
                .post(
                        "/auth/admin/signIn",
                        RequestOptions
                                .create()
                                .setData(signInAdminData)
                );
    }

    public static APIResponse inviteCustomer(APIRequestContext apiRequestContext, String adminToken) {
        Map<String, String> inviteCustomerData = new HashMap<>();
        inviteCustomerData.put("customerEmail", username);
        inviteCustomerData.put("courseId", COURSE_ID);

        return apiRequestContext
                .post(
                        "/admin/customer-invites",
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + adminToken)
                                .setData(inviteCustomerData)
                );
    }

    public static APIResponse signUpPromo(APIRequestContext apiRequestContext, String password, String inviteCode) {
        Map<String, String> loginCustomerData = new HashMap<>();
        loginCustomerData.put("email", username);
        loginCustomerData.put("password", password);
        loginCustomerData.put("code", inviteCode);

        return apiRequestContext
                .post(
                        "/auth/customer/signUp/promo",
                        RequestOptions
                                .create()
                                .setData(loginCustomerData)
                );
    }

    public static APIResponse deleteCustomer(APIRequestContext apiRequestContext, String customerId, String adminToken) {

        return apiRequestContext
                .delete(
                        "/admin/customers/" + customerId,
                        RequestOptions
                                .create()
                                .setHeader("Authorization", "Bearer " + adminToken)
                );
    }
}

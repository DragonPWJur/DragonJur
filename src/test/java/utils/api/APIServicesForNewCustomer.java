package utils.api;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;

import java.util.HashMap;
import java.util.Map;

public class APIServicesForNewCustomer {
    private static final String AUTH_ADMIN_SIGN_IN = "/auth/admin/signIn";
    private static final String ADMIN_CUSTOMER_INVITES = "/admin/customer-invites";
    private static final String AUTH_CUSTOMER_SIGN_UP_PROMO = "/auth/customer/signUp/promo";
    private static final String ADMIN_CUSTOMERS = "/admin/customers/";

    public static APIResponse signInAdmin(APIRequestContext apiRequestContext, String adminUsername, String adminPassword) {
        Map<String, String> signInAdminData = new HashMap<>();
        signInAdminData.put("email", adminUsername);
        signInAdminData.put("password", adminPassword);

        return apiRequestContext
                .post(
                        AUTH_ADMIN_SIGN_IN,
                        RequestOptions
                                .create()
                                .setData(signInAdminData)
                );
    }

    public static APIResponse inviteCustomer(APIRequestContext apiRequestContext, String username, String courseId, String adminToken) {
        Map<String, String> inviteCustomerData = new HashMap<>();
        inviteCustomerData.put("customerEmail", username);
        inviteCustomerData.put("courseId", courseId);

        return apiRequestContext
                .post(
                        ADMIN_CUSTOMER_INVITES,
                        RequestOptions.create()
                                .setHeader("Authorization", "Bearer " + adminToken)
                                .setData(inviteCustomerData)
                );
    }

    public static APIResponse signUpPromo(APIRequestContext apiRequestContext, String username, String password, String inviteCode) {
        Map<String, String> loginCustomerData = new HashMap<>();
        loginCustomerData.put("email", username);
        loginCustomerData.put("password", password);
        loginCustomerData.put("code", inviteCode);

        return apiRequestContext
                .post(
                        AUTH_CUSTOMER_SIGN_UP_PROMO,
                        RequestOptions
                                .create()
                                .setData(loginCustomerData)
                );
    }

    public static APIResponse deleteCustomer(APIRequestContext apiRequestContext, String customerId, String adminToken) {

        return apiRequestContext
                .delete(
                        ADMIN_CUSTOMERS + customerId,
                        RequestOptions
                                .create()
                                .setHeader("Authorization", "Bearer " + adminToken)
                );
    }
}

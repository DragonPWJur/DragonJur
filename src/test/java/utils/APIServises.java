package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import utils.api.obj.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static utils.LoginUtils.getUserToken;

public class APIServises {
    public static void cleanData(Playwright playwright) {
        APIRequest request = playwright.request();
        APIRequestContext requestContext = request.newContext();
        requestContext.delete(ProjectProperties.API_BASE_URL + TestData.RESET_COURSE_RESULTS_END_POINT,
                RequestOptions.create()
                        .setHeader("Authorization", "Bearer " + getUserToken()));
    }

    private static String getAdminToken(Playwright playwright) {
        APIRequest request = playwright.request();
        APIRequestContext requestContext = request.newContext();
        Map<String, String> data = new HashMap<>();

        APIResponse apiResponse = requestContext.post(ProjectProperties.API_BASE_URL + "/auth/admin/signIn",
                RequestOptions.create().setData(data));

        Admin admin = (Admin) getObjectFromResponseBody(apiResponse, Admin.class);
        return admin.getToken();
    }

    private static APIResponse getResponseBody(Playwright playwright, String endPoint) {
        APIRequest request = playwright.request();
        APIRequestContext requestContext = request.newContext();
        APIResponse apiResponse = requestContext.get(ProjectProperties.API_BASE_URL + endPoint,
                RequestOptions.create()
                        .setHeader("Authorization", "Bearer " + getUserToken()));

        if (apiResponse.status() < 200 || apiResponse.status() >= 300) {
            try {
                throw new RuntimeException("API request failed with response status " + apiResponse.status() + " with body: " + apiResponse.text());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return apiResponse;
    }

    private static Object getObjectFromResponseBody(APIResponse responseBody, Class clazz) {
        Object obj = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            obj = objectMapper.readValue(responseBody.text().getBytes(), clazz);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        return obj;
    }


    public static Course getActiveCourse(Playwright playwright) {
        APIResponse responseBody = getResponseBody(playwright, "/courses/active");
        Course course = (Course) getObjectFromResponseBody(responseBody, Course.class);

        return course;
    }

    public static Guide getStudyGuideByActiveCourse(Playwright playwright) {
        Course course = getActiveCourse(playwright);
        APIResponse responseBody = getResponseBody(playwright, "/guides/courses/" + course.getId());

        Guide guide = (Guide) getObjectFromResponseBody(responseBody, Guide.class);

        return guide;
    }

    public static GuideTable getStudyGuideTable(Playwright playwright) {
        Guide guide = getStudyGuideByActiveCourse(playwright);
        APIResponse responseBody = getResponseBody(playwright, "/guides/" + guide.getId() + "/table-of-content");

        GuideTable guideTablee = (GuideTable) getObjectFromResponseBody(responseBody, GuideTable.class);
        return guideTablee;
    }

    public static Chapter getUnitByGuideIdAndChapterId(Playwright playwright, String guideId, String chapterid) {
        APIResponse responseBody = getResponseBody(playwright, "/guides/" + guideId + "/chapters/" + chapterid);
        Chapter chapter = null;
        try {
            chapter = (Chapter) getObjectFromResponseBody(responseBody, Chapter.class);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        return chapter;
    }

    public static Unit editUnitByGuideIdAndChapterId(Playwright playwright, Unit unit) {
        APIRequest request = playwright.request();
        APIRequestContext requestContext = request.newContext();
        Map<String, String> data = new HashMap<>();
        data.put("name", unit.getName());
        APIResponse apiResponse = requestContext.patch(ProjectProperties.API_BASE_URL + "/admin/guides/units/" + unit.getId(),
                RequestOptions.create()
                        .setHeader("Authorization", "Bearer " + getAdminToken(playwright))
                        .setData(data));


        Unit newunit = (Unit) getObjectFromResponseBody(apiResponse, Unit.class);

        return newunit;
    }

}

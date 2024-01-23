package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import utils.api.entity.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
        data.put("email", ProjectProperties.ADMINUSERNAME);
        data.put("password", ProjectProperties.ADMINPASSWORD);

        APIResponse apiResponse = requestContext.post(ProjectProperties.API_BASE_URL + "/auth/admin/signIn",
                RequestOptions.create().setData(data));
        checkStatus(apiResponse);

        Admin admin = (Admin) getObjectFromResponseBody(apiResponse, Admin.class);
        return admin.getToken();
    }

    private static APIResponse getAPIResponse(Playwright playwright, String endPoint) {
        APIRequest request = playwright.request();
        APIRequestContext requestContext = request.newContext();

        APIResponse apiResponse = requestContext.get(ProjectProperties.API_BASE_URL + endPoint,
                RequestOptions.create()
                        .setHeader("Authorization", "Bearer " + getUserToken()));
        checkStatus(apiResponse);

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
        APIResponse responseBody = getAPIResponse(playwright, "/courses/active");
        Course course = (Course) getObjectFromResponseBody(responseBody, Course.class);

        return course;
    }

    public static Guide getStudyGuideByActiveCourse(Playwright playwright) {
        Course course = getActiveCourse(playwright);
        APIResponse responseBody = getAPIResponse(playwright, "/guides/courses/" + course.getId());

        Guide guide = (Guide) getObjectFromResponseBody(responseBody, Guide.class);

        return guide;
    }

    public static GuideTable getStudyGuideTable(Playwright playwright) {
        Guide guide = getStudyGuideByActiveCourse(playwright);
        APIResponse responseBody = getAPIResponse(playwright, "/guides/" + guide.getId() + "/table-of-content");

        GuideTable guideTablee = (GuideTable) getObjectFromResponseBody(responseBody, GuideTable.class);
        return guideTablee;
    }

//    public static GuideTable getStudyGuideTable(APIResponse response) {
//
//        GuideTable guideTablee = (GuideTable) getObjectFromResponseBody(response, GuideTable.class);
//        return guideTablee;
//    }


    public static Chapter getUnitByGuideIdAndChapterId(Playwright playwright, String guideId, String chapterid) {
        APIResponse responseBody = getAPIResponse(playwright, "/guides/" + guideId + "/chapters/" + chapterid);
        Chapter chapter = null;
        try {
            chapter = (Chapter) getObjectFromResponseBody(responseBody, Chapter.class);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        return chapter;
    }

    public static void editUnitByGuideIdAndChapterId(Playwright playwright, Unit unit) {
        APIRequest request = playwright.request();
        APIRequestContext requestContext = request.newContext();

        Unit sendUnit = new Unit();
        Data data = new Data();
        data.setText(unit.getContent().getBlocks().get(0).getData().getText());
        Block block = new Block();
        block.setData(data);
        Content content = new Content();
        content.setBlocks(List.of(block));
        sendUnit.setContent(unit.getContent());

        APIResponse apiResponse = requestContext.patch(ProjectProperties.API_BASE_URL + "/admin/guides/units/" + unit.getId(),
                RequestOptions.create()
                        .setHeader("Authorization", "Bearer " + getAdminToken(playwright))
                        .setData(sendUnit));
        checkStatus(apiResponse);
    }

    private static void checkStatus(APIResponse apiResponse){
        if (apiResponse.status() < 200 || apiResponse.status() >= 300) {
            try {
                throw new RuntimeException("API request failed with response status " + apiResponse.status() + " with body: " + apiResponse.text());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

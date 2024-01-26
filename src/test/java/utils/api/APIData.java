package utils.api;

import static utils.api.APIUtils.getNumericPart;
import static utils.runner.ProjectProperties.COMMON_EMAIL_PART;

public class APIData {

    public static final String EMAIL_END_PART = "@gmail.com";
    public static final String COURSE_ID_GOLD = "bcf37a9f-af5f-47b0-b9aa-c8e36bbd8278";

    public static final String username = COMMON_EMAIL_PART + getNumericPart()+ EMAIL_END_PART;
}

package utils.api.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Guide {

    private String createdAt;
    private String updatedAt;
    private String id;
    private String courseId;
    private String cheatSheetUrl;
    private String textbookUrl;

}
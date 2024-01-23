package utils.api.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Unit {

    private String id;
    private String name;
    private Object notes;
    private Content content;
    private Integer orderIndex;
    private String createdAt;
    private String updatedAt;
    private String chapterId;

}


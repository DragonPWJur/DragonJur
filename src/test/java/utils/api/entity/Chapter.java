package utils.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Chapter {

    private String id;
    private String name;
    private String audioUrl;
    private String quizId;
    private Object notes;
    private Integer orderIndex;
    private List<Unit> units = new LinkedList<>();
}

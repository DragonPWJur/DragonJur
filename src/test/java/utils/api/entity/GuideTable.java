package utils.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class GuideTable {

    private String id;
    private List<Chapter> chapters = new LinkedList<>();

}
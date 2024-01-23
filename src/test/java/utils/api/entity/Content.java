package utils.api.entity;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Content {

    private Long time;
    private List<Block> blocks = new ArrayList<>();
    private String version;
}
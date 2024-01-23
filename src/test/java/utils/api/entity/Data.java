package utils.api.entity;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Data {

    private String text;
    private List<String> items = new ArrayList<>();
    private String style;
    private Object file;
    private String caption;
    private Boolean stretched;
    private Boolean floatToLeft;
    private Boolean floatToRight;

}

package utils.api.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Silver {

    private String id;
    private Integer amount;
    private String currency;
    private Integer oldAmount;
    private String description;


}
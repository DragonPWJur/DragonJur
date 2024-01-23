package utils.api.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Purchase {

    private String id;
    private Integer amount;
    private String currency;
    private Object oldAmount;
    private String description;

}

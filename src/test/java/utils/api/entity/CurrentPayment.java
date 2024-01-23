package utils.api.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentPayment {

    private String type;
    private String level;
    private String period;
    private String status;
    private String activeFrom;
    private String activeTo;

}
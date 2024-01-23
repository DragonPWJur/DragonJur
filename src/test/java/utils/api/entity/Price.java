package utils.api.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Price {

    private String id;
    private String courseId;
    private Purchase purchase;
    private MonthlySubscription monthlySubscriptions;
    private AnnualSubscription annualSubscriptions;


}

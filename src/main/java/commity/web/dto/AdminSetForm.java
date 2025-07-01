package commity.web.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdminSetForm {
    private Integer solvedCountGoal;
    private Integer pointLimit;
    private Integer pointPerProblem;
    private Integer code;
}

package commity.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AdminSetForm {
    private Integer solvedCountGoal;
    private Integer pointLimit;
    private Integer pointPerProblem;
    private Integer code;
}

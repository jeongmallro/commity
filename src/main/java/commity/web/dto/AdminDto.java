package commity.web.dto;

import commity.web.domain.Admin;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDto {
    private Integer solvedCountGoal;
    private Integer pointPerProblem;
    private Integer pointLimit;

    public static AdminDto from(Admin admin) {
        AdminDto adminDto = new AdminDto();
        adminDto.solvedCountGoal = admin.getSolvedCountGoal();
        adminDto.pointPerProblem = admin.getPointPerProblem();
        adminDto.pointLimit = admin.getPointLimit();
        return adminDto;
    }
}

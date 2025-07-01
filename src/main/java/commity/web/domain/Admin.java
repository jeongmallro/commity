package commity.web.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer solvedCountGoal;
    private Integer pointPerProblem;
    private Integer pointLimit;
    private Integer code;

    public static Admin ofCreate(int code) {
        Admin admin = new Admin();
        admin.code = code;
        admin.solvedCountGoal = 0;
        admin.pointPerProblem = 0;
        admin.pointLimit = 0;
        return admin;
    }

    public void updateSet(int solvedCountGoal,
                          int pointLimit,
                          int pointPerProblem) {
        this.solvedCountGoal = solvedCountGoal;
        this.pointLimit = pointLimit;
        this.pointPerProblem = pointPerProblem;
    }
}

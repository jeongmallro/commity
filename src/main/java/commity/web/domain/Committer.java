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
public class Committer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private Integer point;
    private Integer solvedCount;
    private Integer totalSolvedCount;

    public static Committer ofCreate(String username) {
        Committer committer = new Committer();
        committer.username = username;
        committer.solvedCount = 0;
        committer.point = 0;
        committer.totalSolvedCount = 0;

        return committer;
    }

    public int update(int addedCount, int removedCount, int pointPerProblem) {
        int change = addedCount - removedCount;

        if (this.solvedCount + change < 0) {
            throw new RuntimeException("문제 해결 개수는 0개 미만일 수 없습니다.");
        }

        this.solvedCount += change;
        this.totalSolvedCount += change;
        this.point += change * pointPerProblem;
        return this.solvedCount;
    }

    public int updatePoint(int point) {
        this.point += point;
        return this.point;
    }

    public void updateTotalSolvedCount(int totalSolvedCount) {
        this.totalSolvedCount += totalSolvedCount;
    }

    public void initSolvedCount() {
        this.solvedCount = 0;
    }
}

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
    private Long id;

    private String username;

    private Integer point;

    private Integer solvedCount;

    public Committer(String username) {
        this.username = username;
        this.solvedCount = 0;
        this.point = 0;
    }

    public void updateSolvedCount(int addedCount, int removedCount) {
        if (solvedCount + addedCount - removedCount < 0) {
            throw new RuntimeException("문제 해결 개수는 0개 미만일 수 없습니다.");
        }

        solvedCount = solvedCount + addedCount - removedCount;
    }

}

package commity.web.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
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
        solvedCount = solvedCount + addedCount - removedCount;
    }

}

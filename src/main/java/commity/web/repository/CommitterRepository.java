package commity.web.repository;

import commity.web.domain.Committer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommitterRepository extends JpaRepository<Committer, Integer> {

    Optional<Committer> findByUsername(String username);
}

package commity.web.service;

import commity.web.controller.WebhookPayload;
import commity.web.domain.Committer;
import commity.web.repository.CommitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WebhookService {

    private final CommitterRepository committerRepository;

    @Transactional
    public void update(List<WebhookPayload.Commit> commits) {
        commits.forEach(commit -> {
            if (!commit.getMessage().startsWith("Merge")) {

                String authorName = commit.getAuthor().getUsername();
                String committerName = commit.getCommitter().getUsername();

                if (authorName.equals(committerName)) {
                    Committer committer = committerRepository.findByUsername(authorName).orElseGet(() -> {
                                Committer newCommitter = new Committer(committerName);
                                return committerRepository.save(newCommitter);
                            }
                    );
                    int addedCount = commit.getAdded().size();
                    int removedCount = commit.getRemoved().size();

                    committer.updateSolvedCount(addedCount, removedCount);
                }
            }
        });
    }

    /*
    1. author == committer
    message가 Merge로 시작하는 경우, Get a Commit API 호출을 통해 parents가 2개 이상인지 확인 -> 넘긴다.
    2. added -> solvedCount++
    3. removed -> solvedCount--

     */

}

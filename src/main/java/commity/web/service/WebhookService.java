package commity.web.service;

import commity.web.controller.WebhookPayload;
import commity.web.domain.Committer;
import commity.web.repository.CommitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class WebhookService {

    private final CommitterRepository committerRepository;

    @Transactional
    public List<CommitterUpdateResponse> update(List<WebhookPayload.Commit> commits) {
        List<CommitterUpdateResponse> committers = new ArrayList<>();
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

                    int updatedSolvedCount = committer.updateSolvedCount(addedCount, removedCount);
                    committers.add(new CommitterUpdateResponse(committer.getUsername(), updatedSolvedCount));
                }
            }
        });
        return committers;
    }

    @Transactional
    public List<CommitterUpdateResponse> getCommitterUpdateResponses() {
        Committer committerA = committerRepository.findByUsername("userA").orElseGet(() -> {
                    Committer newCommitter = new Committer("userA");
                    return committerRepository.save(newCommitter);
                }
        );
        Committer committerB = committerRepository.findByUsername("userB").orElseGet(() -> {
                    Committer newCommitter = new Committer("userB");
                    return committerRepository.save(newCommitter);
                }
        );

        Random random = new Random();
        int updatedA = committerA.updateSolvedCount(random.nextInt(), 3);
        int updatedB = committerB.updateSolvedCount(random.nextInt(), 3);

        List<CommitterUpdateResponse> list = new ArrayList<>();
        CommitterUpdateResponse userA = new CommitterUpdateResponse("userA", updatedA);
        list.add(userA);
        CommitterUpdateResponse userB = new CommitterUpdateResponse("userB", updatedB);
        list.add(userB);
        return list;
    }

}

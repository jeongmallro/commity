package commity.web.service;

import commity.web.dto.*;
import commity.web.domain.Committer;
import commity.web.repository.CommitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WebhookService {

    private final CommitterRepository committerRepository;
    private final AdminService adminService;

    @Value("${github.token}")
    private String token;

    private static final String CONTENT_PATH = "https://api.github.com/repos/psCodingTest/CodingTest/git/trees/main";
    private static final String COMMIT_PATH = "https://api.github.com/repos/psCodingTest/CodingTest/commits?path=";

    //TODO: "Merge"로 시작하는 커밋 메시지의 경우 Get a Commit API 호출을 통해 parents 개수 확인
    @Transactional
    public List<CommitterUpdateDto> update(List<WebhookPayload.Commit> commits) {
        List<CommitterUpdateDto> committers = new ArrayList<>();
        commits.forEach(commit -> {
            if (!commit.getMessage().startsWith("Merge")) {

                String authorName = commit.getAuthor().getUsername();
                String committerName = commit.getCommitter().getUsername();

                if (authorName.equals(committerName)) {
                    Committer committer = findOrCreate(authorName);
                    int addedCount = commit.getAdded().size();
                    int removedCount = commit.getRemoved().size();

                    Integer pointPerProblem = adminService.getAdmin().getPointPerProblem();
                    int updatedSolvedCount = committer.update(addedCount, removedCount, pointPerProblem);

                    committers.add(new CommitterUpdateDto(committer.getUsername(), updatedSolvedCount));
                }
            }
        });
        return committers;
    }

    @Transactional
    public void getCommits() {
        List<TreeResponse.Tree> folderTrees = getTree(CONTENT_PATH);

        for (TreeResponse.Tree tree : folderTrees) {
            String folder = tree.getPath();
            if (folder.endsWith("회차")) {
                List<TreeResponse.Tree> subFolderTrees = getTree(tree.getUrl());

                for (TreeResponse.Tree subFolderTree : subFolderTrees) {
                    String subFolder = subFolderTree.getPath();
                    List<TreeResponse.Tree> solvedProblems = getTree(subFolderTree.getUrl());

                    if (solvedProblems == null || solvedProblems.isEmpty()) {
                        continue;
                    }

                    CommitPayload.Author author = getUsername(folder, subFolder, solvedProblems);
                    if (author == null) {
                        continue;
                    }

                    Committer committer = findOrCreate(author.getLogin());
                    int count = getCount(solvedProblems);

                    committer.updateTotalSolvedCount(count);
                }
            }

        }
    }

    @Transactional
    public Committer findOrCreate(String username) {
        return committerRepository.findByUsername(username).orElseGet(() -> {
                    Committer newCommitter = Committer.ofCreate(username);
                    return committerRepository.save(newCommitter);
                }
        );
    }

    private CommitPayload.Author getUsername(String folder, String subFolder, List<TreeResponse.Tree> solvedProblems) {
        String file = solvedProblems.stream()
            .map(TreeResponse.Tree::getPath)
            .filter(this::isMarkdown)
            .findFirst().orElseThrow(NoSuchElementException::new);

        List<CommitPayload> commitPayload = getCommitPayload(folder, subFolder, file);
        return commitPayload.get(0).getAuthor();
    }

    private List<CommitPayload> getCommitPayload(String folder, String subFolder, String file) {
        String url = COMMIT_PATH + folder + "/" + subFolder + "/" + file;
        return callGithubRestApi(url, new ParameterizedTypeReference<List<CommitPayload>>(){});
    }

    private <T> T callGithubRestApi(String url, ParameterizedTypeReference<T> responseType) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.github+json");
        headers.set("Authorization", "Bearer " + token);
        headers.set("X-GitHub-Api-Version", "2022-11-28");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, responseType).getBody();
    }

    private List<TreeResponse.Tree> getTree(String url) {
        return callGithubRestApi(url, new ParameterizedTypeReference<TreeResponse>(){}).getTree();
    }

    private boolean isMarkdown(String path) {
        return path.endsWith(".md");
    }

    private int getCount(List<TreeResponse.Tree> solvedProblems) {
        return (int) solvedProblems.stream()
                .filter(p -> isMarkdown(p.getPath()))
                .count();
    }
}

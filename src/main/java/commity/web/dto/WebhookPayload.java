package commity.web.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class WebhookPayload {
    private List<Commit> commits;

    @Getter
    public static class Commit {
        private String id;
        private String message;
        private Author author;
        private Committer committer;
        private List<String> added;
        private List<String> removed;
        private List<String> modified;
    }

    @Getter
    public static class Author {
        private String username;
    }

    @Getter
    public static class Committer {
        private String username;
    }
}

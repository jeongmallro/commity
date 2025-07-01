package commity.web.dto;

import lombok.Getter;

@Getter
public class CommitPayload {
    private Author author;

    @Getter
    public static class Author {
        private String login;
    }

}

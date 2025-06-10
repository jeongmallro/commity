package commity.web.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommitterUpdateResponse {
    private String username;
    private Integer solvedCount;
}

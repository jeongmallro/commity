package commity.web.dto;

import lombok.Getter;
import org.antlr.v4.runtime.tree.Tree;

import java.util.List;

@Getter
public class TreeResponse {
    private List<TreeResponse.Tree> tree;

    @Getter
    public static class Tree{
        private String path;
        private String url;
    }
}

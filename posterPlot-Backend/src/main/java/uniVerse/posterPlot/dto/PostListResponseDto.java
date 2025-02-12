package uniVerse.posterPlot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostListResponseDto {
    private Integer postId;
    private String title;

    public PostListResponseDto(Integer postId, String title) {
        this.postId = postId;
        this.title = title;
    }
}

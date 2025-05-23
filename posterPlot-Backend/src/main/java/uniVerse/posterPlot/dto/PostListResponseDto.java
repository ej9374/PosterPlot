package uniVerse.posterPlot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostListResponseDto {
    @Schema(example = "123")
    private Integer postId;

    @Schema(example = "title")
    private String title;

    @Schema(example = "1")
    private Integer userId;

    @Schema(example = "user123")
    private String id;


    public PostListResponseDto(Integer postId, String title, Integer userId, String id) {
        this.postId = postId;
        this.title = title;
        this.userId = userId;
        this.id = id;
    }
}

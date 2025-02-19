package uniVerse.posterPlot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uniVerse.posterPlot.entity.Genre;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {

    private Integer postId;
    private String id;
    private String title;
    private String content;
    private Integer totalLikes;
    private Genre genre;

    public PostResponseDto(Integer postId, String id, String title, String content, Integer totalLikes, Genre genre) {
        this.postId = postId;
        this.id = id;
        this.title = title;
        this.content = content;
        this.totalLikes = totalLikes;
        this.genre = genre;
    }
}

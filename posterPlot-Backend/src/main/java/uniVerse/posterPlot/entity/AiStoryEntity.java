package uniVerse.posterPlot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ai_story")
public class AiStoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ai_story_id")
    private Integer aiStoryId;

    @Column(name = "story")
    private String story;

    @ManyToOne
    @JoinColumn(name = "movie_list_id", referencedColumnName = "movie_list_id")
    private MovieListEntity movieList;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity user;

    public AiStoryEntity() {}

    public AiStoryEntity(String story, MovieListEntity movieList, UserEntity user) {
        this.story = story;
        this.movieList = movieList;
        this.user = user;
    }
}

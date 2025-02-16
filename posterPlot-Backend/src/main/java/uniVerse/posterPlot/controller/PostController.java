package uniVerse.posterPlot.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniVerse.posterPlot.dto.PostListResponseDto;
import uniVerse.posterPlot.dto.PostRequestDto;
import uniVerse.posterPlot.entity.Genre;
import uniVerse.posterPlot.entity.PostEntity;
import uniVerse.posterPlot.entity.UserEntity;
import uniVerse.posterPlot.service.PostService;
import uniVerse.posterPlot.util.SecurityUtil;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @Operation(summary = "게시글 작성", description = "유저가 새로운 게시글을 작성합니다.")
    @PostMapping("/create")
    public ResponseEntity<String> createPost(@Valid @RequestBody PostRequestDto requestDto){
        try {
            UserEntity user = SecurityUtil.getAuthenticatedUser();
            postService.createPost(user, requestDto);
            return ResponseEntity.ok("게시글이 성공적으로 작성되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러가 발생했습니다.");
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostEntity> getPost(@Valid @PathVariable Integer postId){
        PostEntity post = postService.getPost(postId);
        if(post == null){}
        return ResponseEntity.ok(post);
    }

    @GetMapping("/list")
    public ResponseEntity<List<PostListResponseDto>> getPosts(@RequestParam String type){
        List<PostListResponseDto> posts;
        switch (type){
            case "latest":
                posts = postService.getPostsLatest();
                break;
            case "oldest":
                posts = postService.getPostsOldest();
                break;
            case "likes":
                posts = postService.getPostsByLikes();
                break;
            // 좋아요 개수 상위 3개만 띄우기
            case "top_likes":
                posts = postService.getPostsTopLikes();
                break;
            default:
                throw new IllegalArgumentException("잘못된 요청 타입입니다.");
        }
        return ResponseEntity.ok(posts);
    }

    // 유저별 게시글 조회
    @GetMapping("/list/my")
    public ResponseEntity<List<PostListResponseDto>> getUserPosts(){
        Integer userId = SecurityUtil.getAuthenticatedUser().getUserId();
        List<PostListResponseDto> posts = postService.getPostsByUser(userId);
        return ResponseEntity.ok(posts);
    }

    // 장르별 조회
    @GetMapping("/list/{genre}")
    public ResponseEntity<List<PostListResponseDto>> getGenrePosts(@PathVariable Genre genre){
        List<PostListResponseDto> posts = postService.getPostsByGenre(genre);
        return ResponseEntity.ok(posts);
    }

    // 글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@Valid @PathVariable Integer postId){
        Integer userId = SecurityUtil.getAuthenticatedUser().getUserId();
        postService.deletePost(postId, userId);
    }

    // 좋아요 기능


}

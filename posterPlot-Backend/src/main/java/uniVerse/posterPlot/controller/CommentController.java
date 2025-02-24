package uniVerse.posterPlot.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniVerse.posterPlot.dto.CommentListResponseDto;
import uniVerse.posterPlot.entity.UserEntity;
import uniVerse.posterPlot.service.CommentService;
import uniVerse.posterPlot.util.SecurityUtil;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/comment")
@Tag(name = "CommentController", description = "CommentController API 목록")
public class CommentController {

    private final CommentService commentService;

    //댓글 작성
    @PostMapping("/create")
    public ResponseEntity<String> createComment(@RequestParam String content, @RequestParam Integer postId){
        try{
            UserEntity user = SecurityUtil.getAuthenticatedUser();
            if (user == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 유저가 아닙니다.");
            }
            commentService.createComment(user, postId, content);
            return ResponseEntity.ok("댓글이 성공적으로 작동되었습니다.");
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러가 발생했습니다.");
        }
    }

    //댓글 삭제
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Integer commentId) {
        try {
            Integer userId = SecurityUtil.getAuthenticatedUser().getUserId();
            commentService.deleteComment(commentId, userId);
            return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러가 발생했습니다.");
        }
    }


    //댓글 띄우기
    @GetMapping("/list/{postId}")
    public ResponseEntity<List<CommentListResponseDto>> getCommentList(@PathVariable Integer postId){
        try{
            List<CommentListResponseDto> comments = commentService.getComments(postId);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

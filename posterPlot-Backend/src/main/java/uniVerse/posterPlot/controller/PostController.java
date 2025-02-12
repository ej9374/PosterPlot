package uniVerse.posterPlot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniVerse.posterPlot.dto.PostRequestDto;
import uniVerse.posterPlot.entity.UserEntity;
import uniVerse.posterPlot.util.SecurityUtil;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    // 게시글 작성
    @PostMapping("/create")
    public ResponseEntity<String> createPost(@Valid @RequestBody PostRequestDto requestDto){
        Integer userId = SecurityUtil.getAuthenticatedUserId();

    }


    // 유저별 루틴 조회
    @GetMapping("/list/{userId}")
}

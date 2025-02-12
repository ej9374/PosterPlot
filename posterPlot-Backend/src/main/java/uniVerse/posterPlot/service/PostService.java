package uniVerse.posterPlot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uniVerse.posterPlot.dto.PostListResponseDto;
import uniVerse.posterPlot.dto.PostRequestDto;
import uniVerse.posterPlot.entity.Genre;
import uniVerse.posterPlot.entity.PostEntity;
import uniVerse.posterPlot.entity.UserEntity;
import uniVerse.posterPlot.repository.PostRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    // 글 작성 메서드
    public void createPost(UserEntity user, PostRequestDto requestDto) {

        String title = requestDto.getTitle();
        String content = requestDto.getContent();
        Integer totalLike = 0;
        Genre genre = requestDto.getGenre();

        PostEntity post = new PostEntity(user, title, content, totalLike, genre);
        postRepository.save(post);
    }

    // 전달받은 postId로 postEntity 찾아서 전부 반환
    public PostEntity getPost(Integer postId) {
        PostEntity post = postRepository.findByPostId(postId);
        if (post == null) {
            throw new RuntimeException("게시글을 조회할 수 없습니다.");
        }
        return post;
    }

    // 모든 글 조회하기 (최신순 / postId 내림차순)
    public List<PostListResponseDto> getPostsLatest(){
        List<Integer> postIds = postRepository.findAllRecent();
        if (postIds.isEmpty())
            return Collections.emptyList();

        List<String> titles = getTitlesByPostIds(postIds);
        List<PostListResponseDto> responseList = new ArrayList<>();

        for (int i = 0; i < postIds.size(); i++) {
            responseList.add(new PostListResponseDto(postIds.get(i), titles.get(i)));
        }
        return responseList;
    }

    // 오래된순 (postId 오름차순)
    public List<PostListResponseDto> getPostsOldest(){
        List<Integer> postIds = postRepository.findAllOldest();
        if (postIds.isEmpty())
            return Collections.emptyList();

        List<String> titles = getTitlesByPostIds(postIds);
        List<PostListResponseDto> responseList = new ArrayList<>();

        for (int i = 0; i < postIds.size(); i++) {
            responseList.add(new PostListResponseDto(postIds.get(i), titles.get(i)));
        }
        return responseList;
    }

    // 좋아요순
    public List<PostListResponseDto> getPostsByLikes(){
        List<Integer> postIds = postRepository.findAllByLikes();
        if (postIds.isEmpty())
            return Collections.emptyList();

        List<String> titles = getTitlesByPostIds(postIds);
        List<PostListResponseDto> responseList = new ArrayList<>();

        for (int i = 0; i < postIds.size(); i++) {
            responseList.add(new PostListResponseDto(postIds.get(i), titles.get(i)));
        }
        return responseList;
    }

    // 상위 좋아요 3개의 게시글 띄우기
    public List<PostListResponseDto> getPostsTopLikes(){

    }

    // 유저별 글 조회하기 -> 마이페이지
    public List<PostListResponseDto> getPostsByUser(Integer userId){
        List<Integer> postIds = postRepository.findAllByUserId(userId);
        if (postIds.isEmpty())
            return Collections.emptyList();

        List<String> titles = getTitlesByPostIds(postIds);
        List<PostListResponseDto> responseList = new ArrayList<>();

        for (int i = 0; i < postIds.size(); i++) {
            responseList.add(new PostListResponseDto(postIds.get(i), titles.get(i)));
        }
        return responseList;
    }

    // 장르별 글 조회
    public List<PostListResponseDto> getPostsByGenre(Genre genre){
        List<Integer> postIds = postRepository.findAllByGenre(genre);
        if (postIds.isEmpty())
            return Collections.emptyList();

        List<String> titles = getTitlesByPostIds(postIds);
        List<PostListResponseDto> responseList = new ArrayList<>();

        for (int i = 0; i < postIds.size(); i++) {
            responseList.add(new PostListResponseDto(postIds.get(i), titles.get(i)));
        }
        return responseList;
    }

    // 글 삭제하기 (사용자 토큰 필터 확인 필요)
    public void deletePost(Integer postId, Integer userId) {
        PostEntity post = postRepository.findByPostId(postId);

        if (post == null) {
            throw new RuntimeException("해당 게시글을 찾을 수 없습니다.");
        }
        if (!post.getUser().getUserId().equals(userId)){
            throw new RuntimeException("게시글 삭제 권한이 없습니다.");
        }
        postRepository.delete(postId);
    }

    public List<String> getTitlesByPostIds(List<Integer> postIds){
        return postRepository.findTitlesByPostIds(postIds);
    }

    // 게시글 좋아요 기능
    public

}

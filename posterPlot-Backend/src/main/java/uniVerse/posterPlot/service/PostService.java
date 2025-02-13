package uniVerse.posterPlot.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uniVerse.posterPlot.dto.PostListResponseDto;
import uniVerse.posterPlot.dto.PostRequestDto;
import uniVerse.posterPlot.entity.Genre;
import uniVerse.posterPlot.entity.PostEntity;
import uniVerse.posterPlot.entity.UserEntity;
import uniVerse.posterPlot.repository.PostLikeRepository;
import uniVerse.posterPlot.repository.PostRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    // 글 작성 메서드
    @Transactional
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
            throw new EntityNotFoundException("게시글을 찾을 수 없습니다. postId: " + postId);
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

    // 상위 좋아요 3개의 게시글 띄우기 (좋아요 10개 이상의 글 들중)
    public List<PostListResponseDto> getPostsTopLikes(){
        List<Integer> postIds = postRepository.findTopLikesPost();
        if (postIds.isEmpty())
            return Collections.emptyList();

        List<String> title = getTitlesByPostIds(postIds);
        List<PostListResponseDto> responseList = new ArrayList<>();

        for (int i = 0; i < postIds.size(); i++) {
            responseList.add(new PostListResponseDto(postIds.get(i), title.get(i)));
        }
        return responseList;
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
    @Transactional
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
    @Transactional
    public void likePost(Integer postId, Integer userId) {
        if (postLikeRepository.isExistLike(postId, userId)){
            throw new RuntimeException("이미 좋아요를 누르셨습니다.");
        }
        postLikeRepository.addLike(postId, userId);

        PostEntity post = postRepository.findByPostId(postId);
        post.setTotalLikes(post.getTotalLikes() + 1);
        postRepository.save(post);
    }
}

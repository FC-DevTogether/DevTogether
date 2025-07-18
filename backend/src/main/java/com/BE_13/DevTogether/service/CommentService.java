package com.BE_13.DevTogether.service;

import com.BE_13.DevTogether.dto.request.CommentRequest;
import com.BE_13.DevTogether.dto.response.CommentResponse;
import com.BE_13.DevTogether.entity.comment.Comment;
import com.BE_13.DevTogether.entity.comment.CommentRepository;
import com.BE_13.DevTogether.entity.posts.Post;
import com.BE_13.DevTogether.entity.posts.PostRepository;
import com.BE_13.DevTogether.entity.user.User;
import com.BE_13.DevTogether.entity.user.UserRepository;
import com.BE_13.DevTogether.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 댓글(Comment) 서비스(Service) 클래스이다.
 *
 * @author chan
 */
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository; // 게시글 저장소 (게시글 조회를 위해서)
    private final UserRepository userRepository; // 유저 저장소 (작성자 조회를 위해서)

    /**
     * 게시글(Post)에 댓글을 작성한다.
     *
     * @param userId 작성자
     * @param postId 게시글
     * @param request 작성한 댓글
     *
     * @return 작성한 댓글
     */
    public Long commentCreate(Long userId, Long postId, CommentRequest request) {

        // 등록한 유저 조회
        User author = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수가 없습니다."));

        // 댓글을 작성한 게시글 조회
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글을 조회할 수 없습니다."));

        // 부모 댓글 존재 여부 확인
        // 댓글의 아이디가 -1이 아니면 조회하고 없다면 예외 발생
        // 대댓글 조회 기능
        if (request.parent() != -1) {
            Comment comment = commentRepository.findById(request.parent()).orElseThrow(() -> new IllegalArgumentException("댓글을 조회할 수 없습니다."));
        }

        Comment comment = CommentMapper.toEntity(post, author, request);

        // 댓글 저장소에 저장
        commentRepository.save(comment);

        // 작성한 댓글 아이디 반환
        return comment.getId();
    }

    /**
     * 게시글(Post)에 작성된 댓글 리스트를 가져온다.
     *
     * @param postId 조회한 게시글
     *
     * @return 작성한 댓글 리스트
     */
    public List<CommentResponse> findComments(Long postId) {

        // 조회할 댓글의 게시글을 찾는다. 만약 게시글이 존재하지 않느다면 예외를 발생시킨다.
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));

        // 저장소에서 댓글을 가져온다.
        List<Comment> comments = commentRepository.findByCommentList(post);

        // Comment 리스트를 CommentResponse DTO 리스트로 변환하여 반환한다.
        return comments.stream()
                .map(CommentMapper::toCommentResponse).collect(Collectors.toList());
    }
}

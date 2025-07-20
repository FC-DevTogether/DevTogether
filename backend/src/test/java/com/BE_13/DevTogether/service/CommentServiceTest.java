package com.BE_13.DevTogether.service;

import com.BE_13.DevTogether.dto.request.AuthorPostRequest;
import com.BE_13.DevTogether.dto.request.CommentRequest;
import com.BE_13.DevTogether.entity.comment.Comment;
import com.BE_13.DevTogether.entity.comment.CommentRepository;
import com.BE_13.DevTogether.entity.posts.Post;
import com.BE_13.DevTogether.entity.posts.PostRepository;
import com.BE_13.DevTogether.entity.user.Role;
import com.BE_13.DevTogether.entity.user.User;
import com.BE_13.DevTogether.entity.user.UserRepository;
import com.BE_13.DevTogether.mapper.CommentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Comment(댓글) 기능 단위 테스트 코드
 *
 * @author chan
 */
@ExtendWith(MockitoExtension.class) // ExtendWith : Junit5에서 Mockito를 상용하기 위한 확장 기능 , @Mock, @InjectMocks 등의 어노테이션을 사용할 수 있게 해줌
class CommentServiceTest {

    // 유저 저장소
    @Mock
    private UserRepository userRepository;

    // 게시글 저장소
    @Mock
    private PostRepository postRepository;

    // 댓글 저장소
    @Mock
    private CommentRepository commentRepository;

    // 댓글 서비스 클래스
    @InjectMocks
    private CommentService commentService;

    // 자주 사용되는 필드 선언
    private User testUser; // 유저
    private Post testPost; // 게시글
    private Comment testComment; // 댓글

    private AuthorPostRequest authorRequest;

    private CommentRequest commentRequest; // 댓글 요청 Dto
    private CommentRequest replyRequest;

    @BeforeEach
    void setUp() {

        // 테스트 유저 생성
        testUser = User.builder()
                .username("testUser")
                .password("test")
                .role(Role.USER).build();

        assertThat(testUser).isNotNull();

        // 테스트 게시글 생성
        testPost = Post.builder()
                .title("테스트 제목")
                .author(testUser)
                .code("작성한 코드")
                .content("작성한 본문")
                .build();

        assertThat(testPost).isNotNull();

        // 작성자 요청
        authorRequest = new AuthorPostRequest(1L, testUser.getUsername());

        // 일단 댓글 작성 요청 (parent가 -1이면 일반 댓글)
        commentRequest = new CommentRequest(authorRequest, "테스트 댓글 입니다.", -1L);
        replyRequest = new CommentRequest(authorRequest, "테스트 대댓글 입니다.", 1L);

        // 테스트 댓글 생성
        testComment = Comment.testBuilder()
                .id(1L)
                .post(testPost)
                .author(testUser)
                .content("테스트 댓글입니다.")
                .parent(-1L)
                .buildForTest();

        assertThat(testComment).isNotNull();
    }

    // 게시글에 새 댓글 작성 테스트 코드
    @DisplayName("일반 댓글 생성 - 성공")
    @Test
    void commentCreate_Success() {

        // given
        Long userId = 1L;
        Long postId = 1L;

        System.out.println("=== 디버깅 ===");

        // Mock 객체 가져와서 반환값 정의
        // MockedStatic을 사용하여 static 메소드 모킹 TODO: 모킹이란 무엇인가?
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(postRepository.findById(postId)).thenReturn(Optional.of(testPost));
        when(commentRepository.save(any(Comment.class))).thenReturn(testComment); // TODO: any(Comment.class)는 무엇인가?


        // try-with-resources 문법으로 자동 Mock 해제
        // TODO: mockMapper는 어떤 역활을 하는가?
        try (MockedStatic<CommentMapper> mockMapper = mockStatic(CommentMapper.class)) { // TODO: MockedStatic은 무엇인가?

            mockMapper.when(() -> CommentMapper.toEntity(any(Post.class), any(User.class), any(CommentRequest.class)))
                    .thenReturn(testComment);

            // 메서드 호출 직전에 확인하기
            System.out.println("Before :" + testComment.getId());

            // when(실행) - 실제 테스트할 메서드 실행
            // 댓글 생성
            Long commentId = commentService.commentCreate(userId, postId, commentRequest);

            System.out.println("After :" + testComment.getId());

            if (commentId == null) {

                verify(commentRepository).save(any());

                Comment saved = commentRepository.save(new Comment(testPost, testUser, "댓글입니다", -1L));
                System.out.println("Mock returns: " + saved);
            }

            // then(검증) - 결과가 예상과 일치하는지 확인
            // TODO: NPE 발생
            assertThat(commentId).isEqualTo(1L);

            // TODO: 해당 코드에서 times는 무엇인가?
            // verify - Mock 객체의 객체의 메서드가 예상대로 호출되었는지 검증
            verify(userRepository, times(1)).findById(userId);
            verify(postRepository, times(1)).findById(postId);
            verify(commentRepository, times(1)).save(any(Comment.class));
        }
    }


    // 작성한 댓글에 대댓글 작성 테스트 코드
    @DisplayName("대댓글 생성 - 성공")
    @Test
    void replyCreate_Success() {

        // given
        Long userId = 1L;
        Long postId = 1L;

        // 부모 댓글 생성 (일반 댓글)
        Comment comment = Comment.testBuilder()
                .id(1L)
                .content("테스트 부모댓글 입니다.")
                .post(testPost)
                .author(testUser)
                .parent(-1L)
                .buildForTest();

        // 대댓글 생성 (부모 댓글의 ID를 parent로 지정)
        Comment replyComment = Comment.testBuilder()
                .id(2L)
                .content("테스트 대댓글 입니다.")
                .author(testUser)
                .post(testPost)
                .parent(1L)
                .buildForTest();

        // Mock 설정
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(postRepository.findById(postId)).thenReturn(Optional.of(testPost));
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(replyComment);

        // try-catch-resource
        try (MockedStatic<CommentMapper> mockedMapper = mockStatic(CommentMapper.class)) {
            mockedMapper.when(() -> CommentMapper.toEntity(any(Post.class), any(User.class), any(CommentRequest.class))).thenReturn(replyComment);

            // when
            Long replyId = commentService.commentCreate(userId, postId, replyRequest);

            // then
            assertThat(replyId).isEqualTo(2L);

            // 부모댓글 조회가 실행되었는 확인한다.
            verify(commentRepository, times(1)).findById(1L);
            verify(commentRepository, times(1)).save((any(Comment.class)));
        }
    }

    @DisplayName("댓글 목록 조회 - 성공")
    @Test
    void findComments_Success() {


    }
}
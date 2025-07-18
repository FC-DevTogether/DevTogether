package com.BE_13.DevTogether.entity.posts;

import com.BE_13.DevTogether.entity.board.Board;
import com.BE_13.DevTogether.entity.comment.Comment;
import com.BE_13.DevTogether.entity.user.Role;
import com.BE_13.DevTogether.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 게시글(Posts) 엔티티(Entity) 클래스.
 *
 * @author chan
 */
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "Posts")
public class Post {

    // TODO: private PostsId id 으로 바뀔 수 있다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 게시글 아이디

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    private String title; // 게시글 제목

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author; // 작성자

    @Column(length = 3000)
    private String code; // 작성 코드

    private String content; // 게시글 본문

    @OneToMany(mappedBy = "post")
    @Builder.Default // 기본값으로 초기화하여 NPE 방지
    private List<Comment> comments = new ArrayList<>(); // 작성글

    //  private int commentCounter = 0; // 댓글 갯수 확인 필드

    private Role reqRole; // requireRole 요구 권한

    private String header; // 말머리


    @CreatedDate
    private LocalDateTime createDate; // 생성일

    @Builder
    public Post(Long id, Board board, String title, User author, String code, String content, List<Comment> comments, Role reqRole, String header, LocalDateTime createDate) {
        this.id = id;
        this.board = board;
        this.title = title;
        this.author = author;
        this.code = code;
        this.content = content;
        this.comments = comments;
        this.reqRole = reqRole;
        this.header = header;
        this.createDate = createDate;
    }

    // updateCommentCount 메서드
    // public void updateCommentCount() {} // 현재 댓글 갯수를 반환한다.
}

package com.BE_13.DevTogether.entity.comment;

import com.BE_13.DevTogether.entity.posts.Post;
import com.BE_13.DevTogether.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 댓글(Comment) 엔티티(Entity) 클래스.
 *
 * @author chan
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Comment {

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post; // 본문 아이디

    // TODO: private commentId id 형식으로 바뀔 수 있다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 댓글 아이디

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author; // 댓글 작성자

    @CreatedDate
    private LocalDateTime createDate; // 댓글 작성일

    private String content; // 댓글 내용

    private long parent; // 대댓글 확인 번호

    @Builder
    public Comment(Post post, User author, String content, long parent) {
        this.post = post;
        this.author = author;
        this.content = content;
        this.parent = parent;
    }
}

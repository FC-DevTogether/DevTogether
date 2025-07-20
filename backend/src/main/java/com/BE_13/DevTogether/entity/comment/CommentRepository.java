package com.BE_13.DevTogether.entity.comment;

import com.BE_13.DevTogether.entity.posts.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 저장된 모든 댓글 조회
    // List 배열에 Comment 추가
    List<Comment> findByPost(Post post);
}

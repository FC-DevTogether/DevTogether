package com.BE_13.DevTogether.mapper;

import com.BE_13.DevTogether.dto.request.CommentRequest;
import com.BE_13.DevTogether.dto.response.CommentResponse;
import com.BE_13.DevTogether.entity.comment.Comment;
import com.BE_13.DevTogether.entity.posts.Post;
import com.BE_13.DevTogether.entity.user.User;

import static com.BE_13.DevTogether.mapper.PostMapper.toAuthorResponse;

/**
 * 댓글(Comment) 엔티티(Entity) 객체를 DTO로 변환하는 클래스이다.
 *
 * @author chan
 */
public class CommentMapper {

    // 댓글 작성 요청 시 댓글을 엔티티(Entity)로 변환한다.
    public static Comment toEntity(Post post, User author, CommentRequest request) {

        return Comment.builder()
                .post(post)
                .author(author)
                .content(request.content())
                .parent(request.parent())
                .build();
    }

    // 댓글 조회 응답 시 Entity를 DTO로 변환한다.
    public static CommentResponse toCommentResponse(Comment comment) {

        return new CommentResponse(
                comment.getId(),
                comment.getPost().getId(),
                toAuthorResponse(comment.getAuthor()),
                comment.getContent(),
                comment.getParent(),
                comment.getCreateDate());
    }
}

package com.BE_13.DevTogether.mapper;

import com.BE_13.DevTogether.dto.PostDetailResponse;
import com.BE_13.DevTogether.dto.request.PostCreateRequest;
import com.BE_13.DevTogether.dto.response.AuthorResponse;
import com.BE_13.DevTogether.dto.response.CommentResponse;
import com.BE_13.DevTogether.dto.response.PostSummaryResponse;
import com.BE_13.DevTogether.dto.response.RoleResponse;
import com.BE_13.DevTogether.entity.comment.Comment;
import com.BE_13.DevTogether.entity.posts.Post;
import com.BE_13.DevTogether.entity.user.User;

import java.util.stream.Collectors;

/**
 * 게시글(Post) 엔티티(Entity)를 DTO로 변환하는 클래스이다.
 *
 * @author chan
 */
public class PostMapper {

    // 게시글 생성 요청을 Entity로 변환한다.
    public static Post toEntity(PostCreateRequest request, User user) {
        return Post.builder().
                title(request.title()).
                code(request.code()).
                content(request.content()).
                author(user).
                build();
    }

    // 게시글 목록 DTO 정보를 반환한다.
    public static PostSummaryResponse toSummaryResponse(Post post) {
        return new PostSummaryResponse(
                post.getId(),
                post.getTitle(),
                post.getCode(),
                post.getContent(),
                toAuthorResponse(post.getAuthor()),
                post.getCreateDate()
        );
    }

    // 게시글 상세 DTO 정보를 반환한다.
    public static PostDetailResponse toDetailResponse(Post post) {
        return new PostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getCode(),
                post.getContent(),
                toAuthorResponse(post.getAuthor()),
                post.getComments().stream()
                        .map(PostMapper::toCommentResponse)
                        .collect(Collectors.toList()),
                post.getCreateDate()
        );
    }

    // 작정자 DTO 정보를 반환한다.
    public static AuthorResponse toAuthorResponse(User user) {
        return new AuthorResponse(
                user.getId(),
                user.getUsername(),
                new RoleResponse(
                        user.getRole().getKey(),
                        user.getRole().getDescription()
                )
        );
    }

    // 댓글 DTO 정보를 반환한다.
    public static CommentResponse toCommentResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getPost().getId(),
                toAuthorResponse(comment.getAuthor()),
                comment.getContent(),
                comment.getParent(),
                comment.getCreateDate()
        );
    }


}

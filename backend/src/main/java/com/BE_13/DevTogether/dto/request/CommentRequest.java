package com.BE_13.DevTogether.dto.request;

/**
 * 댓글(comment) 요청(request) DTO 레코드(record) 클래스이다.
 *
 * @param author 댓글 작성자
 * @param content 작성한 댓글
 * @param parent 대댓글
 *
 * @author chan
 */
public record CommentRequest(AuthorPostRequest author,
                             String content,
                             Long parent) {
}

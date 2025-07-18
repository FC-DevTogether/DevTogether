package com.BE_13.DevTogether.controller.rest;

import com.BE_13.DevTogether.dto.request.CommentRequest;
import com.BE_13.DevTogether.dto.response.CommentResponse;
import com.BE_13.DevTogether.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 댓글(Comment) API 요청 컨트롤러 클래스이다.
 *
 * @author chan
 */
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
@RestController
public class ContentRestController {

    private final CommentService commentService;

    /**
     * 댓글(Comment) 작성
     *
     * @param postId  댓글을 작성한 게시글
     * @param request 댓글 내용
     * @return 작성한 댓글
     */
    @PostMapping
    public ResponseEntity<Long> createComment(@PathVariable Long postId, @RequestBody CommentRequest request) {

        Long userId = request.author().id(); // 유저 아이디

        Long commentId = commentService.commentCreate(userId, postId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentId);

    }

    /**
     * 작성된 댓글(Comment) 조회
     *
     * @param postId 댓글을 작성힌 게시글
     * @return 작성된 댓글
     */
    @GetMapping
    public ResponseEntity<List<CommentResponse>> findAllComment(@PathVariable Long postId) {
        List<CommentResponse> response = commentService.findComments(postId);

        return ResponseEntity.ok(response);
    }
}

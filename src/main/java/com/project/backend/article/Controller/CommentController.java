package com.project.backend.article.Controller;

import com.project.backend.article.Service.CommentService;
import com.project.backend.article.protocode.Comment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<?> createComment(@RequestBody Comment.CreateCommentRequest request) {
        try {
            commentService.createComment(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("댓글 작성 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("댓글 작성 실패. 에러 메시지: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteComment(@RequestBody Comment.DeleteCommentRequest request) {
        try {
            commentService.deleteComment(request);
            return ResponseEntity.ok("댓글 삭제 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("댓글 삭제 실패. 에러 메시지: " + e.getMessage());
        }
    }

    @GetMapping("/getByArticleID")
    public ResponseEntity<Comment.GetCommentsByArticleIDResponse> getCommentsByArticleID(@RequestParam int articleID, @RequestParam int userID) {
        try {
            Comment.GetCommentsByArticleIDRequest request = Comment.GetCommentsByArticleIDRequest.newBuilder()
                    .setArticleID(articleID)
                    .setUserID(userID)
                    .build();

            Comment.GetCommentsByArticleIDResponse response = commentService.getCommentsByArticleID(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getRepliesByCommentID")
    public ResponseEntity<Comment.GetRepliesByCommentIDResponse> getRepliesByCommentID(@RequestParam int commentID, @RequestParam int articleID, @RequestParam int userID) {
        try {
            Comment.GetRepliesByCommentIDRequest request = Comment.GetRepliesByCommentIDRequest.newBuilder()
                    .setCommentID(commentID)
                    .setArticleID(articleID)
                    .setUserID(userID)
                    .build();

            Comment.GetRepliesByCommentIDResponse response = commentService.getRepliesByCommentID(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
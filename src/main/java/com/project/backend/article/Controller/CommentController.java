package com.project.backend.article.Controller;

import com.project.backend.JwtTokenProvider;
import com.project.backend.article.Service.CommentService;
import com.project.backend.article.protocode.Comment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private CommentService commentService;
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/create")
    public ResponseEntity<?> createComment(@RequestHeader ("Authorization") String authorizationHeader,@RequestBody Comment.CreateCommentRequest request) {
        String token = authorizationHeader.substring(7);

        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }
        try {
            commentService.createComment(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("댓글 작성 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("댓글 작성 실패 : " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteComment(@RequestHeader ("Authorization") String authorizationHeader,@RequestBody Comment.DeleteCommentRequest request) {
        String token = authorizationHeader.substring(7);

        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }

        try {
            commentService.deleteComment(request);
            return ResponseEntity.ok("댓글 삭제 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("댓글 삭제 실패 : " + e.getMessage());
        }
    }

    @GetMapping("/getByArticleID")
    public ResponseEntity<?> getCommentsByArticleID(@RequestParam int articleID, @RequestParam int userID) {
        try {
            Comment.GetCommentsByArticleIDRequest request = Comment.GetCommentsByArticleIDRequest.newBuilder()
                    .setArticleID(articleID)
                    .setUserID(userID)
                    .build();

            Comment.GetCommentsByArticleIDResponse response = commentService.getCommentsByArticleID(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("에러 메세지 : " + e.getMessage());
        }
    }

    @GetMapping("/getRepliesByCommentID")
    public ResponseEntity<?> getRepliesByCommentID(@RequestParam int commentID, @RequestParam int articleID, @RequestParam int userID) {
        try {
            Comment.GetRepliesByCommentIDRequest request = Comment.GetRepliesByCommentIDRequest.newBuilder()
                    .setCommentID(commentID)
                    .setArticleID(articleID)
                    .setUserID(userID)
                    .build();

            Comment.GetRepliesByCommentIDResponse response = commentService.getRepliesByCommentID(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("에러 메세지 : " + e.getMessage());
        }
    }
}
package com.project.backend.article.Controller;

import com.project.backend.JwtTokenProvider;
import com.project.backend.article.Service.ArticleService;
import com.project.backend.article.protocode.Article;
import com.project.backend.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog")
public class ArticleController {

    private ArticleService articleService;
    private UserService userService;
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/")
    public ResponseEntity<String> createArticle(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Article.CreateArticleRequest request) {
        String token = authorizationHeader.substring(7);

        // 토큰 유효성 검사
        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }

        try {
            Article.CreateArticleResponse articleID = articleService.createArticle(request);

            // 생성된 게시물의 ID를 반환합니다.
            return ResponseEntity.status(HttpStatus.CREATED).body("글 작성 성공. Article ID: " + articleID);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("글 작성 실패. 에러 메시지: " + e.getMessage());
        }
    }


    @GetMapping("/list")
    public ResponseEntity<Article.ListArticleResponse> listArticle(@RequestParam int offset, @RequestParam int amount,
                                                                   @RequestParam Article.ListArticleOrder order,
                                                                   @RequestParam int userID, @RequestParam Article.ArticleType type,
                                                                   @RequestParam int authorID, @RequestParam Article.Duration duration,
                                                                   @RequestParam String query) {
        Article.ListArticleRequest request = Article.ListArticleRequest.newBuilder()
                .setOffset(offset)
                .setAmount(amount)
                .setOrder(order)
                .setUserID(userID)
                .setType(type)
                .setAuthorID(authorID)
                .setDuration(duration)
                .setQuery(query)
                .build();
        Article.ListArticleResponse response = articleService.listArticle(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{articleID}")
    public ResponseEntity<Article.GetArticleResponse> getArticle(@PathVariable int articleID, @RequestParam int userID) {
        Article.GetArticleRequest request = Article.GetArticleRequest.newBuilder()
                .setArticleID(articleID)
                .setUserID(userID)
                .build();
        Article.GetArticleResponse response = articleService.getArticle(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/updateTitle")
    public ResponseEntity<String> updateArticleTitle(@RequestBody Article.UpdateArticleTitleRequest request) {
        Article.UpdateArticleTitleRequest response = articleService.updateArticleTitle(request);
        return ResponseEntity.ok("done");
    }

    @PutMapping("/updateBody")
    public ResponseEntity<String> updateArticleBody(@RequestBody Article.UpdateArticleBodyRequest request) {
        Article.UpdateArticleBodyRequest response = articleService.updateArticleBody(request);
        return ResponseEntity.ok("done");
    }

    @DeleteMapping("/{articleID}")
    public ResponseEntity<String> deleteArticle(@PathVariable int articleID, @RequestParam int userID) {
        Article.DeleteArticleRequest request = Article.DeleteArticleRequest.newBuilder()
                .setArticleID(articleID)
                .setUserID(userID)
                .build();
        Article.DeleteArticleRequest response = articleService.deleteArticle(request);
        return ResponseEntity.ok("done");
    }

    @GetMapping("/listByAuthor")
    public ResponseEntity<Article.ListArticleResponse> listArticleByAuthor(@RequestParam int offset, @RequestParam int amount,
                                                                           @RequestParam Article.ListArticleOrder order,
                                                                           @RequestParam int authorID, @RequestParam int userID,
                                                                           @RequestParam Article.ArticleType type,
                                                                           @RequestParam boolean isPrivate,
                                                                           @RequestParam Article.Duration duration,
                                                                           @RequestParam String query) {
        Article.ListArticleByAuthorRequest request = Article.ListArticleByAuthorRequest.newBuilder()
                .setOffset(offset)
                .setAmount(amount)
                .setOrder(order)
                .setAuthorID(authorID)
                .setUserID(userID)
                .setType(type)
                .setIsPrivate(isPrivate)
                .setDuration(duration)
                .setQuery(query)
                .build();
        Article.ListArticleResponse response = articleService.listArticleByAuthor(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/toggleLike")
    public ResponseEntity<String> toggleArticleLike(@RequestBody Article.ToggleArticleLikeRequest request) {
        Article.ToggleArticleLikeRequest response = articleService.toggleArticleLike(request);
        return ResponseEntity.ok("done");
    }

    @PutMapping("/setVisibility")
    public ResponseEntity<String> setArticleVisibility(@RequestBody Article.SetArticleVisibilityRequest request) {
        Article.SetArticleVisibilityRequest response = articleService.setArticleVisibility(request);
        return ResponseEntity.ok("done");
    }
}

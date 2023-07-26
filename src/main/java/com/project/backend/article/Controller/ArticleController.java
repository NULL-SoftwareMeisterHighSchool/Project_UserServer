package com.project.backend.article.Controller;

import com.project.backend.JwtTokenProvider;
import com.project.backend.article.Service.ArticleService;
import com.project.backend.article.entity.ArticleCreateResponseEntity;
import com.project.backend.article.entity.ArticleResponseEntity;
import com.project.backend.article.protocode.Article;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/blog")
public class ArticleController {

    private ArticleService articleService;
    private JwtTokenProvider jwtTokenProvider;

    //게시물 작성
    @PostMapping("/")
    public ResponseEntity<?> createArticle(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Article.CreateArticleRequest request) {
        String token = authorizationHeader.substring(7);

        // 토큰 유효성 검사
        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }

        try {
            Article.CreateArticleResponse articleID = articleService.createArticle(request);

            ArticleCreateResponseEntity responseEntity = new ArticleCreateResponseEntity("글 작성 성공", articleID);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseEntity);
        } catch (Exception e) {
            ArticleResponseEntity articleResponseEntity = new ArticleResponseEntity("글 작성 실패 : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(articleResponseEntity);
        }
    }

    //게시물 목록
    @GetMapping("/list/{page}")
    public ResponseEntity<?> listArticle(@RequestParam Article.ListArticleOrder order,
                                         @RequestParam int userID, @RequestParam Article.ArticleType type,
                                         @RequestParam int authorID, @RequestParam Article.Duration duration,
                                         @RequestParam String query, @PathVariable int page) {
        try {
            Article.ListArticleRequest request = Article.ListArticleRequest.newBuilder()
                    .setOffset(page*10)
                    .setAmount(10)
                    .setOrder(order)
                    .setUserID(userID)
                    .setType(type)
                    .setAuthorID(authorID)
                    .setDuration(duration)
                    .setQuery(query)
                    .build();
            Article.ListArticleResponse blogList = articleService.listArticle(request);

            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("blogList", blogList);
            jsonResponse.put("total", blogList.getTotalCount());
            jsonResponse.put("page", page);
            return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시물 목록 조회 실패 : " + e.getMessage());
        }
    }

    //게시물 상세보기
    @GetMapping("/{articleID}")
    public ResponseEntity<?> getArticle(@PathVariable int articleID, @RequestParam int userID) {

        try {
            Article.GetArticleRequest request = Article.GetArticleRequest.newBuilder()
                    .setArticleID(articleID)
                    .setUserID(userID)
                    .build();
            Article.GetArticleResponse response = articleService.getArticle(request);
            
            if ( response == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("요청 대상을 찾을 수 없음");
            } else {
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시물 조회 실패 : " + e.getMessage());
        }
    }

    @PatchMapping("/edit/title")
    public ResponseEntity<?> updateArticleTitle(@RequestHeader ("Authorization") String authorizationHeader, @RequestBody Article.UpdateArticleTitleRequest request) {

        String token = authorizationHeader.substring(7);

        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }

        try {
            Article.UpdateArticleTitleRequest response = articleService.updateArticleTitle(request);

            if ( response == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 게시물");
            }else {
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("제목 업데이트 실패 : " + e.getMessage());
        }
    }

    @PatchMapping("/edit/body")
    public ResponseEntity<String> updateArticleBody(@RequestHeader ("Authorization") String authorizationHeader, @RequestBody Article.UpdateArticleBodyRequest request) {

        String token = authorizationHeader.substring(7);

        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }

        try {
            Article.UpdateArticleBodyRequest response = articleService.updateArticleBody(request);
            return ResponseEntity.ok("done");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("내용 업데이트 실패 : " + e.getMessage());
        }
    }

    @DeleteMapping("/{articleID}")
    public ResponseEntity<?> deleteArticle(@RequestHeader ("Authorization") String authorizationHeader, @PathVariable int articleID, @RequestParam int userID) {

        String token = authorizationHeader.substring(7);

        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }
        try {
            //게시물 작성자 확인
            Article.GetArticleRequest getArticleRequest = Article.GetArticleRequest.newBuilder()
                    .setArticleID(articleID)
                    .build();
            Article.GetArticleResponse response = articleService.getArticle(getArticleRequest);

            if ( response.getAuthorID() != userID) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한없음");
            } else if (response == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제할 게시물 조회 실패");
            }

            //게시물 삭제
            Article.DeleteArticleRequest deleteArticleRequest = Article.DeleteArticleRequest.newBuilder()
                    .setArticleID(articleID)
                    .setUserID(userID)
                    .build();

            articleService.deleteArticle(deleteArticleRequest);
            return ResponseEntity.status(HttpStatus.OK).body("게시물 삭제 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시물 삭제 실패 : " + e.getMessage());
        }
    }

    @GetMapping("/listbyauthor/{page}")
    public ResponseEntity<?> listArticleByAuthor(@RequestParam Article.ListArticleOrder order,
                                                 @RequestParam int authorID, @RequestParam int userID,
                                                 @RequestParam Article.ArticleType type,
                                                 @RequestParam boolean isPrivate,
                                                 @RequestParam Article.Duration duration,
                                                 @RequestParam String query, @PathVariable int page) {
        try {
            Article.ListArticleByAuthorRequest request = Article.ListArticleByAuthorRequest.newBuilder()
                    .setOffset(page*10)
                    .setAmount(10)
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
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시물 조회 실패 : " + e.getMessage());
        }
    }

    @PutMapping("/togglelike")
    public ResponseEntity<?> toggleArticleLike(@RequestHeader ("Authorization") String authorizationHeader, @RequestBody Article.ToggleArticleLikeRequest request) {

        String token = authorizationHeader.substring(7);

        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }

        articleService.toggleArticleLike(request);
        return ResponseEntity.status(HttpStatus.OK).body("좋아요 완료");
    }

    @PutMapping("/setvisibility")
    public ResponseEntity<?> setArticleVisibility(@RequestHeader ("Authorization") String authorizationHeader, @RequestBody Article.SetArticleVisibilityRequest request) {

        String token = authorizationHeader.substring(7);

        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }

        Article.SetArticleVisibilityRequest response = articleService.setArticleVisibility(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

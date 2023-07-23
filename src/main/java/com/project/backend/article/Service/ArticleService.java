package com.project.backend.article.Service;


import com.project.backend.article.protocode.Article;

public interface ArticleService {
    /**
     * 게시물 작성
     *
     * CreateArticleRequest (int32 authorID, ArticleType type, string title)
     * CreateArticleResponse (int32 articleID)
     */
    Article.CreateArticleResponse createArticle(Article.CreateArticleRequest request);

    /**
     * 게시물 목록 조회
     *
     * ListArticleRequest (int32 offset, int32 amount, ListArticleOrder order, int32 userID,
     *                    ArticleType type, int32 authorID, Duration duration, string query)
     * ListArticleResponse (repeated ListArticleElement articles, int32 totalCount)
     */
    Article.ListArticleResponse listArticle(Article.ListArticleRequest request);

    /**
     * 게시물 상세보기
     * GetArticleRequest (int32 articleID, int32 userID)
     * GetArticleResponse (int32 articleID, int32 authorID, ArticleType type, string title,
     *                     google.protobuf.Timestamp createdAt, google.protobuf.Timestamp updatedAt,
     *                     string body, int64 views, int32 likes, int32 comments,
     *                     bool isPrivate, bool isLiked, bool isAuthor)
     */
    Article.GetArticleResponse getArticle(Article.GetArticleRequest request);

    /**
     * 게시물 제목 수정
     * UpdateArticleTitleRequest (int32 articleID, int32 userID, string title)
     * google.protobuf.Empty
     */
    Article.UpdateArticleTitleRequest updateArticleTitle(Article.UpdateArticleTitleRequest request);

    /**
     * 게시물 내용 수정
     * UpdateArticleBodyRequest (int32 articleID, int32 userID, string body)
     * google.protobuf.Empty
     */
    Article.UpdateArticleBodyRequest updateArticleBody(Article.UpdateArticleBodyRequest request);

    /**
     * 게시물 삭제
     * DeleteArticleRequest (int32 articleID, int32 userID)
     * google.protobuf.Empty
     */
    Article.DeleteArticleRequest deleteArticle(Article.DeleteArticleRequest request);

    /**
     * 특정 작성자의 게시물 목록 조회
     * ListArticleByAuthorRequest (int32 offset, int32 amount, ListArticleOrder order, int32 authorID, int32 userID,
     *                             ArticleType type, bool isPrivate, Duration duration, string query)
     * ListArticleResponse (repeated ListArticleElement articles, int32 totalCount)
     */
    Article.ListArticleResponse listArticleByAuthor(Article.ListArticleByAuthorRequest request);

    /**
     * 게시물 좋아요 토글
     * ToggleArticleLikeRequest (int32 articleID, int32 userID)
     * google.protobuf.Empty
     */
    Article.ToggleArticleLikeRequest toggleArticleLike(Article.ToggleArticleLikeRequest request);

    /**
     * 게시물 가시성 설정
     * SetArticleVisibilityRequest (int32 articleID, int32 userID, bool isPrivate)
     * google.protobuf.Empty
     */
    Article.SetArticleVisibilityRequest setArticleVisibility(Article.SetArticleVisibilityRequest request);
}

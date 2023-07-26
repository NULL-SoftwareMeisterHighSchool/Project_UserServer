package com.project.backend.article.entity;

import com.project.backend.article.protocode.Article;

public class ArticleCreateResponseEntity {
    private String message;
    private Article.CreateArticleResponse articleId;

    public ArticleCreateResponseEntity(String message, Article.CreateArticleResponse articleId) {
        this.message = message;
        this.articleId = articleId;
    }

    public String getMessage() {
        return message;
    }

    public Article.CreateArticleResponse getArticleId() {
        return articleId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setArticleId(Article.CreateArticleResponse articleId) {
        this.articleId = articleId;
    }
}


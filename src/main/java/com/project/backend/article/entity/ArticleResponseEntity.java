package com.project.backend.article.entity;

public class ArticleResponseEntity {
    private String message;

    public ArticleResponseEntity(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

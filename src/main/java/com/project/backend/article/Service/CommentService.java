package com.project.backend.article.Service;

import com.project.backend.article.protocode.Comment;

public interface CommentService {

    /**
     *
     */
    void createComment(Comment.CreateCommentRequest request);

    /**
     *
     */
    void deleteComment(Comment.DeleteCommentRequest request);

    /**
     *
     */
    Comment.GetCommentsByArticleIDResponse getCommentsByArticleID(Comment.GetCommentsByArticleIDRequest request);

    /**
     *
     */
    Comment.GetRepliesByCommentIDResponse getRepliesByCommentID(Comment.GetRepliesByCommentIDRequest request);

}

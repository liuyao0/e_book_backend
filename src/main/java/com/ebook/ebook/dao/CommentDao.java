package com.ebook.ebook.dao;

import com.ebook.ebook.entity.Comment;

import java.sql.Timestamp;
import java.util.List;

public interface CommentDao {
    List<Comment> getCommentByBookId(Integer bookId);
    void addComment(Comment comment);
}

package com.ebook.ebook.service;


import com.ebook.ebook.dto.CommentDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CommentService {
    List<CommentDTO> getCommentsByBookId(Integer bookId);
    String addComment(String content,Integer bookId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}

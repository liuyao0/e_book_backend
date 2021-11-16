package com.ebook.ebook.serviceimpl;

import com.ebook.ebook.dao.CommentDao;
import com.ebook.ebook.dao.UserDao;
import com.ebook.ebook.dto.CommentDTO;
import com.ebook.ebook.entity.Comment;
import com.ebook.ebook.entity.User;
import com.ebook.ebook.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentDao commentDao;

    @Autowired
    UserDao userDao;

    @Override
    public List<CommentDTO> getCommentsByBookId(Integer bookId) {
        List<CommentDTO> commentDTOList=new ArrayList<>();
        List<Comment> commentList=commentDao.getCommentByBookId(bookId);
        for(Comment comment:commentList)
        {
            User user=userDao.getOne(comment.getUserId());
            CommentDTO commentDTO=new CommentDTO(user.getName(),comment.getTime(),comment.getContent());
            commentDTOList.add(commentDTO);
        }
        commentDTOList.sort((o1, o2) -> {
            if (o1.getTime().after(o2.getTime()))
                return -1;
            else
                return 0;
        });
        return  commentDTOList;
    }

    @Override
    public String addComment(String content,Integer bookId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Integer userId=(Integer)(httpServletRequest.getSession().getAttribute("userId"));
        if(userId==null)
            return "请登录后再发表评论！";
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        Comment comment=new Comment();
        comment.setContent(content);
        comment.setTime(timestamp);
        comment.setUserId(userId);
        comment.setBookId(bookId);
        commentDao.addComment(comment);
        return "";
    }
}

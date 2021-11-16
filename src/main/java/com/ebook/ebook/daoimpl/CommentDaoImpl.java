package com.ebook.ebook.daoimpl;

import com.ebook.ebook.dao.CommentDao;
import com.ebook.ebook.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDaoImpl implements CommentDao {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Comment> getCommentByBookId(Integer bookId) {
        Query query=new Query(Criteria.where("book_id").is(bookId));
        return mongoTemplate.find(query,Comment.class);
    }

    @Override
    public void addComment(Comment comment) {
        mongoTemplate.save(comment);
    }
}

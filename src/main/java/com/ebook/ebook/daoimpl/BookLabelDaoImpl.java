package com.ebook.ebook.daoimpl;

import com.ebook.ebook.dao.BookLabelDao;
import com.ebook.ebook.entity.BookLabel;
import com.ebook.ebook.repository.BookLabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookLabelDaoImpl implements BookLabelDao {
    @Autowired
    private BookLabelRepository bookLabelRepository;


    @Override
    public List<Integer> getBookIdsByLabelName(String labelName) {
        List<BookLabel> bookLabels=bookLabelRepository.findByLabelName(labelName);
        List<Integer> res=new ArrayList<>();
        for(BookLabel bookLabel:bookLabels)
            res.add(bookLabel.getBookId());
        return res;
    }
}

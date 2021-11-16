package com.ebook.ebook.daoimpl;

import com.ebook.ebook.dao.LabelDao;
import com.ebook.ebook.entity.Label;
import com.ebook.ebook.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LabelDaoImpl implements LabelDao {
    @Autowired
    private LabelRepository labelRepository;

    @Override
    public Label findByName(String labelName) {
        return labelRepository.findByName(labelName);
    }
}

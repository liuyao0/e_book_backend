package com.ebook.ebook.dao;

import com.ebook.ebook.entity.Label;

public interface LabelDao {
    Label findByName(String labelName);
}

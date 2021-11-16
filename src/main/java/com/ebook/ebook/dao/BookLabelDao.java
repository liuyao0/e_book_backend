package com.ebook.ebook.dao;

import java.util.List;

public interface BookLabelDao {
    List<Integer> getBookIdsByLabelName(String labelName);
}

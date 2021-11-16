package com.ebook.ebook.repository;

import com.ebook.ebook.entity.BookLabel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookLabelRepository extends JpaRepository<BookLabel,Integer> {
    List<BookLabel> findByLabelName(String labelName);
}

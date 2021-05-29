package com.ebook.ebook.repository;
import com.ebook.ebook.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Integer> {}



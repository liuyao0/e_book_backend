package com.ebook.ebook.repository;

import com.ebook.ebook.entity.Meta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetaRepository extends JpaRepository<Meta,Integer> {
    Meta findByMetaKey(String metaKey);
}

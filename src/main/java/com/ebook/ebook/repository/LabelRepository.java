package com.ebook.ebook.repository;

import com.ebook.ebook.entity.Label;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface LabelRepository extends Neo4jRepository<Label,Long> {
    Label findByName(String labelName);
}

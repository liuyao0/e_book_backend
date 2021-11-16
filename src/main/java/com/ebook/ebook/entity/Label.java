package com.ebook.ebook.entity;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Node
public class Label {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Relationship(type = "similar")
    private Set<Label> similarLabels;

    public Label() {}
    public Label(String name){this.name=name;}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Label> getSimilarLabels() {
        return similarLabels;
    }

    public void setSimilarLabels(Set<Label> similarLabels) {
        this.similarLabels = similarLabels;
    }
}

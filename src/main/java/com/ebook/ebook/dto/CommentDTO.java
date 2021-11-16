package com.ebook.ebook.dto;

import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class CommentDTO implements Serializable {
    private String username;
    private Date time;
    private String content;

    public CommentDTO() {
    }

    public CommentDTO(String username, Date time, String content) {
        this.username = username;
        this.time = time;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentDTO that = (CommentDTO) o;
        return Objects.equals(username, that.username) && Objects.equals(time, that.time) && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, time, content);
    }
}

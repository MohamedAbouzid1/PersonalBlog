package com.mohamed.blog.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Comment {

    private String id;
    private String articleId;
    private String authorName;
    private String content;
    private LocalDateTime createdAt;

    // Default constructor
    public Comment() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }

    // Constructor with parameters
    public Comment(String articleId, String authorName, String content) {
        this.id = UUID.randomUUID().toString();
        this.articleId = articleId;
        this.authorName = authorName;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", articleId='" + articleId + '\'' +
                ", authorName='" + authorName + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
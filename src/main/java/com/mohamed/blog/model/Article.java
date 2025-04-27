package com.mohamed.blog.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Article {
    private String id;
    private String title;
    private String content;
    private LocalDateTime publishedAt;
    private LocalDateTime updatedAt;

    // Default constructor
    public Article() {
        this.id = UUID.randomUUID().toString();
        this.publishedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Constructor with parameters
    public Article(String title, String content) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.content = content;
        this.publishedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publishedAt=" + publishedAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
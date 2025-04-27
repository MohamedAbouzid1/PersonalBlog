package com.mohamed.blog.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mohamed.blog.model.Article;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepository {

    private final String STORAGE_DIRECTORY = "blog_data";
    private final ObjectMapper objectMapper;

    public ArticleRepository() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Create storage directory if it doesn't exist
        File directory = new File(STORAGE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public List<Article> findAll() {
        List<Article> articles = new ArrayList<>();
        File directory = new File(STORAGE_DIRECTORY);

        File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));
        if (files != null) {
            for (File file : files) {
                try {
                    Article article = objectMapper.readValue(file, Article.class);
                    articles.add(article);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return articles;
    }

    public Optional<Article> findById(String id) {
        Path filePath = Paths.get(STORAGE_DIRECTORY, id + ".json");
        if (Files.exists(filePath)) {
            try {
                Article article = objectMapper.readValue(filePath.toFile(), Article.class);
                return Optional.of(article);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    public Article save(Article article) {
        if (article.getId() == null) {
            article.setId(java.util.UUID.randomUUID().toString());
        }

        if (article.getPublishedAt() == null) {
            article.setPublishedAt(LocalDateTime.now());
        }

        article.setUpdatedAt(LocalDateTime.now());

        try {
            objectMapper.writeValue(
                    Paths.get(STORAGE_DIRECTORY, article.getId() + ".json").toFile(),
                    article);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return article;
    }

    public void deleteById(String id) {
        Path filePath = Paths.get(STORAGE_DIRECTORY, id + ".json");
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
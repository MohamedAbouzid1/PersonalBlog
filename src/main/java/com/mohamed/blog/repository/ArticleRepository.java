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

        // Determine where files are being saved
        File directory = new File(STORAGE_DIRECTORY);
        System.out.println("==============================");
        System.out.println("ARTICLE REPOSITORY INITIALIZATION");
        System.out.println("Article storage directory: " + directory.getAbsolutePath());
        System.out.println("Current working directory: " + System.getProperty("user.dir"));
        System.out.println("Directory exists: " + directory.exists());
        System.out.println("==============================");

        // Create storage directory if it doesn't exist
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            System.out.println("Directory creation result: " + created);
        }
    }

    public List<Article> findAll() {
        List<Article> articles = new ArrayList<>();
        File directory = new File(STORAGE_DIRECTORY);

        System.out.println("Looking for articles in: " + directory.getAbsolutePath());
        System.out.println("Directory exists: " + directory.exists());

        File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));
        System.out.println("Found " + (files != null ? files.length : 0) + " JSON files");

        if (files != null) {
            for (File file : files) {
                try {
                    System.out.println("Reading article from: " + file.getName());
                    Article article = objectMapper.readValue(file, Article.class);
                    articles.add(article);
                    System.out.println("Successfully loaded article: " + article.getTitle());
                } catch (IOException e) {
                    System.err.println("ERROR reading article file " + file.getName() + ": " + e.getMessage());
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
        System.out.println("==== ARTICLE REPOSITORY SAVE METHOD CALLED ====");

        if (article.getId() == null) {
            article.setId(java.util.UUID.randomUUID().toString());
            System.out.println("Generated new ID for article: " + article.getId());
        }

        if (article.getPublishedAt() == null) {
            article.setPublishedAt(LocalDateTime.now());
            System.out.println("Set publication date for article: " + article.getPublishedAt());
        }

        article.setUpdatedAt(LocalDateTime.now());
        System.out.println("Set updated date for article: " + article.getUpdatedAt());

        try {
            Path filePath = Paths.get(STORAGE_DIRECTORY, article.getId() + ".json");
            System.out.println("Attempting to save article to: " + filePath.toAbsolutePath());

            // Make sure directory exists
            File directory = new File(STORAGE_DIRECTORY);
            if (!directory.exists()) {
                System.out.println("Creating directory: " + directory.getAbsolutePath());
                boolean created = directory.mkdirs();
                System.out.println("Directory creation result: " + created);
            }

            // Try to create an empty file first to test write permissions
            try {
                File testFile = new File(directory, "test_write_permissions.txt");
                boolean testFileCreated = testFile.createNewFile();
                System.out.println("Test file creation result: " + testFileCreated);
                if (testFileCreated) {
                    testFile.delete(); // Clean up
                }
            } catch (Exception e) {
                System.err.println("ERROR creating test file: " + e.getMessage());
                e.printStackTrace();
            }

            // Now try to write the actual article
            objectMapper.writeValue(filePath.toFile(), article);
            System.out.println("Successfully saved article: " + article.getTitle());

            // Verify file was created
            File checkFile = new File(filePath.toFile().getAbsolutePath());
            System.out.println("Article file exists after save: " + checkFile.exists());
        } catch (IOException e) {
            System.err.println("ERROR saving article: " + e.getMessage());
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
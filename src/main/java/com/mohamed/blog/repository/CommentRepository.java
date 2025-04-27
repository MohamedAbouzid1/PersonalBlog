package com.mohamed.blog.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mohamed.blog.model.Comment;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CommentRepository {

    private final String STORAGE_DIRECTORY = "blog_data/comments";
    private final ObjectMapper objectMapper;

    public CommentRepository() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Create storage directory if it doesn't exist
        File directory = new File(STORAGE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public List<Comment> findAll() {
        List<Comment> comments = new ArrayList<>();
        File directory = new File(STORAGE_DIRECTORY);

        File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));
        if (files != null) {
            for (File file : files) {
                try {
                    Comment comment = objectMapper.readValue(file, Comment.class);
                    comments.add(comment);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return comments;
    }

    public List<Comment> findByArticleId(String articleId) {
        return findAll().stream()
                .filter(comment -> comment.getArticleId().equals(articleId))
                .collect(Collectors.toList());
    }

    public Optional<Comment> findById(String id) {
        Path filePath = Paths.get(STORAGE_DIRECTORY, id + ".json");
        if (Files.exists(filePath)) {
            try {
                Comment comment = objectMapper.readValue(filePath.toFile(), Comment.class);
                return Optional.of(comment);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    public Comment save(Comment comment) {
        try {
            objectMapper.writeValue(
                    Paths.get(STORAGE_DIRECTORY, comment.getId() + ".json").toFile(),
                    comment);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return comment;
    }

    public void deleteById(String id) {
        Path filePath = Paths.get(STORAGE_DIRECTORY, id + ".json");
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteByArticleId(String articleId) {
        List<Comment> commentsToDelete = findByArticleId(articleId);
        for (Comment comment : commentsToDelete) {
            deleteById(comment.getId());
        }
    }
}
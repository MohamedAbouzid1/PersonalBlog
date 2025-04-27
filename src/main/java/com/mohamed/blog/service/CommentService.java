package com.mohamed.blog.service;

import com.mohamed.blog.model.Comment;
import com.mohamed.blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll().stream()
                .sorted(Comparator.comparing(Comment::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    public List<Comment> getCommentsByArticleId(String articleId) {
        return commentRepository.findByArticleId(articleId).stream()
                .sorted(Comparator.comparing(Comment::getCreatedAt))
                .collect(Collectors.toList());
    }

    public Optional<Comment> getCommentById(String id) {
        return commentRepository.findById(id);
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public boolean deleteComment(String id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            commentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void deleteCommentsByArticleId(String articleId) {
        commentRepository.deleteByArticleId(articleId);
    }
}
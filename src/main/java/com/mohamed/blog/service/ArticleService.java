package com.mohamed.blog.service;

import com.mohamed.blog.model.Article;
import com.mohamed.blog.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentService commentService;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, CommentService commentService) {
        this.articleRepository = articleRepository;
        this.commentService = commentService;
    }

    public List<Article> getAllArticles() {
        System.out.println("Getting all articles...");
        List<Article> articles = articleRepository.findAll();
        System.out.println("Found " + articles.size() + " articles");
        return articles.stream()
                .sorted(Comparator.comparing(Article::getPublishedAt).reversed())
                .collect(Collectors.toList());
    }

    public Optional<Article> getArticleById(String id) {
        return articleRepository.findById(id);
    }

    public Article createArticle(Article article) {
        System.out.println("Creating article with title: " + article.getTitle());
        Article saved = articleRepository.save(article);
        System.out.println("Article created with ID: " + saved.getId());
        return saved;
    }

    public Optional<Article> updateArticle(String id, Article updatedArticle) {
        Optional<Article> existingArticle = articleRepository.findById(id);
        if (existingArticle.isPresent()) {
            Article article = existingArticle.get();
            article.setTitle(updatedArticle.getTitle());
            article.setContent(updatedArticle.getContent());
            article.setUpdatedAt(LocalDateTime.now());
            return Optional.of(articleRepository.save(article));
        }
        return Optional.empty();
    }

    public boolean deleteArticle(String id) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()) {
            // Delete associated comments first
            commentService.deleteCommentsByArticleId(id);
            // Then delete the article
            articleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
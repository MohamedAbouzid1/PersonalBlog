package com.mohamed.blog.controller;

import com.mohamed.blog.model.Article;
import com.mohamed.blog.model.Comment;
import com.mohamed.blog.service.ArticleService;
import com.mohamed.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class GuestController {

    private final ArticleService articleService;
    private final CommentService commentService;

    @Autowired
    public GuestController(ArticleService articleService, CommentService commentService) {
        this.articleService = articleService;
        this.commentService = commentService;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("articles", articleService.getAllArticles());
        return "index";
    }

    @GetMapping("/articles/{id}")
    public String viewArticle(@PathVariable String id, Model model) {
        Optional<Article> article = articleService.getArticleById(id);
        if (article.isPresent()) {
            // Get the article
            model.addAttribute("article", article.get());

            // Get comments for this article
            List<Comment> comments = commentService.getCommentsByArticleId(id);
            model.addAttribute("comments", comments);

            // Add an empty comment object for the comment form
            model.addAttribute("newComment", new Comment());

            return "article";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
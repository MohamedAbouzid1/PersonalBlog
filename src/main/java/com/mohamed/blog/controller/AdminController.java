package com.mohamed.blog.controller;

import com.mohamed.blog.model.Article;
import com.mohamed.blog.service.ArticleService;
import com.mohamed.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ArticleService articleService;
    private final CommentService commentService;

    @Autowired
    public AdminController(ArticleService articleService, CommentService commentService) {
        this.articleService = articleService;
        this.commentService = commentService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Article> articles = articleService.getAllArticles();

        // Get comment counts for each article
        Map<String, Integer> commentCounts = new HashMap<>();
        for (Article article : articles) {
            int count = commentService.getCommentsByArticleId(article.getId()).size();
            commentCounts.put(article.getId(), count);
        }

        model.addAttribute("articles", articles);
        model.addAttribute("commentCounts", commentCounts);

        return "admin/dashboard";
    }

    @GetMapping("/articles/new")
    public String newArticleForm(Model model) {
        model.addAttribute("article", new Article());
        return "admin/article-form";
    }

    @PostMapping("/articles")
    public String createArticle(@ModelAttribute Article article, RedirectAttributes redirectAttributes) {
        articleService.createArticle(article);
        redirectAttributes.addFlashAttribute("message", "Article created successfully!");
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/articles/{id}/edit")
    public String editArticleForm(@PathVariable String id, Model model) {
        Optional<Article> article = articleService.getArticleById(id);
        if (article.isPresent()) {
            model.addAttribute("article", article.get());
            return "admin/article-form";
        } else {
            return "redirect:/admin/dashboard";
        }
    }

    @PostMapping("/articles/{id}")
    public String updateArticle(@PathVariable String id, @ModelAttribute Article article,
            RedirectAttributes redirectAttributes) {
        articleService.updateArticle(id, article);
        redirectAttributes.addFlashAttribute("message", "Article updated successfully!");
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/articles/{id}/delete")
    public String deleteArticle(@PathVariable String id, RedirectAttributes redirectAttributes) {
        if (articleService.deleteArticle(id)) {
            redirectAttributes.addFlashAttribute("message", "Article deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Article not found!");
        }
        return "redirect:/admin/dashboard";
    }
}
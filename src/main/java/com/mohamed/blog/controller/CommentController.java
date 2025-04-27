package com.mohamed.blog.controller;

import com.mohamed.blog.model.Comment;
import com.mohamed.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/articles/{articleId}/comments")
    public String addComment(@PathVariable String articleId,
            @ModelAttribute Comment comment,
            RedirectAttributes redirectAttributes) {

        // Set the article ID for the comment
        comment.setArticleId(articleId);

        // Validate comment fields
        if (comment.getAuthorName() == null || comment.getAuthorName().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("commentError", "Name cannot be empty");
            return "redirect:/articles/" + articleId;
        }

        if (comment.getContent() == null || comment.getContent().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("commentError", "Comment content cannot be empty");
            return "redirect:/articles/" + articleId;
        }

        // Save the comment
        commentService.createComment(comment);
        redirectAttributes.addFlashAttribute("commentSuccess", "Comment added successfully");

        return "redirect:/articles/" + articleId;
    }

    @PostMapping("/admin/comments/{id}/delete")
    public String deleteComment(@PathVariable String id,
            @ModelAttribute("articleId") String articleId,
            RedirectAttributes redirectAttributes) {

        if (commentService.deleteComment(id)) {
            redirectAttributes.addFlashAttribute("message", "Comment deleted successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to delete comment");
        }

        return "redirect:/articles/" + articleId;
    }
}
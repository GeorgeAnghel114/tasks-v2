package com.example.tasks.controller;

import com.example.tasks.domain.Comment;
import com.example.tasks.service.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{taskId}/comments/{userId}")
    public Comment addCommentToTask(
            @PathVariable Long taskId,
            @PathVariable Long userId,
            @RequestBody Comment comment) {
        return commentService.addCommentToTask(taskId, userId, comment);
    }
}

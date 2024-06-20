package com.example.tasks.controller;

import com.example.tasks.domain.Comment;
import com.example.tasks.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{taskId}/comments/{userId}")
    public ResponseEntity<Comment> addCommentToTask(
            @PathVariable Long taskId,
            @PathVariable Long userId,
            @RequestBody Comment comment) {
        Comment createdComment = commentService.addCommentToTask(taskId, userId, comment);
        return ResponseEntity.ok(createdComment);
    }
}

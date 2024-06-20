package com.example.tasks.service;

import com.example.tasks.domain.Comment;
import com.example.tasks.domain.Task;
import com.example.tasks.domain.User;
import com.example.tasks.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final TaskService taskService;
    private final UserService userService;
    private final CommentRepository commentRepository;

    public CommentService(TaskService taskService, UserService userService, CommentRepository commentRepository) {
        this.taskService = taskService;
        this.userService = userService;
        this.commentRepository = commentRepository;
    }

    public Comment addCommentToTask(Long taskId, Long userId, Comment comment) {
        Task task = taskService.getTaskById(taskId);
        User user = userService.findById(userId);
        comment.setTask(task);
        comment.setUser(user);
        return commentRepository.save(comment);
    }
}

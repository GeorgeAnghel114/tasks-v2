package com.example.tasks.service;

import com.example.tasks.domain.Comment;
import com.example.tasks.domain.Task;
import com.example.tasks.domain.User;
import com.example.tasks.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CommentServiceTest {
    private CommentService commentService;
    @Mock
    private TaskService taskService;
    @Mock
    private UserService userService;
    @Mock
    private CommentRepository commentRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        commentService = new CommentService(taskService, userService, commentRepository);
    }


    @Test
    public void testAddCommentToTask() {
        Long taskId = 1L;
        Long userId = 1L;
        String commentText = "This is a comment";

        Task task = new Task();
        task.setId(taskId);
        task.setTitle("Task");

        User user = new User();
        user.setId(userId);
        user.setUsername("user1");

        Comment comment = new Comment();
        comment.setContent(commentText);

        when(taskService.getTaskById(taskId)).thenReturn(task);
        when(userService.findById(userId)).thenReturn(user);
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        Comment savedComment = commentService.addCommentToTask(taskId, userId, comment);

        assertEquals(commentText, savedComment.getContent());

        verify(taskService, times(1)).getTaskById(taskId);
        verify(userService, times(1)).findById(userId);
        verify(commentRepository, times(1)).save(comment);
    }
}

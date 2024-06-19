package com.example.tasks.service;

import com.example.tasks.domain.Task;
import com.example.tasks.domain.User;
import com.example.tasks.repository.TaskRepository;
import com.example.tasks.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    public Task assignTaskToUser(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        User user = userService.findById(userId);
        task.setResponsible(user);
        return taskRepository.save(task);
    }

    public Task updateTask(Long taskId, Task updatedTask) {
        Optional<Task> existingTaskOptional = taskRepository.findById(taskId);
        if (existingTaskOptional.isPresent()) {
            Task existingTask = existingTaskOptional.get();
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setDueDate(updatedTask.getDueDate());

            return taskRepository.save(existingTask);
        } else {
            throw new RuntimeException("Task not found");
        }
    }
}

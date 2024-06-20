package com.example.tasks.service;

import com.example.tasks.domain.Task;
import com.example.tasks.domain.User;
import com.example.tasks.domain.dtos.SearchDTO;
import com.example.tasks.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
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
        Task task = getTaskById(taskId);
        if(updatedTask.getResponsible() != null) {
            User user = userService.findById(updatedTask.getResponsible().getId());
            if(user.getId()!=null){
                task.setResponsible(user);
            }
        }
        task.setDueDate(updatedTask.getDueDate());
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        taskRepository.save(task);
        return task;
    }

    public Task getTaskById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public Task addSubTask(Long parentTaskId, Long subTaskId) {
        Task parentTask = taskRepository.findById(parentTaskId)
                .orElseThrow(() -> new RuntimeException("Parent task not found"));

        Task subTask = taskRepository.findById(subTaskId)
                .orElseThrow(() -> new RuntimeException("Subtask not found"));

        parentTask.addSubTask(subTask);
        return taskRepository.save(parentTask);
    }

    public List<Task> getSearchParams(SearchDTO searchDTO) {
        return taskRepository.findBySearch(
                searchDTO.getTitle(),
                searchDTO.getDueDate(),
                searchDTO.getDescription());
    }
}

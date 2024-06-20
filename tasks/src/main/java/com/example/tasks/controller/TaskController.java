package com.example.tasks.controller;

import com.example.tasks.domain.Task;
import com.example.tasks.domain.dtos.SearchDTO;
import com.example.tasks.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTaskById(@PathVariable Long taskId){
        try {
            return ResponseEntity.ok().body(taskService.getTaskById(Long.valueOf(taskId)));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Task not found!");
        }
    }

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.addTask(task));
    }

    @PutMapping("/{taskId}/user/{userId}")
    public Task assignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId) {
        return taskService.assignTaskToUser(taskId, userId);
    }

    @PostMapping("/{taskId}")
    public Task updateTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
        return taskService.updateTask(taskId, updatedTask);
    }

    @PostMapping("/{parentTaskId}/subtasks/{subTaskId}")
    public ResponseEntity<Task> addSubTask(@PathVariable Long parentTaskId, @PathVariable Long subTaskId) {
        return ResponseEntity.ok(taskService.addSubTask(parentTaskId, subTaskId));
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchParams(@RequestBody SearchDTO searchDTO) {
            return ResponseEntity.ok().body(taskService.getSearchParams(searchDTO));
    }
}

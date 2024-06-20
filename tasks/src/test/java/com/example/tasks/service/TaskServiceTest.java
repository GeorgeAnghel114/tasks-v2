package com.example.tasks.service;

import com.example.tasks.domain.Task;
import com.example.tasks.domain.User;
import com.example.tasks.domain.dtos.SearchDTO;
import com.example.tasks.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskServiceTest {
    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskService(taskRepository, userService);
    }

    @Test
    public void testAddTask() {
        Task taskToAdd = new Task();
        taskToAdd.setTitle("Test Task");

        when(taskRepository.save(any(Task.class))).thenReturn(taskToAdd);
        Task addedTask = taskService.addTask(taskToAdd);

        assertEquals("Test Task", addedTask.getTitle());
        verify(taskRepository).save(taskToAdd);
    }

    @Test
    public void testAssignTaskToUser() {
        Task task = new Task();
        task.setId(1L);

        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        when(taskRepository.findById(1L)).thenReturn(java.util.Optional.of(task));
        when(userService.findById(1L)).thenReturn(user);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task assignedTask = taskService.assignTaskToUser(1L, 1L);

        assertEquals(user, assignedTask.getResponsible());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testUpdateTask() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setTitle("Existing Task");

        Task updatedTask = new Task();
        updatedTask.setId(1L);
        updatedTask.setTitle("Updated Task");
        updatedTask.setDescription("Updated Description");

        User responsibleUser = new User();
        responsibleUser.setId(2L);
        responsibleUser.setUsername("responsibleUser");

        when(taskRepository.findById(1L)).thenReturn(java.util.Optional.of(existingTask));
        when(userService.findById(2L)).thenReturn(responsibleUser);
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        Task result = taskService.updateTask(1L, updatedTask);

        assertEquals("Updated Task", result.getTitle());
        assertEquals("Updated Description", result.getDescription());

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    public void testGetTaskById() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task foundTask = taskService.getTaskById(1L);

        assertEquals("Test Task", foundTask.getTitle());
        assertEquals(1L, foundTask.getId());

        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddSubTask() {
        Task parentTask = new Task();
        parentTask.setId(1L);
        parentTask.setTitle("Parent Task");

        Task subTask = new Task();
        subTask.setId(2L);
        subTask.setTitle("Sub Task");

        parentTask.addSubTask(subTask);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(parentTask));
        when(taskRepository.findById(2L)).thenReturn(Optional.of(subTask));
        when(taskRepository.save(any(Task.class))).thenReturn(parentTask);

        Task updatedParentTask = taskService.addSubTask(1L, 2L);

        assertEquals(2, updatedParentTask.getSubTasks().size());
        assertEquals(subTask, updatedParentTask.getSubTasks().get(0));

        verify(taskRepository, times(1)).save(parentTask);

        assertThrows(IllegalArgumentException.class, () -> parentTask.addSubTask(parentTask));
    }

    @Test
    public void testGetSearchParams() {
        SearchDTO searchDTO = new SearchDTO("Test Task","Test Description", null);

        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Test Task 1");
        task1.setDescription("Test Description 1");

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Test Task 2");
        task2.setDescription("Test Description 2");

        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);

        when(taskRepository.findBySearch("Test Task", null, "Test Description")).thenReturn(tasks);

        List<Task> foundTasks = taskService.getSearchParams(searchDTO);

        assertEquals(2, foundTasks.size());
        assertEquals("Test Task 1", foundTasks.get(0).getTitle());
        assertEquals("Test Description 2", foundTasks.get(1).getDescription());

        verify(taskRepository, times(1)).findBySearch("Test Task", null, "Test Description");
    }
}

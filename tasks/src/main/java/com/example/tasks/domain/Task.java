package com.example.tasks.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "responsible_id")
    private User responsible;

    @ManyToOne
    @JoinColumn(name = "parent_task_id")
    @JsonBackReference
    private Task parentTask;

    @OneToMany(mappedBy = "parentTask", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Task> subTasks = new ArrayList<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    public void addSubTask(Task subTask) {
        if (subTask == null) {
            throw new IllegalArgumentException("Subtask cannot be null");
        }
        if (subTask == this) {
            throw new IllegalArgumentException("A task cannot be a subtask of itself");
        }
        Task currentParent = this;
        while (currentParent != null) {
            if (currentParent.equals(subTask)) {
                throw new IllegalArgumentException("Cyclic dependency detected: Adding this subtask would create a loop");
            }
            currentParent = currentParent.getParentTask();
        }
        subTasks.add(subTask);
        subTask.setParentTask(this);
    }
}
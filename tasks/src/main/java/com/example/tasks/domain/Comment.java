package com.example.tasks.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String content;

    @ManyToOne
    @JoinColumn(name = "task_id")
    @JsonBackReference
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

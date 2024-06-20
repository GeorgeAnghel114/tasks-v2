package com.example.tasks.domain.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Data
public class SearchDTO {
        private String title;
        private String description;
        private LocalDate dueDate;
}


package com.example.tasks.repository;

import com.example.tasks.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT t FROM Task t WHERE (:title IS NULL OR LOWER(t.title) like LOWER(concat('%', cast(:title as string ),'%'))) " +
            "AND (cast(:dueDate as date) IS NULL OR t.dueDate > :dueDate) "+
            "and (:description IS NULL OR LOWER(t.description) like LOWER(concat('%', cast(:description as string ),'%')))")
    List<Task> findBySearch(@Param("title") String title,
                            @Param("dueDate") LocalDate dueDate,
                            @Param("description") String description);
}

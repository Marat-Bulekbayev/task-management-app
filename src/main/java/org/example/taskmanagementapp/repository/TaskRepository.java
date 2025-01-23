package org.example.taskmanagementapp.repository;

import org.example.taskmanagementapp.model.entity.Task;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @EntityGraph(attributePaths = {"author", "assignee", "comments", "comments.author"})
    List<Task> findAll();

    @EntityGraph(attributePaths = {"author", "assignee", "comments", "comments.author"})
    List<Task> findAllByAuthorId(Long authorId);

    @EntityGraph(attributePaths = {"author", "assignee", "comments", "comments.author"})
    Optional<Task> findByIdAndAuthorId(Long id, Long authorId);

    @EntityGraph(attributePaths = {"author", "assignee", "comments", "comments.author"})
    Optional<Task> findByIdAndAssigneeId(Long id, Long assigneeId);
}

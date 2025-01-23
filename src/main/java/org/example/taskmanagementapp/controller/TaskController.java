package org.example.taskmanagementapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.taskmanagementapp.model.dto.CommentDto;
import org.example.taskmanagementapp.model.dto.TaskDto;
import org.example.taskmanagementapp.model.request.ChangeStatusRequest;
import org.example.taskmanagementapp.model.request.CommentTaskRequest;
import org.example.taskmanagementapp.model.request.CreateTaskRequest;
import org.example.taskmanagementapp.model.request.UpdateTaskRequest;
import org.example.taskmanagementapp.model.response.CreateTaskResponse;
import org.example.taskmanagementapp.model.response.UpdateTaskResponse;
import org.example.taskmanagementapp.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<CreateTaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request) {
        String email = getCurrentUserEmail();

        return ResponseEntity.ok(taskService.createTask(request, email));
    }

    @PostMapping("/{taskId}/assign/{assigneeId}")
    public void assignTaskToUser(@PathVariable Long taskId, @PathVariable Long assigneeId) {
        String email = getCurrentUserEmail();

        taskService.assignTaskToUser(taskId, email, assigneeId);
    }

    @PostMapping("/{taskId}/change-status")
    public void assignTaskToUser(@PathVariable Long taskId, @RequestBody ChangeStatusRequest request) {
        String email = getCurrentUserEmail();

        taskService.changeTaskStatus(taskId, email, request.getStatus());
    }

    @PostMapping("/{taskId}/add-comment")
    public ResponseEntity<CommentDto> addCommentToTask(@PathVariable Long taskId, @RequestBody CommentTaskRequest request) {
        String email = getCurrentUserEmail();

        return ResponseEntity.ok(taskService.addCommentToTask(taskId, request, email));
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> findAllTasks() {
        String email = getCurrentUserEmail();

        return ResponseEntity.ok(taskService.findAllTasks(email));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> findTaskById(@PathVariable Long taskId) {
        String email = getCurrentUserEmail();

        return ResponseEntity.ok(taskService.findTaskById(taskId, email));
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<UpdateTaskResponse> updateTask(@PathVariable Long taskId, @Valid @RequestBody UpdateTaskRequest request) {
        String email = getCurrentUserEmail();

        return ResponseEntity.ok(taskService.updateTask(taskId, request, email));
    }

    @DeleteMapping("/{taskId}")
    public void deleteTaskById(@PathVariable Long taskId) {
        String email = getCurrentUserEmail();

        taskService.deleteTaskById(taskId, email);
    }

    private String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}

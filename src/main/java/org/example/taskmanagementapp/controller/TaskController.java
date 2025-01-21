package org.example.taskmanagementapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.taskmanagementapp.model.dto.TaskDto;
import org.example.taskmanagementapp.model.request.CreateTaskRequest;
import org.example.taskmanagementapp.model.response.CreateTaskResponse;
import org.example.taskmanagementapp.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
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
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity.ok(taskService.createTask(request, email));
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> findAllTasks() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity.ok(taskService.findAllTasks(email));
    }
}

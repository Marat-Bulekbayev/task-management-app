package org.example.taskmanagementapp.service;

import org.example.taskmanagementapp.model.dto.TaskDto;
import org.example.taskmanagementapp.model.entity.Task;
import org.example.taskmanagementapp.model.request.CreateTaskRequest;
import org.example.taskmanagementapp.model.request.UpdateTaskRequest;
import org.example.taskmanagementapp.model.response.CreateTaskResponse;
import org.example.taskmanagementapp.model.response.UpdateTaskResponse;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    CreateTaskResponse createTask(CreateTaskRequest request, String email);

    List<TaskDto> findAllTasks(String email);

    Optional<Task> findTaskById(Long id);

    UpdateTaskResponse updateTask(UpdateTaskRequest request);

    void deleteTaskById(Long id);
}

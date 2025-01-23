package org.example.taskmanagementapp.service;

import org.example.taskmanagementapp.model.dto.CommentDto;
import org.example.taskmanagementapp.model.dto.TaskDto;
import org.example.taskmanagementapp.model.enums.TaskStatus;
import org.example.taskmanagementapp.model.request.CommentTaskRequest;
import org.example.taskmanagementapp.model.request.CreateTaskRequest;
import org.example.taskmanagementapp.model.request.UpdateTaskRequest;
import org.example.taskmanagementapp.model.response.CreateTaskResponse;
import org.example.taskmanagementapp.model.response.UpdateTaskResponse;
import org.springframework.data.domain.Page;

public interface TaskService {

    CreateTaskResponse createTask(CreateTaskRequest request, String email);

    Page<TaskDto> findAllTasks(String email, int page, int size, String sortBy, boolean ascending);

    TaskDto findTaskById(Long taskId, String email);

    UpdateTaskResponse updateTask(Long taskId, UpdateTaskRequest request, String email);

    void deleteTaskById(Long taskId, String email);

    void assignTaskToUser(Long taskId, String email, Long assigneeId);

    void changeTaskStatus(Long taskId, String email, TaskStatus status);

    CommentDto addCommentToTask(Long taskId, CommentTaskRequest request, String email);
}

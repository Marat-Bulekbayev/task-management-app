package org.example.taskmanagementapp.service;

import org.example.taskmanagementapp.model.dto.CommentDto;
import org.example.taskmanagementapp.model.dto.TaskDto;
import org.example.taskmanagementapp.model.enums.TaskStatus;
import org.example.taskmanagementapp.model.request.CommentTaskRequest;
import org.example.taskmanagementapp.model.request.CreateTaskRequest;
import org.example.taskmanagementapp.model.request.UpdateTaskRequest;
import org.example.taskmanagementapp.model.response.CreateTaskResponse;
import org.example.taskmanagementapp.model.response.UpdateTaskResponse;

import java.util.List;

public interface TaskService {

    CreateTaskResponse createTask(CreateTaskRequest request, String email);

    List<TaskDto> findAllTasks(String email);

    TaskDto findTaskById(Long taskId, String email);

    UpdateTaskResponse updateTask(Long taskId, UpdateTaskRequest request, String email);

    void deleteTaskById(Long taskId, String email);

    void assignTaskToUser(Long taskId, String email, Long assigneeId);

    void changeTaskStatus(Long taskId, String email, TaskStatus status);

    CommentDto addCommentToTask(Long taskId, CommentTaskRequest request, String email);
}

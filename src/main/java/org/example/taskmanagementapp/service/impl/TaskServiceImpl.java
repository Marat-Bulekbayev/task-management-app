package org.example.taskmanagementapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.taskmanagementapp.exception.TaskNotFoundException;
import org.example.taskmanagementapp.mapper.TaskMapper;
import org.example.taskmanagementapp.model.dto.TaskDto;
import org.example.taskmanagementapp.model.entity.Task;
import org.example.taskmanagementapp.model.entity.User;
import org.example.taskmanagementapp.model.enums.TaskStatus;
import org.example.taskmanagementapp.model.enums.UserRole;
import org.example.taskmanagementapp.model.request.CreateTaskRequest;
import org.example.taskmanagementapp.model.request.UpdateTaskRequest;
import org.example.taskmanagementapp.model.response.CreateTaskResponse;
import org.example.taskmanagementapp.model.response.UpdateTaskResponse;
import org.example.taskmanagementapp.repository.TaskRepository;
import org.example.taskmanagementapp.repository.UserRepository;
import org.example.taskmanagementapp.service.TaskService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @Transactional
    @Override
    public CreateTaskResponse createTask(CreateTaskRequest request, String email) {
        User user = userRepository.findByEmail(email).orElseThrow();

        Task task = taskMapper.toTaskEntity(request);
        task.setStatus(TaskStatus.TO_DO);
        task.setAuthor(user);
        Task savedTask = taskRepository.save(task);

        log.info("Create new task with id: {} by user: {}", savedTask.getId(), user.getEmail());
        return taskMapper.toCreateTaskResponse(savedTask);
    }

    @Override
    public List<TaskDto> findAllTasks(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();

        if (user.getRole().equals(UserRole.ADMIN)) {
            return taskRepository.findAll().stream().map(taskMapper::toTaskDto).toList();
        } else {
            return taskRepository.findAllByAuthorId(user.getId()).stream().map(taskMapper::toTaskDto).toList();
        }
    }

    @Override
    public TaskDto findTaskById(Long id, String email) {
        User user = userRepository.findByEmail(email).orElseThrow();

        if (user.getRole().equals(UserRole.ADMIN)) {
            Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(String.format("Task with id: %d not found", id)));
            return taskMapper.toTaskDto(task);
        } else {
            Task task = taskRepository.findByIdAndAuthorId(id, user.getId())
                    .orElseThrow(() -> new TaskNotFoundException(String.format("Task with id: %d not found for user %s", id, user.getEmail())));
            return taskMapper.toTaskDto(task);
        }
    }

    @Transactional
    @Override
    public UpdateTaskResponse updateTask(UpdateTaskRequest request) {
        return null;
    }

    @Transactional
    @Override
    public void deleteTaskById(Long id, String username) {
        User user = userRepository.findByEmail(username).orElseThrow();

        if (user.getRole().equals(UserRole.ADMIN)) {
            log.info("Delete task with id: {} by admin user: {}", id, user.getEmail());
            taskRepository.deleteById(id);
        } else {
            throw new AccessDeniedException("You don't have permission for this action");
        }
    }
}

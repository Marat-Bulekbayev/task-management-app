package org.example.taskmanagementapp.service.impl;

import lombok.RequiredArgsConstructor;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public Optional<Task> findTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Transactional
    @Override
    public UpdateTaskResponse updateTask(UpdateTaskRequest request) {
        return null;
    }

    @Transactional
    @Override
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }
}

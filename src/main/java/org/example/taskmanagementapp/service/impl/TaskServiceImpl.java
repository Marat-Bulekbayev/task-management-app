package org.example.taskmanagementapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.taskmanagementapp.exception.TaskNotFoundException;
import org.example.taskmanagementapp.exception.UserNotFoundException;
import org.example.taskmanagementapp.mapper.TaskMapper;
import org.example.taskmanagementapp.model.dto.CommentDto;
import org.example.taskmanagementapp.model.dto.TaskDto;
import org.example.taskmanagementapp.model.entity.Task;
import org.example.taskmanagementapp.model.entity.User;
import org.example.taskmanagementapp.model.enums.TaskStatus;
import org.example.taskmanagementapp.model.enums.UserRole;
import org.example.taskmanagementapp.model.request.CommentTaskRequest;
import org.example.taskmanagementapp.model.request.CreateTaskRequest;
import org.example.taskmanagementapp.model.request.UpdateTaskRequest;
import org.example.taskmanagementapp.model.response.CreateTaskResponse;
import org.example.taskmanagementapp.model.response.UpdateTaskResponse;
import org.example.taskmanagementapp.repository.TaskRepository;
import org.example.taskmanagementapp.repository.UserRepository;
import org.example.taskmanagementapp.service.CommentService;
import org.example.taskmanagementapp.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentService commentService;
    private final TaskMapper taskMapper;

    @Transactional
    @Override
    public CreateTaskResponse createTask(CreateTaskRequest request, String email) {
        User currentUser = findUserByEmail(email);

        Task task = taskMapper.toTaskEntity(request);
        task.setStatus(TaskStatus.TO_DO);
        task.setAuthor(currentUser);

        if (request.getAssigneeId() != null) {
            User assigneeUser = findUserById(request.getAssigneeId());
            task.setAssignee(assigneeUser);
        }

        Task savedTask = taskRepository.save(task);

        log.info("Create new task with id: {} by user: {}", savedTask.getId(), currentUser.getEmail());
        return taskMapper.toCreateTaskResponse(savedTask);
    }

    @Override
    public Page<TaskDto> findAllTasks(String email, int page, int size, String sortBy, boolean ascending) {
        User currentUser = userRepository.findByEmail(email).orElseThrow();
        Sort.Direction direction = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        if (isCurrentUserHasAdminRole(currentUser)) {
            return taskRepository.findAll(pageable).map(taskMapper::toTaskDto);
        } else {
            return taskRepository.findAllByAuthorId(currentUser.getId(), pageable).map(taskMapper::toTaskDto);
        }
    }

    @Override
    public TaskDto findTaskById(Long taskId, String email) {
        User currentUser = findUserByEmail(email);
        Task task;

        if (isCurrentUserHasAdminRole(currentUser)) {
            task = findTaskById(taskId);
        } else {
            task = findTaskByIdAndAuthorId(taskId, currentUser.getId());
        }

        return taskMapper.toTaskDto(task);
    }

    @Transactional
    @Override
    public UpdateTaskResponse updateTask(Long taskId, UpdateTaskRequest request, String email) {
        User currentUser = findUserByEmail(email);

        Task taskForUpdate;

        if (isCurrentUserHasAdminRole(currentUser)) {
            taskForUpdate = findTaskById(taskId);
        } else {
            taskForUpdate = findTaskByIdAndAssigneeId(taskId, currentUser.getId());
        }

        return taskMapper.toUpdateTaskResponse(updateTask(taskForUpdate, request));
    }

    @Transactional
    @Override
    public void assignTaskToUser(Long taskId, String email, Long assigneeId) {
        User currentUser = findUserByEmail(email);
        User assignee = findUserById(assigneeId);
        Task task = findTaskById(taskId);

        if (isCurrentUserHasAdminRole(currentUser)) {
            task.setAssignee(assignee);
            taskRepository.save(task);
        } else {
            throw new AccessDeniedException("You don't have permission for this action");
        }
    }

    @Transactional
    @Override
    public void changeTaskStatus(Long taskId, String email, TaskStatus status) {
        User currentUser = findUserByEmail(email);
        Task task;

        if (isCurrentUserHasAdminRole(currentUser)) {
            task = findTaskById(taskId);
        } else {
            task = findTaskByIdAndAssigneeId(taskId, currentUser.getId());
        }

        task.setStatus(status);
        taskRepository.save(task);
    }

    @Transactional
    @Override
    public void deleteTaskById(Long taskId, String email) {
        User currentUser = findUserByEmail(email);

        if (isCurrentUserHasAdminRole(currentUser)) {
            log.info("Delete task with id: {} by admin user: {}", taskId, currentUser.getEmail());
            taskRepository.deleteById(taskId);
        } else {
            throw new AccessDeniedException("You don't have permission for this action");
        }
    }

    @Transactional
    @Override
    public CommentDto addCommentToTask(Long taskId, CommentTaskRequest request, String email) {
        User currentUser = findUserByEmail(email);
        Task task;

        if (isCurrentUserHasAdminRole(currentUser)) {
            task = findTaskById(taskId);
        } else {
            task = findTaskByIdAndAssigneeId(taskId, currentUser.getId());
        }

        return commentService.addCommentToTask(task, request.getContent(), currentUser);
    }

    private boolean isCurrentUserHasAdminRole(User user) {
        return user.getRole().equals(UserRole.ADMIN);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(String.format("User with id: %d not found", userId)));
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(String.format("User with email: %s not found", email)));
    }

    private Task findTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(String.format("Task with id: %d not found", taskId)));
    }

    private Task findTaskByIdAndAuthorId(Long taskId, Long authorId) {
        return taskRepository.findByIdAndAuthorId(taskId, authorId)
                .orElseThrow(() -> new TaskNotFoundException(String.format("Task with id: %d not found with authorId: %d", taskId, authorId)));
    }

    private Task findTaskByIdAndAssigneeId(Long taskId, Long assigneeId) {
        return taskRepository.findByIdAndAssigneeId(taskId, assigneeId).orElseThrow(() -> new TaskNotFoundException(String.format("Task with id: %d not found", taskId)));
    }

    private Task updateTask(Task taskForUpdate, UpdateTaskRequest request) {
        if (request.getTitle() != null && !request.getTitle().equals(taskForUpdate.getTitle())) {
            taskForUpdate.setTitle(request.getTitle());
        }
        if (request.getDescription() != null && !request.getDescription().equals(taskForUpdate.getDescription())) {
            taskForUpdate.setDescription(request.getDescription());
        }
        if (request.getStatus() != null && !request.getStatus().equals(taskForUpdate.getStatus())) {
            taskForUpdate.setStatus(request.getStatus());
        }
        if (request.getPriority() != null && !request.getPriority().equals(taskForUpdate.getPriority())) {
            taskForUpdate.setPriority(request.getPriority());
        }

        return taskRepository.save(taskForUpdate);
    }
}

package org.example.taskmanagementapp.service.impl;

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
import org.example.taskmanagementapp.service.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentService commentService;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void testCreateTask() {
        CreateTaskRequest request = new CreateTaskRequest();
        CreateTaskResponse expectedResponse = new CreateTaskResponse();
        User user = User.builder().role(UserRole.ADMIN).build();
        Task task = Task.builder().build();

        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        when(taskMapper.toTaskEntity(any())).thenReturn(task);
        when(taskRepository.save(any())).thenReturn(task);
        when(taskMapper.toCreateTaskResponse(any())).thenReturn(expectedResponse);

        CreateTaskResponse response = taskService.createTask(request, "email");

        assertNotNull(response);
        assertEquals(expectedResponse, response);

        verify(userRepository).findByEmail(any());
        verify(taskMapper).toTaskEntity(any());
        verify(taskRepository).save(any());
        verify(taskMapper).toCreateTaskResponse(any());
    }

    @Test
    void testFindTaskById() {
        User user = User.builder().role(UserRole.ADMIN).build();
        Task task = Task.builder().build();
        TaskDto dto = TaskDto.builder().build();

        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        when(taskRepository.findById(any())).thenReturn(Optional.ofNullable(task));
        when(taskMapper.toTaskDto(any())).thenReturn(dto);

        TaskDto result = taskService.findTaskById(1L, "email");

        assertNotNull(result);
        assertEquals(dto, result);

        verify(userRepository).findByEmail(any());
        verify(taskRepository).findById(any());
        verify(taskMapper).toTaskDto(any());
    }

    @Test
    void testUpdateTask() {
        UpdateTaskRequest request = new UpdateTaskRequest();
        UpdateTaskResponse expectedResponse = new UpdateTaskResponse();
        User user = User.builder().role(UserRole.ADMIN).build();
        Task task = Task.builder().build();

        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        when(taskRepository.findById(any())).thenReturn(Optional.ofNullable(task));
        when(taskRepository.save(any())).thenReturn(task);
        when(taskMapper.toUpdateTaskResponse(any())).thenReturn(expectedResponse);

        UpdateTaskResponse response = taskService.updateTask(1L, request, "email");

        assertNotNull(response);
        assertEquals(expectedResponse, response);

        verify(userRepository).findByEmail(any());
        verify(taskRepository).findById(any());
        verify(taskRepository).save(any());
        verify(taskMapper).toUpdateTaskResponse(any());
    }

    @Test
    void testAssignTaskToUser() {
        User user = User.builder().role(UserRole.ADMIN).build();
        User assignee = User.builder().role(UserRole.USER).build();
        Task task = Task.builder().build();

        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(assignee));
        when(taskRepository.findById(any())).thenReturn(Optional.ofNullable(task));
        when(taskRepository.save(any())).thenReturn(task);

        taskService.assignTaskToUser(1L, "email", 1L);

        verify(userRepository).findByEmail(any());
        verify(userRepository).findById(any());
        verify(taskRepository).findById(any());
        verify(taskRepository).save(any());
    }

    @Test
    void testChangeTaskStatus() {
        User user = User.builder().role(UserRole.ADMIN).build();
        Task task = Task.builder().build();

        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        when(taskRepository.findById(any())).thenReturn(Optional.ofNullable(task));
        when(taskRepository.save(any())).thenReturn(task);

        taskService.changeTaskStatus(1L, "email", TaskStatus.IN_PROGRESS);

        verify(userRepository).findByEmail(any());
        verify(taskRepository).findById(any());
        verify(taskRepository).save(any());
    }

    @Test
    void testDeleteTaskById() {
        User user = User.builder().role(UserRole.ADMIN).build();

        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));

        taskService.deleteTaskById(1L, "email");

        verify(userRepository).findByEmail(any());
        verify(taskRepository).deleteById(any());
    }
}

package org.example.taskmanagementapp.mapper;

import org.example.taskmanagementapp.model.dto.TaskDto;
import org.example.taskmanagementapp.model.entity.Task;
import org.example.taskmanagementapp.model.request.CreateTaskRequest;
import org.example.taskmanagementapp.model.response.CreateTaskResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toTaskEntity(CreateTaskRequest request);

    CreateTaskResponse toCreateTaskResponse(Task task);

    TaskDto toTaskDto(Task task);
}

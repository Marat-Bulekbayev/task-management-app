package org.example.taskmanagementapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.example.taskmanagementapp.model.response.ErrorResponse;
import org.example.taskmanagementapp.model.response.UpdateTaskResponse;
import org.example.taskmanagementapp.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/tasks")
@Tag(name = "Tasks", description = "Endpoints for managing tasks")
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Create task", description = "Creates a task.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task created successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateTaskResponse.class)))
    })
    @PostMapping
    public ResponseEntity<CreateTaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request) {
        String email = getCurrentUserEmail();

        return ResponseEntity.ok(taskService.createTask(request, email));
    }

    @Operation(summary = "Assign task to user", description = "Assigns a task to a specific user.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task assigned successfully")
    })
    @PostMapping("/{taskId}/assign/{assigneeId}")
    public void assignTaskToUser(@PathVariable Long taskId, @PathVariable Long assigneeId) {
        String email = getCurrentUserEmail();

        taskService.assignTaskToUser(taskId, email, assigneeId);
    }

    @Operation(summary = "Change task status", description = "Changes the status of a task.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task status changed successfully")
    })
    @PostMapping("/{taskId}/change-status")
    public void assignTaskToUser(@PathVariable Long taskId, @RequestBody ChangeStatusRequest request) {
        String email = getCurrentUserEmail();

        taskService.changeTaskStatus(taskId, email, request.getStatus());
    }

    @Operation(summary = "Add comment to task", description = "Adds a comment to a task.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment added successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CommentDto.class))),
    })
    @PostMapping("/{taskId}/add-comment")
    public ResponseEntity<CommentDto> addCommentToTask(@PathVariable Long taskId, @RequestBody CommentTaskRequest request) {
        String email = getCurrentUserEmail();

        return ResponseEntity.ok(taskService.addCommentToTask(taskId, request, email));
    }

    @Operation(summary = "Find all tasks", description = "Retrieves all tasks for the current user.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = TaskDto.class), minItems = 3))),
    })
    @GetMapping
    public ResponseEntity<Page<TaskDto>> findAllTasks(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size,
                                                      @RequestParam(defaultValue = "title") String sortBy,
                                                      @RequestParam(defaultValue = "true") boolean ascending) {
        String email = getCurrentUserEmail();

        return ResponseEntity.ok(taskService.findAllTasks(email, page, size, sortBy, ascending));
    }

    @Operation(summary = "Find task by ID", description = "Retrieves a task by its ID.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TaskDto.class))),
    })
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> findTaskById(@PathVariable Long taskId) {
        String email = getCurrentUserEmail();

        return ResponseEntity.ok(taskService.findTaskById(taskId, email));
    }

    @Operation(summary = "Update task", description = "Updates a task.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateTaskResponse.class))),
    })
    @PatchMapping("/{taskId}")
    public ResponseEntity<UpdateTaskResponse> updateTask(@PathVariable Long taskId, @Valid @RequestBody UpdateTaskRequest request) {
        String email = getCurrentUserEmail();

        return ResponseEntity.ok(taskService.updateTask(taskId, request, email));
    }

    @Operation(summary = "Delete task", description = "Deletes a task by its ID.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task deleted successfully")
    })
    @DeleteMapping("/{taskId}")
    public void deleteTaskById(@PathVariable Long taskId) {
        String email = getCurrentUserEmail();

        taskService.deleteTaskById(taskId, email);
    }

    private String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}

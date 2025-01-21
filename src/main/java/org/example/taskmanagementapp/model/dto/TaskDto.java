package org.example.taskmanagementapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.taskmanagementapp.model.enums.TaskPriority;
import org.example.taskmanagementapp.model.enums.TaskStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class TaskDto {

    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private UserDto author;
    private UserDto assignee;
    private List<CommentDto> comments;
}

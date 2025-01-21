package org.example.taskmanagementapp.model.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.taskmanagementapp.model.entity.User;
import org.example.taskmanagementapp.model.enums.TaskPriority;
import org.example.taskmanagementapp.model.enums.TaskStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CreateTaskResponse {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private User author;
    private User assignee;
}

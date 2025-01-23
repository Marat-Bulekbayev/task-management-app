package org.example.taskmanagementapp.service;

import org.example.taskmanagementapp.model.dto.CommentDto;
import org.example.taskmanagementapp.model.entity.Task;
import org.example.taskmanagementapp.model.entity.User;

public interface CommentService {

    CommentDto addCommentToTask(Task task, String content, User author);
}

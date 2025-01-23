package org.example.taskmanagementapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagementapp.mapper.CommentMapper;
import org.example.taskmanagementapp.model.dto.CommentDto;
import org.example.taskmanagementapp.model.entity.Comment;
import org.example.taskmanagementapp.model.entity.Task;
import org.example.taskmanagementapp.model.entity.User;
import org.example.taskmanagementapp.repository.CommentRepository;
import org.example.taskmanagementapp.service.CommentService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto addCommentToTask(Task task, String content, User author) {
        Comment comment = Comment.builder()
                .task(task)
                .content(content)
                .author(author)
                .build();

        Comment savedComment = commentRepository.save(comment);

        return commentMapper.toCommentDto(savedComment);
    }
}

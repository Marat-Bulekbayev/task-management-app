package org.example.taskmanagementapp.service.impl;

import org.example.taskmanagementapp.mapper.CommentMapper;
import org.example.taskmanagementapp.model.dto.CommentDto;
import org.example.taskmanagementapp.model.entity.Comment;
import org.example.taskmanagementapp.model.entity.Task;
import org.example.taskmanagementapp.model.entity.User;
import org.example.taskmanagementapp.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void testAddCommentToTask() {
        Task task = Task.builder().build();
        User author = User.builder().build();
        Comment comment = Comment.builder().build();
        CommentDto expectedDto = CommentDto.builder().build();

        when(commentRepository.save(any())).thenReturn(comment);
        when(commentMapper.toCommentDto(any())).thenReturn(expectedDto);

        CommentDto result = commentService.addCommentToTask(task, "content", author);

        assertNotNull(result);
        assertEquals(expectedDto, result);

        verify(commentRepository).save(any());
        verify(commentMapper).toCommentDto(any());
    }
}

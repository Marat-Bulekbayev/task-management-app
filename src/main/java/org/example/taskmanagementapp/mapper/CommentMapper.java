package org.example.taskmanagementapp.mapper;

import org.example.taskmanagementapp.model.dto.CommentDto;
import org.example.taskmanagementapp.model.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDto toCommentDto(Comment comment);
}

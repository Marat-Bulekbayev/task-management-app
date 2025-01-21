package org.example.taskmanagementapp.mapper;

import org.example.taskmanagementapp.model.entity.User;
import org.example.taskmanagementapp.model.request.RegistrationRequest;
import org.example.taskmanagementapp.model.response.RegistrationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", ignore = true)
    User toUserEntity(RegistrationRequest request);

    RegistrationResponse toRegistrationResponse(User user);
}

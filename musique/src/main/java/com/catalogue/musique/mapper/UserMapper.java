package com.catalogue.musique.mapper;

import com.catalogue.musique.dto.reponse.UserResponse;
import com.catalogue.musique.dto.request.UserRequest;
import com.catalogue.musique.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "roles", source = "roleRequests")
    User toEntity(UserRequest request);
    UserResponse toResponse(User user);
}

package com.catalogue.musique.mapper;

import com.catalogue.musique.dto.request.UserRequest;
import com.catalogue.musique.dto.response.UserResponse;
import com.catalogue.musique.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")

public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse toDto(User user);


    User toEntity(UserRequest request);
}

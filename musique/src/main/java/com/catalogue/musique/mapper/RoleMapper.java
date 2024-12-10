package com.catalogue.musique.mapper;

import com.catalogue.musique.dto.request.RoleRequest;
import com.catalogue.musique.dto.reponse.RoleResponse;
import com.catalogue.musique.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rolename", source = "rolename")
    Role toEntity(RoleRequest roleRequest);

    @Mapping(target = "rolename", source = "rolename")
    RoleResponse toResponse(Role role);
}

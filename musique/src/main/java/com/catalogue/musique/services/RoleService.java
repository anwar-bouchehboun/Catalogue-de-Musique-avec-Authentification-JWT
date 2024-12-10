package com.catalogue.musique.services;

import com.catalogue.musique.dto.request.RoleRequest;
import com.catalogue.musique.dto.reponse.RoleResponse;
import com.catalogue.musique.mapper.RoleMapper;
import com.catalogue.musique.model.Role;
import com.catalogue.musique.repository.RoleRepository;
import com.catalogue.musique.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper = RoleMapper.INSTANCE;

    public RoleResponse createRole(RoleRequest roleRequest) {
        roleRepository.findByRolename(roleRequest.getRolename())
                .ifPresent(existingRole -> {
                    throw new ValidationException("Le nom du rôle existe déjà : " + roleRequest.getRolename());
                });

        Role role = roleMapper.toEntity(roleRequest);

        Role savedRole = roleRepository.save(role);

        return roleMapper.toResponse(savedRole);
    }


    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toResponse)
                .collect(Collectors.toList());
    }
}

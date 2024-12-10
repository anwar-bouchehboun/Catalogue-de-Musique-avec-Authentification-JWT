package com.catalogue.musique.services;

import com.catalogue.musique.dto.reponse.UserResponse;
import com.catalogue.musique.dto.request.RoleRequest;
import com.catalogue.musique.dto.request.UserRequest;
import com.catalogue.musique.mapper.UserMapper;
import com.catalogue.musique.model.Role;
import com.catalogue.musique.model.User;
import com.catalogue.musique.repository.RoleRepository;
import com.catalogue.musique.repository.UserRepository;
import com.catalogue.musique.validation.NotFoundExceptionHndler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper=UserMapper.INSTANCE;

    public UserResponse creerUser(UserRequest request) {
        User user = userMapper.toEntity(request);
        
        if (request.getRoleRequests() != null && !request.getRoleRequests().isEmpty()) {
            List<Role> roles = new ArrayList<>();
            for (RoleRequest roleRequest : request.getRoleRequests()) {
                Role userRole = roleRepository.findByRolename(roleRequest.getRolename())
                        .orElseGet(() -> {
                            Role newRole = new Role();
                            newRole.setRolename(roleRequest.getRolename());
                            return roleRepository.save(newRole);
                        });
                roles.add(userRole);
            }
            user.setRoles(roles);
        }

        return userMapper.toResponse(userRepository.save(user));
    }

    public List<UserResponse> obtenirTousLesUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    public UserResponse obtenirUserParId(String id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new NotFoundExceptionHndler("Utilisateur non trouvé"));
    }

    public UserResponse mettreAJourUser(String id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionHndler("Utilisateur non trouvé"));

        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());

        if (request.getRoleRequests() != null && !request.getRoleRequests().isEmpty()) {
            List<Role> roles = new ArrayList<>();
            for (RoleRequest roleRequest : request.getRoleRequests()) {
                Role userRole = roleRepository.findByRolename(roleRequest.getRolename())
                        .orElseGet(() -> {
                            Role newRole = new Role();
                            newRole.setRolename(roleRequest.getRolename());
                            return roleRepository.save(newRole);
                        });
                roles.add(userRole);
            }
            user.setRoles(roles);
        }

        return userMapper.toResponse(userRepository.save(user));
    }

    public void supprimerUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundExceptionHndler("Utilisateur non trouvé");
        }
        userRepository.deleteById(id);
    }

    public Page<UserResponse> obtenirUsersAvecPagination(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toResponse);
    }

    public UserResponse mettreAJourRoles(String id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionHndler("Utilisateur non trouvé"));

        // Gérer les rôles ici
        if (request.getRoleRequests() != null && !request.getRoleRequests().isEmpty()) {
            List<Role> roles = new ArrayList<>();
            for (RoleRequest roleRequest : request.getRoleRequests()) {
                Role userRole = roleRepository.findByRolename(roleRequest.getRolename())
                        .orElseGet(() -> {
                            Role newRole = new Role();
                            newRole.setRolename(roleRequest.getRolename());
                            return roleRepository.save(newRole);
                        });
                roles.add(userRole);
            }
            user.setRoles(roles);
        }

        return userMapper.toResponse(userRepository.save(user));
    }
}

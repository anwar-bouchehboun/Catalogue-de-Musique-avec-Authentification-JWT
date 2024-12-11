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
import com.catalogue.musique.validation.ValidationException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService  {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper=UserMapper.INSTANCE;

    private final PasswordEncoder encoder;

    public UserResponse creerUser(UserRequest request) {
        if (userRepository.findByLogin(request.getLogin()).isPresent()) {
            throw new ValidationException("Le login " + request.getLogin() + " existe déjà");
        }
        request.setPassword(encoder.encode(request.getPassword()));
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

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
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

   /* @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userDetail = userRepository.findByLogin(username);

        // Converting UserInfo to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }*/
}

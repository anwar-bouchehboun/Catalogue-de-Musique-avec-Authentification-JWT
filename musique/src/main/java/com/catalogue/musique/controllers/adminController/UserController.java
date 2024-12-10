package com.catalogue.musique.controllers.adminController;

import com.catalogue.musique.dto.reponse.UserResponse;
import com.catalogue.musique.dto.request.UserRequest;
import com.catalogue.musique.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);



    @GetMapping
    public ResponseEntity<List<UserResponse>> obtenirTousLesUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Rôles de l'utilisateur dans le contrôleur : {}", authentication.getAuthorities());
        List<UserResponse> users = userService.obtenirTousLesUsers();
        return ResponseEntity.ok(users);
    }




    @PutMapping("/{id}/roles")
    public ResponseEntity<UserResponse> mettreAJourRoles(@Valid  @PathVariable String id, @RequestBody UserRequest request) {
        UserResponse response = userService.mettreAJourRoles(id, request);
        return ResponseEntity.ok(response);
    }


}

package com.catalogue.musique.controllers.adminController;

import com.catalogue.musique.dto.reponse.UserResponse;
import com.catalogue.musique.dto.request.UserRequest;
import com.catalogue.musique.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> creerUser(@Valid  @RequestBody UserRequest request) {
        UserResponse response = userService.creerUser(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> obtenirTousLesUsers() {
        List<UserResponse> users = userService.obtenirTousLesUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> obtenirUserParId(@PathVariable String id) {
        UserResponse response = userService.obtenirUserParId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> mettreAJourUser(@Valid  @PathVariable String id, @RequestBody UserRequest request) {
        UserResponse response = userService.mettreAJourUser(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/roles")
    public ResponseEntity<UserResponse> mettreAJourRoles(@Valid  @PathVariable String id, @RequestBody UserRequest request) {
        UserResponse response = userService.mettreAJourRoles(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerUser(@PathVariable String id) {
        userService.supprimerUser(id);
        return ResponseEntity.noContent().build();
    }
}

package com.catalogue.musique.controllers.authController;


import com.catalogue.musique.dto.reponse.UserResponse;
import com.catalogue.musique.dto.request.AuthRequest;
import com.catalogue.musique.dto.request.UserRequest;
import com.catalogue.musique.services.UserService;
import com.catalogue.musique.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final JwtUtil jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest request) {
        UserResponse response = userService.creerUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticateAndGetToken(@Valid @RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword())
        );

        if (authentication.isAuthenticated()) {
            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            log.info("Roles de l'utilisateur : {}", roles);
            String token = jwtService.generateToken(authRequest.getLogin(), roles);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            throw new ValidationException("Invalid login or password");
        }
    }
}

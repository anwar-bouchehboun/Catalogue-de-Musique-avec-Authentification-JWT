package com.catalogue.musique.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    private String id;

    @NotBlank(message = "Le login ne peut pas être vide")
    private String login;

    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    private String password;

    private Boolean active=true;

    @DBRef
    private Collection<Role> roles;

    
}

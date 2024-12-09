package com.catalogue.musique.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

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

    @NotNull(message = "L'état actif ne peut pas être nul")
    private Boolean active;

    @DBRef
    private Collection<Role> roles;
}

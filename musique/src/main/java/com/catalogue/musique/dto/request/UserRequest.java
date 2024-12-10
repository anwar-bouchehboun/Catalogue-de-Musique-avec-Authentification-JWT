package com.catalogue.musique.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank(message = "Le login ne peut pas être vide")
    private String login;

    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    private String password;

    @NotNull(message = "L'état actif ne peut pas être nul")
    private Boolean active;

    private List<String> roleIds;
}

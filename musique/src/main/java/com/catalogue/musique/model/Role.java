package com.catalogue.musique.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
@Data
@Document(collection = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

    @Id
    private String id;

    @NotBlank(message = "Le login ne peut pas être vide")
    private String username;

}

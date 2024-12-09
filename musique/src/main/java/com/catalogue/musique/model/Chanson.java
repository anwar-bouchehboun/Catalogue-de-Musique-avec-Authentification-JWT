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

@Data
@Document(collection = "chansons")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Chanson {
    @Id
    private String id;

    @NotBlank(message = "Le titre ne peut pas être vide")
    private String titre;

    @NotNull(message = "La durée ne peut pas être nulle")
    private Integer duree;

    @NotNull(message = "Le numéro de piste ne peut pas être nul")
    private Integer trackNumber;

    @DBRef
    private Album album;
}

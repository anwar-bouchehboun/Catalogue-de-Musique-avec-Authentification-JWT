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
import java.util.List;

@Data
@Document(collection = "albums")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Album {
    @Id
    private String id;

    @NotBlank(message = "Le titre ne peut pas être vide")
    private String titre;

    @NotBlank(message = "L'artiste ne peut pas être vide")
    private String artiste;

    @NotNull(message = "L'année ne peut pas être nulle")
    private Integer annee;

    @DBRef
    private List<Chanson> chansons;
}

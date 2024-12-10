package com.catalogue.musique.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumRequest {
    @NotBlank(message = "Le titre ne peut pas être vide")
    private String titre;
    
    @NotBlank(message = "L'artiste ne peut pas être vide")
    private String artiste;
    
    @NotNull(message = "L'année ne peut pas être nulle")
    @Min(value = 1900, message = "L'année doit être supérieure à 1900")
    private Integer annee;
}

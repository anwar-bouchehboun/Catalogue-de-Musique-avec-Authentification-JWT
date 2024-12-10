package com.catalogue.musique.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChansonRequest {
    @NotBlank(message = "Le titre ne peut pas être vide")
    private String titre;

    @NotNull(message = "La durée ne peut pas être nulle")
    @Min(value = 1, message = "La durée doit être supérieure à 0")
    private Integer duree;

    @NotNull(message = "Le numéro de piste ne peut pas être nul")
    @Min(value = 1, message = "Le numéro de piste doit être supérieur à 0")
    private Integer trackNumber;

    @NotBlank(message = "L'ID de l'album ne peut pas être vide")
    private String albumId;
}

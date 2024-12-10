package com.catalogue.musique.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChansonResponce {
    private String id;
    private String titre;
    private Integer duree;
    private Integer trackNumber;
}

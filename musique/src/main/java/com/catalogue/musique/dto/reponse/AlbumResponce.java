package com.catalogue.musique.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumResponce {
    private String id;
    private String titre;
    private String artiste;
    private Integer annee;
    private List<ChansonResponce> chansons;
}

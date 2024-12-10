package com.catalogue.musique.controllers.userController;

import com.catalogue.musique.dto.reponse.ChansonResponce;
import com.catalogue.musique.services.ChansonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/user/chansons")
@RequiredArgsConstructor
public class ChansonControllerUser {
    private final ChansonService chansonService;

    @GetMapping
    public ResponseEntity<List<ChansonResponce>> obtenirToutesLesChansons() {
        return ResponseEntity.ok(chansonService.obtenirToutesLesChansons());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ChansonResponce>> obtenirChansonsAvecPagination(
            Pageable pageable
    ) {
        return ResponseEntity.ok(chansonService.obtenirChansonsAvecPagination(pageable));
    }

    @GetMapping("/recherche")
    public ResponseEntity<Page<ChansonResponce>> rechercherChansonsParTitre(
            @RequestParam String titre,
            Pageable pageable) {

        return ResponseEntity.ok(chansonService.rechercherChansonsParTitre(titre, pageable));
    }

    @GetMapping("/album")
    public ResponseEntity<Page<ChansonResponce>> obtenirChansonsParNomAlbum(
            @RequestParam String nomAlbum,
            Pageable pageable) {
        return ResponseEntity.ok(chansonService.obtenirChansonsParNomAlbum(nomAlbum, pageable));
    }
}

package com.catalogue.musique.controllers.adminController;

import com.catalogue.musique.dto.reponse.ChansonResponce;
import com.catalogue.musique.dto.request.ChansonRequest;
import com.catalogue.musique.services.ChansonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/chansons")
@RequiredArgsConstructor
public class ChansonController {
    private final ChansonService chansonService;

    @PostMapping
    public ResponseEntity<ChansonResponce> creerChanson(@Valid @RequestBody ChansonRequest request) {
        return new ResponseEntity<>(chansonService.creerChanson(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ChansonResponce>> obtenirToutesLesChansons() {
        return ResponseEntity.ok(chansonService.obtenirToutesLesChansons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChansonResponce> obtenirChansonParId(@PathVariable String id) {
        return ResponseEntity.ok(chansonService.obtenirChansonParId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChansonResponce> mettreAJourChanson(
            @PathVariable String id,
            @Valid @RequestBody ChansonRequest request) {
        return ResponseEntity.ok(chansonService.mettreAJourChanson(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerChanson(@PathVariable String id) {
        chansonService.supprimerChanson(id);
        return ResponseEntity.noContent().build();
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

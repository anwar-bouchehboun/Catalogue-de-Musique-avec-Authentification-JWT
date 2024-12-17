package com.catalogue.musique.controllers.userController;

import com.catalogue.musique.dto.reponse.AlbumResponce;
import com.catalogue.musique.services.AlbumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.catalogue.musique.validation.UnauthorizedException;

@RestController
@RequestMapping("/api/user/albums")
@RequiredArgsConstructor
@Slf4j
public class AlbumControllerUser {

    private final AlbumService albumService;

    @GetMapping
    public ResponseEntity<List<AlbumResponce>> getAllAlbums() {
        log.info("Récupération de tous les albums");
        List<AlbumResponce> albums = albumService.getAllAlbums();
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<AlbumResponce>> getAllAlbums(HttpServletRequest request) {
        log.info("Récupération de tous les albums pour l'utilisateur");
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String token = authHeader.substring(7);
        List<AlbumResponce> albums = albumService.getAllAlbumsForUser(token);
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }
    throw new UnauthorizedException("Token d'authentification manquant");
}

    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponce> getAlbumById(@PathVariable String id) {
        log.info("Récupération de l'album avec l'ID : {}", id);
        AlbumResponce album = albumService.getAlbumById(id);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<AlbumResponce>> getAllPagination(Pageable pageable) {
        log.info("Récupération des albums avec pagination");
        Page<AlbumResponce> albums = albumService.getAllPagination(pageable);
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @GetMapping("/recherche/titre")
    public ResponseEntity<Page<AlbumResponce>> rechercherParTitre(
            @RequestParam(required = false) String titre,
            Pageable pageable) {
        log.info("Recherche d'albums par titre : {}", titre);
        Page<AlbumResponce> albums = albumService.lister(titre, pageable);
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @GetMapping("/recherche/artiste")
    public ResponseEntity<Page<AlbumResponce>> rechercherParArtiste(
            @RequestParam(required = false) String artiste,
            Pageable pageable) {
        log.info("Recherche d'albums par artiste : {}", artiste);
        Page<AlbumResponce> albums = albumService.findByArtiste(artiste, pageable);
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @GetMapping("/recherche/annee")
    public ResponseEntity<Page<AlbumResponce>> rechercherParAnnee(
            @RequestParam(required = false) Integer annee,
            Pageable pageable) {
        log.info("Recherche d'albums par année : {}", annee);
        Page<AlbumResponce> albums = albumService.findByAnnee(annee, pageable);
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }
}

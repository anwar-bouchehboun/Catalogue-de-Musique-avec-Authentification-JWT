package com.catalogue.musique.controllers.adminController;



import com.catalogue.musique.dto.request.AlbumRequest;
import com.catalogue.musique.dto.reponse.AlbumResponce;
import com.catalogue.musique.services.AlbumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/albums")
@RequiredArgsConstructor
@Slf4j
public class AlbumController {

    private final AlbumService albumService;

    @PostMapping
    public ResponseEntity<AlbumResponce> createAlbum(@Valid @RequestBody AlbumRequest albumRequest) {
        log.info("Création d'un nouvel album : {}", albumRequest.getTitre());
        AlbumResponce createdAlbum = albumService.createAlbum(albumRequest);
        return new ResponseEntity<>(createdAlbum, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AlbumResponce>> getAllAlbums() {
        log.info("Récupération de tous les albums");
        List<AlbumResponce> albums = albumService.getAllAlbums();
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponce> getAlbumById(@PathVariable String id) {
        log.info("Récupération de l'album avec l'ID : {}", id);
        AlbumResponce album = albumService.getAlbumById(id);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumResponce> updateAlbum(@PathVariable String id,
                                                   @Valid @RequestBody AlbumRequest albumRequest) {
        log.info("Mise à jour de l'album avec l'ID : {}", id);
        AlbumResponce updatedAlbum = albumService.updateAlbum(id, albumRequest);
        return new ResponseEntity<>(updatedAlbum, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable String id) {
        log.info("Suppression de l'album avec l'ID : {}", id);
        albumService.deleteAlbum(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

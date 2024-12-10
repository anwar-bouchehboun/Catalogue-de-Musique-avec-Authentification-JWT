package com.catalogue.musique.repository;

import com.catalogue.musique.model.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends MongoRepository<Album, String> {
    Page<Album> findByTitre(String titre, Pageable pageable);
    Page<Album> findByArtiste(String artiste, Pageable pageable);
    Page<Album> findByAnnee(Integer annee, Pageable pageable);


}

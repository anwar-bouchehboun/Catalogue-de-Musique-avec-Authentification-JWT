package com.catalogue.musique.repository;

import com.catalogue.musique.model.Chanson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChansonRepository extends MongoRepository<Chanson, String> {
    Page<Chanson> findByTitreContainingIgnoreCase(String titre, Pageable pageable);
    Page<Chanson> findByAlbumId(String albumId, Pageable pageable);
}

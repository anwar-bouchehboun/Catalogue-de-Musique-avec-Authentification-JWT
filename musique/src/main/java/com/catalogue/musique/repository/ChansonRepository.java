package com.catalogue.musique.repository;

import com.catalogue.musique.model.Chanson;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChansonRepository extends MongoRepository<Chanson, String> {
}

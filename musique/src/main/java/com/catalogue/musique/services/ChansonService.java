package com.catalogue.musique.services;

import com.catalogue.musique.dto.reponse.ChansonResponce;
import com.catalogue.musique.dto.request.ChansonRequest;
import com.catalogue.musique.mapper.ChansonMapper;
import com.catalogue.musique.model.Album;
import com.catalogue.musique.model.Chanson;
import com.catalogue.musique.repository.AlbumRepository;
import com.catalogue.musique.repository.ChansonRepository;
import com.catalogue.musique.validation.NotFoundExceptionHndler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChansonService {
    private final ChansonRepository chansonRepository;
    private final AlbumRepository albumRepository;
    private final ChansonMapper chansonMapper;

    public ChansonResponce creerChanson(ChansonRequest request) {
        Album album = albumRepository.findById(request.getAlbumId())
                .orElseThrow(() -> new NotFoundExceptionHndler("Album non trouvé"));

        Chanson chanson = chansonMapper.toEntity(request);
        chanson.setAlbum(album);


        return chansonMapper.toResponse(chansonRepository.save(chanson));
    }

    public List<ChansonResponce> obtenirToutesLesChansons() {
        return chansonRepository.findAll().stream()
                .map(chansonMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ChansonResponce obtenirChansonParId(String id) {
        return chansonRepository.findById(id)
                .map(chansonMapper::toResponse)
                .orElseThrow(() -> new NotFoundExceptionHndler("Chanson non trouvée"));
    }

    public ChansonResponce mettreAJourChanson(String id, ChansonRequest request) {
        Chanson chanson = chansonRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionHndler("Chanson non trouvée"));

        Album album = albumRepository.findById(request.getAlbumId())
                .orElseThrow(() -> new NotFoundExceptionHndler("Album non trouvé"));

        chanson.setTitre(request.getTitre());
        chanson.setDuree(request.getDuree());
        chanson.setTrackNumber(request.getTrackNumber());
        chanson.setAlbum(album);

        return chansonMapper.toResponse(chansonRepository.save(chanson));
    }

    public void supprimerChanson(String id) {
        if (!chansonRepository.existsById(id)) {
            throw new NotFoundExceptionHndler("Chanson non trouvée");
        }
        chansonRepository.deleteById(id);
    }

    public Page<ChansonResponce> obtenirChansonsAvecPagination(Pageable pageable) {
        Page<Chanson> chansonsPage = chansonRepository.findAll(pageable);
        return chansonsPage.map(chansonMapper::toResponse);
    }

    public Page<ChansonResponce> rechercherChansonsParTitre(String titre, Pageable pageable) {
        Page<Chanson> chansonsPage = chansonRepository.findByTitreContainingIgnoreCase(titre, pageable);
        return chansonsPage.map(chansonMapper::toResponse);
    }

    public Page<ChansonResponce> obtenirChansonsParNomAlbum(String nomAlbum, Pageable pageable) {
        Album album = albumRepository.findByTitreContainingIgnoreCase(nomAlbum)
                .orElseThrow(() -> new NotFoundExceptionHndler("Album non trouvé"));

        Page<Chanson> chansonsPage = chansonRepository.findByAlbumId(album.getId(), pageable);
        return chansonsPage.map(chansonMapper::toResponse);
    }
}

package com.catalogue.musique.services;

import com.catalogue.musique.dto.request.AlbumRequest;
import com.catalogue.musique.dto.reponse.AlbumResponce;
import com.catalogue.musique.mapper.AlbumMapper;
import com.catalogue.musique.model.Album;
import com.catalogue.musique.repository.AlbumRepository;
import com.catalogue.musique.validation.NotFoundExceptionHndler;
import com.catalogue.musique.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumService {
    
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper=AlbumMapper.INSTANCE;

    public AlbumResponce createAlbum(AlbumRequest albumRequest) {
        Album album = albumMapper.toEntity(albumRequest);
        Album savedAlbum = albumRepository.save(album);
        return albumMapper.toResponse(savedAlbum);
    }

    public List<AlbumResponce> getAllAlbums() {
        return albumRepository.findAll().stream()
                .map(albumMapper::toResponse)
                .collect(Collectors.toList());
    }

    public AlbumResponce getAlbumById(String id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Album non trouvé avec l'ID : " + id));
        return albumMapper.toResponse(album);
    }

    public AlbumResponce updateAlbum(String id, AlbumRequest albumRequest) {
        Album existingAlbum = albumRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionHndler("Album non trouvé avec l'ID : " + id));

        Album albumToUpdate = albumMapper.toEntity(albumRequest);
        albumToUpdate.setId(id);
        albumToUpdate.setChansons(existingAlbum.getChansons());

        Album updatedAlbum = albumRepository.save(albumToUpdate);
        return albumMapper.toResponse(updatedAlbum);
    }

    public void deleteAlbum(String id) {
        if (!albumRepository.existsById(id)) {
            throw new NotFoundExceptionHndler("Album non trouvé avec l'ID : " + id);
        }
        albumRepository.deleteById(id);
    }


    public Page<AlbumResponce> getAllPagination(Pageable pageable) {
        return albumRepository.findAll(pageable)
                .map(albumMapper::toResponse);
    }

    @Transactional
    public Page<AlbumResponce> lister(String titre, Pageable pageable) {
        if (titre != null && !titre.isEmpty()) {
            return albumRepository.findByTitre(titre, pageable)
                    .map(albumMapper::toResponse);
        }
        return albumRepository.findAll(pageable)
                .map(albumMapper::toResponse);
    }

    @Transactional
    public Page<AlbumResponce> findByArtiste(String artiste, Pageable pageable) {
        if (artiste != null && !artiste.isEmpty()) {
            return albumRepository.findByArtiste(artiste, pageable)
                    .map(albumMapper::toResponse);
        }
        return albumRepository.findAll(pageable)
                .map(albumMapper::toResponse);
    }

    @Transactional
    public Page<AlbumResponce> findByAnnee(Integer annee, Pageable pageable) {
        if (annee != null) {
            return albumRepository.findByAnnee(annee, pageable)
                    .map(albumMapper::toResponse);
        }
        return albumRepository.findAll(pageable)
                .map(albumMapper::toResponse);
    }

}

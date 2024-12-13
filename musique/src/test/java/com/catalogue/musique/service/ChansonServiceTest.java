package com.catalogue.musique.service;

import com.catalogue.musique.dto.reponse.ChansonResponce;
import com.catalogue.musique.dto.request.ChansonRequest;
import com.catalogue.musique.mapper.ChansonMapper;
import com.catalogue.musique.model.Album;
import com.catalogue.musique.model.Chanson;
import com.catalogue.musique.repository.AlbumRepository;
import com.catalogue.musique.repository.ChansonRepository;
import com.catalogue.musique.services.ChansonService;
import com.catalogue.musique.validation.NotFoundExceptionHndler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChansonServiceTest {

    @Mock
    private ChansonRepository chansonRepository;

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private ChansonMapper chansonMapper;

    @InjectMocks
    private ChansonService chansonService;

    private Album album;
    private Chanson chanson;
    private ChansonRequest chansonRequest;
    private ChansonResponce chansonResponse;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        album = Album.builder()
                .id("1")
                .titre("Album Test")
                .build();

        chanson = Chanson.builder()
                .id("1")
                .titre("Chanson Test")
                .duree(180)
                .trackNumber(1)
                .album(album)
                .build();

        chansonRequest = ChansonRequest.builder()
                .titre("Chanson Test")
                .duree(180)
                .trackNumber(1)
                .albumId("1")
                .build();

        chansonResponse = ChansonResponce.builder()
                .id("1")
                .titre("Chanson Test")
                .duree(180)
                .trackNumber(1)
                .build();

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void creerChanson_DevraitCreerEtRetournerChanson() {
        when(albumRepository.findById("1")).thenReturn(Optional.of(album));
        when(chansonMapper.toEntity(chansonRequest)).thenReturn(chanson);
        when(chansonRepository.save(any(Chanson.class))).thenReturn(chanson);
        when(chansonMapper.toResponse(chanson)).thenReturn(chansonResponse);

        ChansonResponce result = chansonService.creerChanson(chansonRequest);

        assertNotNull(result);
        assertEquals(chansonResponse.getTitre(), result.getTitre());
        verify(chansonRepository).save(any(Chanson.class));
    }

    @Test
    void creerChanson_DevraitLeverException_QuandAlbumNExistePas() {
        when(albumRepository.findById("999")).thenReturn(Optional.empty());
        chansonRequest.setAlbumId("999");

        assertThrows(NotFoundExceptionHndler.class, () -> chansonService.creerChanson(chansonRequest));
    }

    @Test
    void obtenirToutesLesChansons_DevraitRetournerListeChansons() {
        when(chansonRepository.findAll()).thenReturn(Arrays.asList(chanson));
        when(chansonMapper.toResponse(chanson)).thenReturn(chansonResponse);

        List<ChansonResponce> result = chansonService.obtenirToutesLesChansons();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void obtenirChansonParId_DevraitRetournerChanson_QuandIdExiste() {
        when(chansonRepository.findById("1")).thenReturn(Optional.of(chanson));
        when(chansonMapper.toResponse(chanson)).thenReturn(chansonResponse);

        ChansonResponce result = chansonService.obtenirChansonParId("1");

        assertNotNull(result);
        assertEquals(chansonResponse.getTitre(), result.getTitre());
    }

    @Test
    void obtenirChansonParId_DevraitLeverException_QuandIdNExistePas() {
        when(chansonRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(NotFoundExceptionHndler.class, () -> chansonService.obtenirChansonParId("999"));
    }

    @Test
    void mettreAJourChanson_DevraitMettreAJourEtRetournerChanson() {
        when(chansonRepository.findById("1")).thenReturn(Optional.of(chanson));
        when(albumRepository.findById("1")).thenReturn(Optional.of(album));
        when(chansonRepository.save(any(Chanson.class))).thenReturn(chanson);
        when(chansonMapper.toResponse(chanson)).thenReturn(chansonResponse);

        ChansonResponce result = chansonService.mettreAJourChanson("1", chansonRequest);

        assertNotNull(result);
        assertEquals(chansonResponse.getTitre(), result.getTitre());
        verify(chansonRepository).save(any(Chanson.class));
    }

    @Test
    void supprimerChanson_DevraitSupprimerChanson() {
        when(chansonRepository.existsById("1")).thenReturn(true);

        assertDoesNotThrow(() -> chansonService.supprimerChanson("1"));
        verify(chansonRepository).deleteById("1");
    }

    @Test
    void supprimerChanson_DevraitLeverException_QuandIdNExistePas() {
        when(chansonRepository.existsById("999")).thenReturn(false);

        assertThrows(NotFoundExceptionHndler.class, () -> chansonService.supprimerChanson("999"));
    }


}

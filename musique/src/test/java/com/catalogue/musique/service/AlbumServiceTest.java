package com.catalogue.musique.service;

import com.catalogue.musique.dto.request.AlbumRequest;
import com.catalogue.musique.dto.reponse.AlbumResponce;
import com.catalogue.musique.model.Album;
import com.catalogue.musique.repository.AlbumRepository;
import com.catalogue.musique.services.AlbumService;
import com.catalogue.musique.validation.NotFoundExceptionHndler;
import com.catalogue.musique.validation.ValidationException;
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
class AlbumServiceTest {

    @Mock
    private AlbumRepository albumRepository;

    @InjectMocks
    private AlbumService albumService;

    private Album album;
    private AlbumRequest albumRequest;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        album = Album.builder()
                .id("1")
                .titre("Test Album")
                .artiste("Test Artiste")
                .annee(2024)
                .chansons(Arrays.asList())
                .build();

        albumRequest = AlbumRequest.builder()
                .titre("Test Album")
                .artiste("Test Artiste")
                .annee(2024)
                .build();

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void createAlbum_DevraitCreerEtRetournerAlbum() {
        when(albumRepository.save(any(Album.class))).thenReturn(album);

        AlbumResponce result = albumService.createAlbum(albumRequest);

        assertNotNull(result);
        assertEquals(album.getTitre(), result.getTitre());
        assertEquals(album.getArtiste(), result.getArtiste());
        assertEquals(album.getAnnee(), result.getAnnee());
        verify(albumRepository).save(any(Album.class));
    }

    @Test
    void getAllAlbums_DevraitRetournerListeAlbums() {
        when(albumRepository.findAll()).thenReturn(Arrays.asList(album));

        List<AlbumResponce> result = albumService.getAllAlbums();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(album.getTitre(), result.get(0).getTitre());
    }

    @Test
    void getAlbumById_DevraitRetournerAlbum_QuandIdExiste() {
        when(albumRepository.findById("1")).thenReturn(Optional.of(album));

        AlbumResponce result = albumService.getAlbumById("1");

        assertNotNull(result);
        assertEquals(album.getId(), "1");
        assertEquals(album.getTitre(), result.getTitre());
    }

    @Test
    void getAlbumById_DevraitLeverException_QuandIdNExistePas() {
        when(albumRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(ValidationException.class, () -> albumService.getAlbumById("999"));
    }

    @Test
    void updateAlbum_DevraitMettreAJourAlbum() {
        when(albumRepository.findById("1")).thenReturn(Optional.of(album));
        when(albumRepository.save(any(Album.class))).thenReturn(album);

        AlbumResponce result = albumService.updateAlbum("1", albumRequest);

        assertNotNull(result);
        assertEquals(album.getTitre(), result.getTitre());
        verify(albumRepository).save(any(Album.class));
    }

    @Test
    void updateAlbum_DevraitLeverException_QuandIdNExistePas() {
        when(albumRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(NotFoundExceptionHndler.class, () -> 
            albumService.updateAlbum("999", albumRequest));
    }

    @Test
    void deleteAlbum_DevraitSupprimerAlbum() {
        when(albumRepository.existsById("1")).thenReturn(true);

        assertDoesNotThrow(() -> albumService.deleteAlbum("1"));
        verify(albumRepository).deleteById("1");
    }

    @Test
    void deleteAlbum_DevraitLeverException_QuandIdNExistePas() {
        when(albumRepository.existsById("999")).thenReturn(false);

        assertThrows(NotFoundExceptionHndler.class, () -> albumService.deleteAlbum("999"));
    }


}
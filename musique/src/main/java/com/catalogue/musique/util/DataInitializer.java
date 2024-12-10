package com.catalogue.musique.util;

import com.catalogue.musique.model.Album;
import com.catalogue.musique.model.Chanson;
import com.catalogue.musique.model.Role;
import com.catalogue.musique.model.User;
import com.catalogue.musique.repository.AlbumRepository;
import com.catalogue.musique.repository.ChansonRepository;
import com.catalogue.musique.repository.RoleRepository;
import com.catalogue.musique.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
@Profile("dev")
public class DataInitializer {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;
    private final ChansonRepository chansonRepository;

    @Bean
    CommandLineRunner init() {
        return args -> {
            log.info("Début de la réinitialisation des données...");
            
            // Suppression de toutes les données existantes
            log.info("Suppression des données existantes...");
            chansonRepository.deleteAll();
            albumRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            
            log.info("Toutes les données ont été supprimées");

            // Initialisation des rôles et utilisateurs
            log.info("Création des nouveaux rôles...");
            Role roleAdmin = Role.builder()
                    .rolename("ROLE_ADMIN")
                    .build();
            Role roleUser = Role.builder()
                    .rolename("ROLE_USER")
                    .build();
            
            roleRepository.save(roleAdmin);
            roleRepository.save(roleUser);

            User admin = User.builder()
                    .login("admin")
                    .password("admin123")
                    .active(true)
                    .roles(Arrays.asList(roleAdmin, roleUser))
                    .build();
            userRepository.save(admin);

            // Initialisation des albums et chansons
            log.info("Création des nouveaux albums...");
            Album album1 = Album.builder()
                    .titre("Thriller")
                    .artiste("Michael Jackson")
                    .annee(1982)
                    .build();
            albumRepository.save(album1);

            List<Chanson> chansonsAlbum1 = Arrays.asList(
                Chanson.builder()
                    .titre("Thriller")
                    .duree(357)
                    .trackNumber(1)
                    .album(album1)
                    .build(),
                Chanson.builder()
                    .titre("Beat It")
                    .duree(258)
                    .trackNumber(2)
                    .album(album1)
                    .build(),
                Chanson.builder()
                    .titre("Billie Jean")
                    .duree(294)
                    .trackNumber(3)
                    .album(album1)
                    .build()
            );
            chansonRepository.saveAll(chansonsAlbum1);
            
            album1.setChansons(chansonsAlbum1);
            albumRepository.save(album1);

            Album album2 = Album.builder()
                    .titre("Back in Black")
                    .artiste("AC/DC")
                    .annee(1980)
                    .build();
            albumRepository.save(album2);

            List<Chanson> chansonsAlbum2 = Arrays.asList(
                Chanson.builder()
                    .titre("Back in Black")
                    .duree(255)
                    .trackNumber(1)
                    .album(album2)
                    .build(),
                Chanson.builder()
                    .titre("Hells Bells")
                    .duree(312)
                    .trackNumber(2)
                    .album(album2)
                    .build()
            );
            chansonRepository.saveAll(chansonsAlbum2);
            
            album2.setChansons(chansonsAlbum2);
            albumRepository.save(album2);

            // Affichage du résumé
            log.info("=== Résumé de la réinitialisation ===");
            log.info("Nombre de rôles créés : {}", roleRepository.count());
            log.info("Nombre d'utilisateurs créés : {}", userRepository.count());
            log.info("Nombre d'albums créés : {}", albumRepository.findAll().size());
            log.info("Nombre de chansons créées : {}", chansonRepository.findAll().size());
            log.info("Réinitialisation terminée avec succès!");
        };
    }
}

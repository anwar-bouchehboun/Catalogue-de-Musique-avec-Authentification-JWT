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
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class DataInitializer {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;
    private final ChansonRepository chansonRepository;
    private final Environment environment;

    @Bean
    CommandLineRunner init() {
        return args -> {
            log.info("Début de l'initialisation des données...");
            log.info("Profil actif : {}", Arrays.toString(environment.getActiveProfiles()));

            try {
                // Initialisation des rôles et utilisateurs
                log.info("Création des rôles...");
                Role roleAdmin = Role.builder()
                        .username("ROLE_ADMIN")
                        .build();
                Role roleUser = Role.builder()
                        .username("ROLE_USER")
                        .build();
                
                roleRepository.save(roleAdmin);
                roleRepository.save(roleUser);
                log.info("Rôles créés avec succès : ROLE_ADMIN, ROLE_USER");

                log.info("Création de l'utilisateur admin...");
                User admin = User.builder()
                        .login("admin")
                        .password("admin123")
                        .active(true)
                        .roles(Arrays.asList(roleAdmin, roleUser))
                        .build();
                userRepository.save(admin);
                log.info("Utilisateur admin créé avec succès");

                // Initialisation des albums et chansons
                log.info("Création des albums...");
                Album album1 = Album.builder()
                        .titre("Thriller")
                        .artiste("Michael Jackson")
                        .annee(1982)
                        .build();
                albumRepository.save(album1);

                log.info("Création des chansons pour l'album Thriller...");
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
                log.info("Album Thriller et ses chansons créés avec succès");

                log.info("Création du second album...");
                Album album2 = Album.builder()
                        .titre("Back in Black")
                        .artiste("AC/DC")
                        .annee(1980)
                        .build();
                albumRepository.save(album2);

                log.info("Création des chansons pour l'album Back in Black...");
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
                log.info("Album Back in Black et ses chansons créés avec succès");

                // Résumé final
                log.info("=== Résumé de l'initialisation ===");
                log.info("Nombre de rôles créés : {}", roleRepository.count());
                log.info("Nombre d'utilisateurs créés : {}", userRepository.count());
                log.info("Nombre d'albums créés : {}", albumRepository.findAll().size());
                log.info("Nombre de chansons créées : {}", chansonRepository.findAll().size());
                log.info("Initialisation terminée avec succès!");

            } catch (Exception e) {
                log.error("Erreur lors de l'initialisation des données", e);
                throw e;
            }
        };
    }
}

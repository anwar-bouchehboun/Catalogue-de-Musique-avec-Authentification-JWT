package com.catalogue.musique.services;

import com.catalogue.musique.security.TokenBlacklist;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenBlacklistService {

    private final TokenBlacklist tokenBlacklist;
    private static final int MAX_SIZE = 2;

    public void addToBlacklist(String token) {
        log.info("Ajout du token à la blacklist");
        tokenBlacklist.addToBlacklist(token,MAX_SIZE);
    }

    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklist.isBlacklisted(token);
    }

}

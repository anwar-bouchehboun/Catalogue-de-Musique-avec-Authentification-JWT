package com.catalogue.musique.services;

import com.catalogue.musique.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoDetails implements UserDetails {

    private String username; // Utilisé pour stocker le nom d'utilisateur
    private String password;  // Utilisé pour stocker le mot de passe
    private List<GrantedAuthority> authorities;

    public UserInfoDetails(User userInfo) {
        this.username = userInfo.getLogin(); // Utiliser login au lieu de name
        this.password = userInfo.getPassword();
        this.authorities = userInfo.getRoles().stream() // Utilisation de la méthode getRoles()
                .map(role -> new SimpleGrantedAuthority(role.getRolename())) // Assurez-vous que Role a getRolename()
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password; // Retourne le mot de passe
    }

    @Override
    public String getUsername() {
        return username; // Retourne le nom d'utilisateur
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Indique que le compte n'est pas expiré
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Indique que le compte n'est pas verrouillé
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Indique que les informations d'identification ne sont pas expirées
    }

    @Override
    public boolean isEnabled() {
        return true; // Indique que le compte est activé
    }
}

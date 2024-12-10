package com.catalogue.musique.dto.request;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {


    private String login;
    private String password;
}

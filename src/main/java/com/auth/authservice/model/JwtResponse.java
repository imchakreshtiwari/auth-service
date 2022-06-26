package com.auth.authservice.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class JwtResponse {
    String token;
}

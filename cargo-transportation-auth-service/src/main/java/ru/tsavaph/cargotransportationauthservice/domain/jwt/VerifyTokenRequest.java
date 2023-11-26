package ru.tsavaph.cargotransportationauthservice.domain.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerifyTokenRequest {

    private String token;
    private String login;

}

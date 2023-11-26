package ru.tsavaph.cargotransportationjwtservice.service;

import lombok.RequiredArgsConstructor;
import ru.tsavaph.cargotransportationjwtservice.domain.*;

@RequiredArgsConstructor
public class JwtService {

    private final TokenService tokenService;

    public GenerateTokenResponse generateToken(GenerateTokenRequest request) {
        var token = tokenService.generateToken(request.getLogin());
        return GenerateTokenResponse.builder()
                .token(token)
                .build();
    }

    public VerifyTokenResponse verifyToken(VerifyTokenRequest request) {
        var isValid = tokenService.isTokenValid(request.getToken(), request.getLogin());
        return VerifyTokenResponse.builder()
                .isTokenValid(isValid)
                .build();
    }

    public ExtractLoginResponse extractLogin(ExtractLoginRequest request) {
        var login = tokenService.extractLogin(request.getToken());
        return ExtractLoginResponse.builder()
                .login(login)
                .build();
    }

}

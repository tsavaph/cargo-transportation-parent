package ru.tsavaph.cargotransportationauthservice.service;

import lombok.RequiredArgsConstructor;
import ru.tsavaph.cargotransportationauthservice.domain.jwt.ExtractLoginRequest;
import ru.tsavaph.cargotransportationauthservice.domain.jwt.GenerateTokenRequest;
import ru.tsavaph.cargotransportationauthservice.domain.jwt.VerifyTokenRequest;
import ru.tsavaph.cargotransportationauthservice.domain.user.User;
import ru.tsavaph.cargotransportationauthservice.feign.JwtService;

@RequiredArgsConstructor
public class TokenService {

    private final JwtService jwtService;

    public String generateToken(User user) {
        var generateTokenRequest = new GenerateTokenRequest(user.getPhoneNumber());
        var response = jwtService.generate(generateTokenRequest);
        return response.getToken();
    }

    public String extractLogin(String jwt) {
        var extractLoginRequest = new ExtractLoginRequest(jwt);
        var response = jwtService.extractLogin(extractLoginRequest);
        return response.getLogin();
    }

    public Boolean isTokenValid(String jwt, User user) {
        var verifyTokenRequest = new VerifyTokenRequest(jwt, user.getPhoneNumber());
        var response = jwtService.verify(verifyTokenRequest);
        return response.getIsTokenValid();
    }
}

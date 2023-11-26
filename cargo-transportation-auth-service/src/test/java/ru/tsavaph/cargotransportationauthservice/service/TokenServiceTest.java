package ru.tsavaph.cargotransportationauthservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.tsavaph.cargotransportationauthservice.domain.jwt.*;
import ru.tsavaph.cargotransportationauthservice.domain.user.User;
import ru.tsavaph.cargotransportationauthservice.feign.JwtService;
import ru.tsavaph.cargotransportationauthservice.repository.UserRepository;
import ru.tsavaph.cargotransportationauthservice.service.authentication.AuthenticationService;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceTest {

    private JwtService jwtServiceMock;
    private TokenService tokenService;

    @BeforeEach
    void init() {
        jwtServiceMock = Mockito.mock(JwtService.class);
        tokenService = new TokenService(jwtServiceMock);
    }

    @Test
    void generateToken() {
        var phoneNumber = "+71234567890";
        var user = User.builder().phoneNumber(phoneNumber).build();
        var token = "token";
        var generateTokenRequest = new GenerateTokenRequest(phoneNumber);
        Mockito.when(jwtServiceMock.generate(generateTokenRequest))
                .thenReturn(new GenerateTokenResponse(token));
        var result = tokenService.generateToken(user);
        Assertions.assertEquals(token, result);
        Mockito.verify(jwtServiceMock).generate(generateTokenRequest);
    }

    @Test
    void extractLogin() {
        var token = "token";
        var login = "+71234567890";
        var extractLoginRequest = new ExtractLoginRequest(token);
        Mockito.when(jwtServiceMock.extractLogin(extractLoginRequest))
                .thenReturn(new ExtractLoginResponse(login));
        var result = tokenService.extractLogin(token);
        Assertions.assertEquals(login, result);
        Mockito.verify(jwtServiceMock).extractLogin(extractLoginRequest);
    }

    @Test
    void isTokenValid() {
        var phoneNumber = "+71234567890";
        var user = User.builder().phoneNumber(phoneNumber).build();
        var token = "token";
        var verifyTokenRequest = new VerifyTokenRequest(token, phoneNumber);
        Mockito.when(jwtServiceMock.verify(verifyTokenRequest))
                .thenReturn(new VerifyTokenResponse(true));
        var result = tokenService.isTokenValid(token, user);
        Assertions.assertEquals(true, result);
        Mockito.verify(jwtServiceMock).verify(verifyTokenRequest);
    }

}
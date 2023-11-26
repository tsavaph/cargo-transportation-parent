package ru.tsavaph.cargotransportationjwtservice.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.tsavaph.cargotransportationjwtservice.domain.*;
import ru.tsavaph.cargotransportationjwtservice.service.JwtService;

class JwtControllerTest {
    private JwtController controller;
    private JwtService jwtServiceMock;

    @BeforeEach
    void init() {
        jwtServiceMock = Mockito.mock(JwtService.class);
        controller = new JwtController(jwtServiceMock);
    }

    @Test
    void generate() {
        var generateTokenRequest = new GenerateTokenRequest("+71234567890");
        var generateTokenResponse = new GenerateTokenResponse("test_token");
        Mockito.when(jwtServiceMock.generateToken(generateTokenRequest)).thenReturn(generateTokenResponse);
        var result = controller.generate(generateTokenRequest);
        Assertions.assertEquals(generateTokenResponse, result.getBody());
        Mockito.verify(jwtServiceMock).generateToken(generateTokenRequest);
    }

    @Test
    void verify() {
        var verifyTokenRequest = new VerifyTokenRequest("token","+71234567890");
        var verifyTokenResponse = new VerifyTokenResponse(true);
        Mockito.when(jwtServiceMock.verifyToken(verifyTokenRequest)).thenReturn(verifyTokenResponse);
        var result = controller.verify(verifyTokenRequest);
        Assertions.assertEquals(verifyTokenResponse, result.getBody());
        Mockito.verify(jwtServiceMock).verifyToken(verifyTokenRequest);
    }

    @Test
    void extractLogin() {
        var extractLoginRequest = new ExtractLoginRequest("token");
        var extractLoginResponse = new ExtractLoginResponse("+71234567890");
        Mockito.when(jwtServiceMock.extractLogin(extractLoginRequest)).thenReturn(extractLoginResponse);
        var result = controller.extractLogin(extractLoginRequest);
        Assertions.assertEquals(extractLoginResponse, result.getBody());
        Mockito.verify(jwtServiceMock).extractLogin(extractLoginRequest);
    }

}
package ru.tsavaph.cargotransportationauthservice.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.tsavaph.cargotransportationauthservice.domain.*;
import ru.tsavaph.cargotransportationauthservice.service.authentication.AuthenticationService;
import ru.tsavaph.cargotransportationauthservice.service.register.RegisterService;
import ru.tsavaph.cargotransportationauthservice.service.verification.VerificationService;

class AuthenticationControllerTest {

    private RegisterService registerServiceMock;
    private AuthenticationService authenticationServiceMock;
    private VerificationService verificationServiceMock;
    private AuthenticationController controller;

    @BeforeEach
    void init() {
        registerServiceMock = Mockito.mock(RegisterService.class);
        authenticationServiceMock = Mockito.mock(AuthenticationService.class);
        verificationServiceMock = Mockito.mock(VerificationService.class);
        controller = new AuthenticationController(
                registerServiceMock,
                authenticationServiceMock,
                verificationServiceMock
        );
    }

    @Test
    void register() {
        var registerRequest = RegisterRequest.builder()
                .name("test")
                .phoneNumber("+71234567890")
                .email("test@gmail.com")
                .password("password")
                .build();
        var authenticationResponse = new AuthenticationResponse("test_token");
        Mockito.when(registerServiceMock.register(registerRequest)).thenReturn(authenticationResponse);
        var result = controller.register(registerRequest);
        Mockito.verify(registerServiceMock).register(registerRequest);
        Assertions.assertEquals(authenticationResponse, result.getBody());
    }

    @Test
    void authenticate() {
        var authRequest = AuthenticationRequest.builder()
                .phoneNumber("+71234567890")
                .password("password")
                .build();
        var authenticationResponse = new AuthenticationResponse("test_token");
        Mockito.when(authenticationServiceMock.authenticate(authRequest)).thenReturn(authenticationResponse);
        var result = controller.authenticate(authRequest);
        Mockito.verify(authenticationServiceMock).authenticate(authRequest);
        Assertions.assertEquals(authenticationResponse, result.getBody());
    }

    @Test
    void verify() {
        var verifyRequest = new VerifyRequest("test_token");
        var verifyResponse = new VerifyResponse(true);
        Mockito.when(verificationServiceMock.verify(verifyRequest)).thenReturn(verifyResponse);
        var result = controller.verify(verifyRequest);
        Mockito.verify(verificationServiceMock).verify(verifyRequest);
        Assertions.assertEquals(verifyResponse, result.getBody());
    }

}
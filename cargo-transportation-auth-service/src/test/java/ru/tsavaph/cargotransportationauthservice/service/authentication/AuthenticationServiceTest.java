package ru.tsavaph.cargotransportationauthservice.service.authentication;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.tsavaph.cargotransportationauthservice.domain.AuthenticationRequest;
import ru.tsavaph.cargotransportationauthservice.domain.user.Role;
import ru.tsavaph.cargotransportationauthservice.domain.user.User;
import ru.tsavaph.cargotransportationauthservice.exception.UserNotFoundException;
import ru.tsavaph.cargotransportationauthservice.repository.UserRepository;
import ru.tsavaph.cargotransportationauthservice.feign.JwtService;
import ru.tsavaph.cargotransportationauthservice.service.TokenService;

import java.util.Optional;

class AuthenticationServiceTest {

    private UserRepository repositoryMock;
    private TokenService tokenService;
    private AuthenticationService authenticationService;

    @BeforeEach
    void init() {
        repositoryMock = Mockito.mock(UserRepository.class);
        tokenService = Mockito.mock(TokenService.class);
        authenticationService = new AuthenticationService(repositoryMock, tokenService);
    }

    @Test
    void authenticate_success() {
        var phoneNumber = "+71234567890";
        var authRequest = AuthenticationRequest.builder()
                .phoneNumber(phoneNumber)
                .password("password")
                .build();
        var user = User.builder()
                .id(1L)
                .phoneNumber(phoneNumber)
                .password("encoded_password")
                .role(Role.USER)
                .build();
        var token = "TOKEN";

        Mockito.when(repositoryMock.findByPhoneNumber(phoneNumber)).thenReturn(Optional.of(user));
        Mockito.when(tokenService.generateToken(user)).thenReturn(token);

        var result =  authenticationService.authenticate(authRequest);

        Mockito.verify(repositoryMock).findByPhoneNumber(phoneNumber);
        Mockito.verify(tokenService).generateToken(user);

        Assertions.assertEquals(token, result.getToken());
    }

    @Test
    void authenticate_exception() {
        var phoneNumber = "+71234567890";
        var authRequest = AuthenticationRequest.builder()
                .phoneNumber(phoneNumber)
                .password("password")
                .build();

        Mockito.when(repositoryMock.findByPhoneNumber(phoneNumber)).thenThrow(UserNotFoundException.class);

        Assertions.assertThrows(UserNotFoundException.class, () -> authenticationService.authenticate(authRequest));

        Mockito.verify(repositoryMock).findByPhoneNumber(phoneNumber);
        Mockito.verifyNoInteractions(tokenService);

    }

}
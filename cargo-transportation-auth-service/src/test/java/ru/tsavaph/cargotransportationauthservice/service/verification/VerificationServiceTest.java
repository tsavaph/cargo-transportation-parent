package ru.tsavaph.cargotransportationauthservice.service.verification;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import ru.tsavaph.cargotransportationauthservice.domain.VerifyRequest;
import ru.tsavaph.cargotransportationauthservice.domain.user.User;
import ru.tsavaph.cargotransportationauthservice.repository.UserRepository;
import ru.tsavaph.cargotransportationauthservice.service.JwtService;

import java.util.Optional;

class VerificationServiceTest {

    private UserRepository repositoryMock;
    private JwtService jwtServiceMock;
    private VerificationService verificationService;

    @BeforeEach
    void init() {
        repositoryMock = Mockito.mock(UserRepository.class);
        jwtServiceMock = Mockito.mock(JwtService.class);
        verificationService = new VerificationService(repositoryMock, jwtServiceMock);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void verify_userNotAbsent(boolean isValid) {
        var token = "token";
        var login = "login";
        var user = User.builder().build();

        Mockito.when(jwtServiceMock.extractLogin(token)).thenReturn(login);
        Mockito.when(repositoryMock.findByPhoneNumber(login)).thenReturn(Optional.of(user));
        Mockito.when(jwtServiceMock.isTokenValid(token, user)).thenReturn(isValid);

        var verifyRequest = VerifyRequest.builder()
                .token(token)
                .build();

        var result = verificationService.verify(verifyRequest);

        Mockito.verify(jwtServiceMock).extractLogin(token);
        Mockito.verify(repositoryMock).findByPhoneNumber(login);
        Mockito.verify(jwtServiceMock).isTokenValid(token, user);

        Assertions.assertEquals(isValid, result.getIsTokenValid());
    }

    @Test
    void verify_userAbsent() {
        var token = "token";
        var login = "login";

        Mockito.when(jwtServiceMock.extractLogin(token)).thenReturn(login);
        Mockito.when(repositoryMock.findByPhoneNumber(login)).thenReturn(Optional.empty());

        var verifyRequest = VerifyRequest.builder()
                .token(token)
                .build();

        var result = verificationService.verify(verifyRequest);

        Mockito.verify(jwtServiceMock).extractLogin(token);
        Mockito.verify(repositoryMock).findByPhoneNumber(login);

        Assertions.assertEquals(false, result.getIsTokenValid());
    }

}
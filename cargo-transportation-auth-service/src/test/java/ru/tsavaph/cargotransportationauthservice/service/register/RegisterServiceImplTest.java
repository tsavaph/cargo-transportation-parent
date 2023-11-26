package ru.tsavaph.cargotransportationauthservice.service.register;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.tsavaph.cargotransportationauthservice.domain.RegisterRequest;
import ru.tsavaph.cargotransportationauthservice.domain.user.User;
import ru.tsavaph.cargotransportationauthservice.exception.UserAlreadyExistsException;
import ru.tsavaph.cargotransportationauthservice.repository.UserRepository;
import ru.tsavaph.cargotransportationauthservice.service.JwtService;
import ru.tsavaph.cargotransportationauthservice.service.authentication.AuthenticationService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceImplTest {

    private UserRepository repositoryMock;
    private PasswordEncoder passwordEncoderMock;
    private JwtService jwtServiceMock;
    private RegisterService registerService;

    @BeforeEach
    void init() {
        repositoryMock = Mockito.mock(UserRepository.class);
        passwordEncoderMock = Mockito.mock(PasswordEncoder.class);
        jwtServiceMock = Mockito.mock(JwtService.class);
        registerService = new RegisterServiceImpl(repositoryMock, passwordEncoderMock, jwtServiceMock);
    }

    @Test
    void register_success() {
        var phoneNumber = "+71234567890";
        var registerRequest = RegisterRequest.builder()
                .name("test")
                .phoneNumber(phoneNumber)
                .email("test@gmail.com")
                .password("password")
                .build();

        var token = "TOKEN";
        Mockito.when(jwtServiceMock.generateToken(Mockito.any(User.class))).thenReturn(token);
        Mockito.when(repositoryMock.findByPhoneNumber(phoneNumber)).thenReturn(Optional.empty());
        var result = registerService.register(registerRequest);

        Mockito.verify(passwordEncoderMock).encode(registerRequest.getPassword());
        Mockito.verify(repositoryMock).save(Mockito.any(User.class));
        Mockito.verify(repositoryMock).findByPhoneNumber(phoneNumber);
        Mockito.verify(jwtServiceMock).generateToken(Mockito.any(User.class));

        Assertions.assertEquals(token, result.getToken());

    }

    @Test
    void register_exception() {
        var phoneNumber = "+71234567890";
        var registerRequest = RegisterRequest.builder()
                .name("test")
                .phoneNumber(phoneNumber)
                .email("test@gmail.com")
                .password("password")
                .build();

        var token = "TOKEN";
        Mockito.when(repositoryMock.findByPhoneNumber(phoneNumber)).thenReturn(Optional.of(User.builder().build()));
        Mockito.when(jwtServiceMock.generateToken(Mockito.any(User.class))).thenReturn(token);

        Assertions.assertThrows(
                UserAlreadyExistsException.class,
                () -> registerService.register(registerRequest)
        );

        Mockito.verify(repositoryMock).findByPhoneNumber(phoneNumber);
    }

}
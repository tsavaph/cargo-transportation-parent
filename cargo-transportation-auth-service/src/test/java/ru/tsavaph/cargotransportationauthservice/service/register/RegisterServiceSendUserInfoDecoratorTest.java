package ru.tsavaph.cargotransportationauthservice.service.register;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.tsavaph.cargotransportationauthservice.domain.RegisterRequest;
import ru.tsavaph.cargotransportationauthservice.feign.UserInfoService;
import ru.tsavaph.cargotransportationauthservice.repository.UserRepository;
import ru.tsavaph.cargotransportationauthservice.service.JwtService;

import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceSendUserInfoDecoratorTest {

    private RegisterService registerServiceMock;
    private UserInfoService userInfoServiceMock;
    private RegisterService registerServiceDecorator;

    @BeforeEach
    void init() {
        registerServiceMock = Mockito.mock(RegisterService.class);
        userInfoServiceMock = Mockito.mock(UserInfoService.class);
        registerServiceDecorator = new RegisterServiceSendUserInfoDecorator(registerServiceMock, userInfoServiceMock);
    }

    @Test
    void register() {
        var registerRequest = RegisterRequest.builder()
                .name("test")
                .phoneNumber("+71234567890")
                .email("test@gmail.com")
                .password("password")
                .build();
    }
}
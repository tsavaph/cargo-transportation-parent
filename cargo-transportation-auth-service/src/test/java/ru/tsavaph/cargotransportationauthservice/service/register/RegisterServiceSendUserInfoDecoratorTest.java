package ru.tsavaph.cargotransportationauthservice.service.register;

import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.tsavaph.cargotransportationauthservice.domain.AuthenticationResponse;
import ru.tsavaph.cargotransportationauthservice.domain.RegisterRequest;
import ru.tsavaph.cargotransportationauthservice.domain.UserInfoDto;
import ru.tsavaph.cargotransportationauthservice.exception.UserInfoServiceException;
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
    void register_success() {
        var registerRequest = RegisterRequest.builder()
                .name("test")
                .phoneNumber("+71234567890")
                .email("test@gmail.com")
                .password("password")
                .build();
        var expected = AuthenticationResponse.builder().build();
        Mockito.when(registerServiceMock.register(registerRequest)).thenReturn(expected);

        var result = registerServiceDecorator.register(registerRequest);
        Assertions.assertEquals(expected, result);

        Mockito.verify(registerServiceMock).register(registerRequest);

        var userInfo = UserInfoDto.builder()
                .name(registerRequest.getName())
                .login(registerRequest.getPhoneNumber())
                .email(registerRequest.getEmail())
                .build();
        Mockito.verify(userInfoServiceMock).sendUserInfo(userInfo);
    }

    @Test
    void register_feignException() {
        var registerRequest = RegisterRequest.builder()
                .name("test")
                .phoneNumber("+71234567890")
                .email("test@gmail.com")
                .password("password")
                .build();
        var response = AuthenticationResponse.builder().build();
        Mockito.when(registerServiceMock.register(registerRequest)).thenReturn(response);
        var userInfo = UserInfoDto.builder()
                .name(registerRequest.getName())
                .login(registerRequest.getPhoneNumber())
                .email(registerRequest.getEmail())
                .build();

        var feignExceptionMock = Mockito.mock(FeignException.class);
        Mockito.when(feignExceptionMock.status()).thenReturn(404);

        Mockito.doThrow(feignExceptionMock)
                .when(userInfoServiceMock)
                .sendUserInfo(userInfo);

        Assertions.assertThrows(
                UserInfoServiceException.class,
                () -> registerServiceDecorator.register(registerRequest)
        );

    }
}
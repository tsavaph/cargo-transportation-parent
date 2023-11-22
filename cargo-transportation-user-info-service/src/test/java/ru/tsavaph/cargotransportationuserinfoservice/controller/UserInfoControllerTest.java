package ru.tsavaph.cargotransportationuserinfoservice.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import ru.tsavaph.cargotransportationuserinfoservice.controller.UserInfoController;
import ru.tsavaph.cargotransportationuserinfoservice.domain.UserInfoDto;
import ru.tsavaph.cargotransportationuserinfoservice.service.UserInfoService;

class UserInfoControllerTest {

    private UserInfoService userInfoServiceMock;
    private UserInfoController userInfoController;

    @BeforeEach
    void init() {
        userInfoServiceMock = Mockito.mock(UserInfoService.class);
        userInfoController = new UserInfoController(userInfoServiceMock);
    }

    @Test
    void saveUserInfo() {
        var userInfoDto = UserInfoDto.builder().build();
        Mockito.when(userInfoServiceMock.saveUserInfo(userInfoDto)).thenReturn(userInfoDto);
        var result = userInfoController.saveUserInfo(userInfoDto);

        Mockito.verify(userInfoServiceMock).saveUserInfo(userInfoDto);
        Assertions.assertEquals(userInfoDto, result.getBody());
        Assertions.assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
    }

    @Test
    void getUserInfo() {
        var userInfoDto = UserInfoDto.builder().build();
        var login = "+77777777777";
        Mockito.when(userInfoServiceMock.getUserInfo(login)).thenReturn(userInfoDto);

        var result = userInfoController.getUserInfo(login);
        Mockito.verify(userInfoServiceMock).getUserInfo(login);
        Assertions.assertEquals(userInfoDto, result.getBody());
        Assertions.assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
    }
}
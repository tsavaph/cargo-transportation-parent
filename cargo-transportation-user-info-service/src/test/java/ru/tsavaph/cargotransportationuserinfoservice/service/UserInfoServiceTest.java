package ru.tsavaph.cargotransportationuserinfoservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.tsavaph.cargotransportationuserinfoservice.domain.UserInfoDto;
import ru.tsavaph.cargotransportationuserinfoservice.domain.UserInfoEntity;
import ru.tsavaph.cargotransportationuserinfoservice.exception.UserInfoNotFoundException;
import ru.tsavaph.cargotransportationuserinfoservice.mapper.UserInfoMapper;
import ru.tsavaph.cargotransportationuserinfoservice.repository.UserInfoRepository;

import java.util.Optional;

class UserInfoServiceTest {

    private UserInfoRepository userInfoRepositoryMock;
    private UserInfoService userInfoService;

    @BeforeEach
    void init() {
        userInfoRepositoryMock = Mockito.mock(UserInfoRepository.class);
        userInfoService = new UserInfoService(userInfoRepositoryMock);
    }

    @Test
    void saveUserInfo_save() {
        var login = "login";
        var userInfoDto = UserInfoDto.builder()
                .name("name")
                .login(login)
                .email("email")
                .build();
        var userInfoDtoEntity = UserInfoMapper.map(userInfoDto);
        Mockito.when(userInfoRepositoryMock.findByLogin(login)).thenReturn(Optional.empty());

        var result = userInfoService.saveUserInfo(userInfoDto);

        Mockito.verify(userInfoRepositoryMock).findByLogin(login);
        Mockito.verify(userInfoRepositoryMock).save(userInfoDtoEntity);
        Assertions.assertEquals(userInfoDto, result);
    }

    @Test
    void saveUserInfo_update() {
        var login = "login";
        var userInfoDto = UserInfoDto.builder()
                .name("name")
                .login(login)
                .email("email")
                .build();
        var userInfoEntity = UserInfoMapper.map(userInfoDto);
        userInfoEntity.setId(1L);
        Mockito.when(userInfoRepositoryMock.findByLogin(login)).thenReturn(Optional.of(userInfoEntity));

        var result = userInfoService.saveUserInfo(userInfoDto);

        Mockito.verify(userInfoRepositoryMock).findByLogin(login);
        Mockito.verify(userInfoRepositoryMock).save(userInfoEntity);
        Assertions.assertEquals(userInfoDto, result);
    }

    @Test
    void getUserInfo_success() {
        var login = "login";
        var userInfoEntity = UserInfoEntity.builder()
                .id(1L)
                .name("name")
                .login(login)
                .email("email")
                .build();
        Mockito.when(userInfoRepositoryMock.findByLogin(login)).thenReturn(Optional.of(userInfoEntity));

        var result = userInfoService.getUserInfo(login);
        Mockito.verify(userInfoRepositoryMock).findByLogin(login);
        Assertions.assertEquals(UserInfoMapper.map(userInfoEntity), result);
    }

    @Test
    void getUserInfo_exception() {
        var login = "login";
        Mockito.when(userInfoRepositoryMock.findByLogin(login)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserInfoNotFoundException.class, () -> userInfoService.getUserInfo(login));
        Mockito.verify(userInfoRepositoryMock).findByLogin(login);
    }

}
package ru.tsavaph.cargotransportationuserinfoservice.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.tsavaph.cargotransportationuserinfoservice.domain.UserInfoDto;
import ru.tsavaph.cargotransportationuserinfoservice.domain.UserInfoEntity;

import static org.junit.jupiter.api.Assertions.*;

class UserInfoMapperTest {

    @Test
    void mapUserInfoEntity() {
        var login = "login";
        var email = "email";
        var name = "name";

        var userInfoDto = UserInfoDto.builder()
                .login(login)
                .email(email)
                .name(name)
                .build();

        var userInfoEntity = UserInfoEntity.builder()
                .id(1L)
                .login(login)
                .email(email)
                .name(name)
                .build();

        Assertions.assertEquals(userInfoDto, UserInfoMapper.map(userInfoEntity));
    }

    @Test
    void mapUserInfoDto() {
        var login = "login";
        var email = "email";
        var name = "name";

        var userInfoDto = UserInfoDto.builder()
                .login(login)
                .email(email)
                .name(name)
                .build();

        var userInfoEntity = UserInfoEntity.builder()
                .login(login)
                .email(email)
                .name(name)
                .build();

        Assertions.assertEquals(userInfoEntity, UserInfoMapper.map(userInfoDto));
    }
}
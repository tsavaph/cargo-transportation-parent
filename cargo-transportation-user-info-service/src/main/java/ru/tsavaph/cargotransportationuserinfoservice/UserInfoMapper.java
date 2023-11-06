package ru.tsavaph.cargotransportationuserinfoservice;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.tsavaph.cargotransportationuserinfoservice.domain.UserInfoDto;
import ru.tsavaph.cargotransportationuserinfoservice.domain.UserInfoEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserInfoMapper {

    public static UserInfoEntity map(UserInfoDto userInfoDto) {
        return UserInfoEntity.builder()
                .name(userInfoDto.getName())
                .email(userInfoDto.getEmail())
                .login(userInfoDto.getLogin())
                .build();
    }

    public static UserInfoDto map(UserInfoEntity userInfoEntity) {
        return UserInfoDto.builder()
                .name(userInfoEntity.getName())
                .email(userInfoEntity.getEmail())
                .login(userInfoEntity.getLogin())
                .build();
    }

}

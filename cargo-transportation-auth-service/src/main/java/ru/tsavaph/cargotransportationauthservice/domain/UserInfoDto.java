package ru.tsavaph.cargotransportationauthservice.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoDto {

    private String name;
    private String phoneNumber;
    private String email;

}

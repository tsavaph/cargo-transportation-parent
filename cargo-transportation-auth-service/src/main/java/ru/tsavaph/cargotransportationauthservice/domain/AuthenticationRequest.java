package ru.tsavaph.cargotransportationauthservice.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import ru.tsavaph.cargotransportationauthservice.configuration.validator.PhoneNumber;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @PhoneNumber
    private String phoneNumber;

    @NotNull
    private String password;

}

package ru.tsavaph.cargotransportationauthservice.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import ru.tsavaph.cargotransportationauthservice.configuration.validator.PhoneNumber;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotNull
    @Size(max = 255)
    private String name;

    @PhoneNumber
    private String phoneNumber;

    @Email
    private String email;

    @NotNull
    private String password;

}

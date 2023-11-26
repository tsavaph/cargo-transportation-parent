package ru.tsavaph.cargotransportationauthservice.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import ru.tsavaph.cargotransportationauthservice.domain.jwt.ExtractLoginRequest;
import ru.tsavaph.cargotransportationauthservice.domain.jwt.GenerateTokenRequest;
import ru.tsavaph.cargotransportationauthservice.domain.jwt.VerifyTokenRequest;
import ru.tsavaph.cargotransportationauthservice.domain.user.User;
import ru.tsavaph.cargotransportationauthservice.exception.JwtServiceException;
import ru.tsavaph.cargotransportationauthservice.feign.JwtService;

@RequiredArgsConstructor
public class TokenService {

    private final JwtService jwtService;

    public String generateToken(User user) {
        var generateTokenRequest = new GenerateTokenRequest(user.getPhoneNumber());
        try {
            var response = jwtService.generate(generateTokenRequest);
            return response.getToken();
        } catch (FeignException exception) {
            throw new JwtServiceException(
                    exception.getMessage(),
                    HttpStatus.valueOf(exception.status())
            );
        }
    }

    public String extractLogin(String jwt) {
        var extractLoginRequest = new ExtractLoginRequest(jwt);
        try {
            var response = jwtService.extractLogin(extractLoginRequest);
            return response.getLogin();
        } catch (FeignException exception) {
            throw new JwtServiceException(
                    exception.getMessage(),
                    HttpStatus.valueOf(exception.status())
            );
        }
    }

    public Boolean isTokenValid(String jwt, User user) {
        var verifyTokenRequest = new VerifyTokenRequest(jwt, user.getPhoneNumber());
        try {
            var response = jwtService.verify(verifyTokenRequest);
            return response.getIsTokenValid();
        } catch (FeignException exception) {
            throw new JwtServiceException(
                    exception.getMessage(),
                    HttpStatus.valueOf(exception.status())
            );
        }
    }
}

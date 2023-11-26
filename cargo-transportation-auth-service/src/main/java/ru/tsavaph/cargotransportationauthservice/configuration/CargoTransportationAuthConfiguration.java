package ru.tsavaph.cargotransportationauthservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.tsavaph.cargotransportationauthservice.feign.UserInfoService;
import ru.tsavaph.cargotransportationauthservice.repository.UserRepository;
import ru.tsavaph.cargotransportationauthservice.feign.JwtService;
import ru.tsavaph.cargotransportationauthservice.service.TokenService;
import ru.tsavaph.cargotransportationauthservice.service.authentication.AuthenticationService;
import ru.tsavaph.cargotransportationauthservice.service.register.RegisterService;
import ru.tsavaph.cargotransportationauthservice.service.register.RegisterServiceImpl;
import ru.tsavaph.cargotransportationauthservice.service.register.RegisterServiceSendUserInfoDecorator;
import ru.tsavaph.cargotransportationauthservice.service.verification.VerificationService;

import java.util.concurrent.TimeUnit;

@Configuration
public class CargoTransportationAuthConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenService tokenService(JwtService jwtService) {
        return new TokenService(jwtService);
    }

    @Bean
    public AuthenticationService authenticationService(UserRepository repository, TokenService tokenService) {
        return new AuthenticationService(repository, tokenService);
    }

    @Bean
    public VerificationService verificationService(UserRepository repository, TokenService tokenService) {
        return new VerificationService(repository, tokenService);
    }

    @Bean
    public RegisterService registerService(
            UserRepository repository,
            PasswordEncoder passwordEncoder,
            TokenService tokenService,
            UserInfoService userInfoService) {

        var registerService = new RegisterServiceImpl(repository, passwordEncoder, tokenService);
        return new RegisterServiceSendUserInfoDecorator(registerService, userInfoService);
    }

}

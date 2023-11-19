package ru.tsavaph.cargotransportationauthservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import ru.tsavaph.cargotransportationauthservice.feign.UserInfoService;
import ru.tsavaph.cargotransportationauthservice.repository.UserRepository;
import ru.tsavaph.cargotransportationauthservice.service.JwtService;
import ru.tsavaph.cargotransportationauthservice.service.authentication.AuthenticationService;
import ru.tsavaph.cargotransportationauthservice.service.register.RegisterService;
import ru.tsavaph.cargotransportationauthservice.service.register.RegisterServiceImpl;
import ru.tsavaph.cargotransportationauthservice.service.register.RegisterServiceSendUserInfoDecorator;
import ru.tsavaph.cargotransportationauthservice.service.verification.VerificationService;

import java.util.concurrent.TimeUnit;

@Configuration
public class CargoTransportationAuthConfiguration {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.tokenLifetimeHours}")
    private long tokenLifetimeHours;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtService jwtService() {
        return new JwtService(
                TimeUnit.HOURS.toMillis(tokenLifetimeHours),
                secretKey
        );
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public AuthenticationService authenticationService(UserRepository repository, JwtService jwtService) {
        return new AuthenticationService(repository, jwtService);
    }

    @Bean
    public VerificationService verificationService(UserRepository repository, JwtService jwtService) {
        return new VerificationService(repository, jwtService);
    }

    @Bean
    RegisterService registerService(
            UserRepository repository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            UserInfoService userInfoService) {

        var registerService = new RegisterServiceImpl(repository, passwordEncoder, jwtService);
        return new RegisterServiceSendUserInfoDecorator(registerService, userInfoService);
    }

}

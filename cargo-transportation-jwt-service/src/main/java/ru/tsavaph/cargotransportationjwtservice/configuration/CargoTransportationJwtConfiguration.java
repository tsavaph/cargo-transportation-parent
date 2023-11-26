package ru.tsavaph.cargotransportationjwtservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tsavaph.cargotransportationjwtservice.service.JwtService;
import ru.tsavaph.cargotransportationjwtservice.service.TokenService;

import java.util.concurrent.TimeUnit;

@Configuration
public class CargoTransportationJwtConfiguration {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.tokenLifetimeHours}")
    private long tokenLifetimeHours;


    @Bean
    public TokenService tokenService() {
        return new TokenService(
                TimeUnit.HOURS.toMillis(tokenLifetimeHours),
                secretKey
        );
    }

    @Bean
    public JwtService jwtService(TokenService tokenService) {
        return new JwtService(tokenService);
    }

}

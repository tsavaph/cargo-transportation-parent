package ru.tsavaph.cargotransportationauthservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.tsavaph.cargotransportationauthservice.domain.jwt.*;

@FeignClient(name = "cargo-transportation-jwt-service")
public interface JwtService {

    @PostMapping(value = "${jwt-service.url.generate}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    GenerateTokenResponse generate(@RequestBody GenerateTokenRequest request);

    @PostMapping(value = "${jwt-service.url.verify}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    VerifyTokenResponse verify(@RequestBody VerifyTokenRequest request);

    @PostMapping(value = "${jwt-service.url.extract-login}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ExtractLoginResponse extractLogin(@RequestBody ExtractLoginRequest request);

}

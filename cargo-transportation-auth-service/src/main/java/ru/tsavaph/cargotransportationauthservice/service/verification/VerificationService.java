package ru.tsavaph.cargotransportationauthservice.service.verification;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tsavaph.cargotransportationauthservice.domain.VerifyRequest;
import ru.tsavaph.cargotransportationauthservice.domain.VerifyResponse;
import ru.tsavaph.cargotransportationauthservice.repository.UserRepository;
import ru.tsavaph.cargotransportationauthservice.service.JwtService;

@RequiredArgsConstructor
public class VerificationService {

    private final UserRepository repository;
    private final JwtService jwtService;

    @Transactional(readOnly = true)
    public VerifyResponse verify(VerifyRequest request) {
        var jwt = request.getToken();
        var login = jwtService.extractLogin(jwt);
        var user = repository.findByPhoneNumber(login);

        return new VerifyResponse(
                jwtService.isTokenValid(jwt, user.orElseThrow())
        );
    }

}

package ru.tsavaph.cargotransportationauthservice.service.verification;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tsavaph.cargotransportationauthservice.domain.VerifyRequest;
import ru.tsavaph.cargotransportationauthservice.domain.VerifyResponse;
import ru.tsavaph.cargotransportationauthservice.repository.UserRepository;
import ru.tsavaph.cargotransportationauthservice.service.TokenService;

@RequiredArgsConstructor
public class VerificationService {

    private final UserRepository repository;
    private final TokenService tokenService;

    @Transactional(readOnly = true)
    public VerifyResponse verify(VerifyRequest request) {
        var jwt = request.getToken();
        var login = tokenService.extractLogin(jwt);
        var user = repository.findByPhoneNumber(login);

        if (user.isPresent()) {
            return new VerifyResponse(
                    tokenService.isTokenValid(jwt, user.get())
            );
        }

        return new VerifyResponse(false);

    }

}

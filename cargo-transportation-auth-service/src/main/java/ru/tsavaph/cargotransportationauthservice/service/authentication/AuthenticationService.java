package ru.tsavaph.cargotransportationauthservice.service.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tsavaph.cargotransportationauthservice.domain.AuthenticationRequest;
import ru.tsavaph.cargotransportationauthservice.domain.AuthenticationResponse;
import ru.tsavaph.cargotransportationauthservice.exception.UserNotFoundException;
import ru.tsavaph.cargotransportationauthservice.repository.UserRepository;
import ru.tsavaph.cargotransportationauthservice.service.TokenService;

@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final TokenService tokenService;

    @Transactional(readOnly = true)
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = repository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new UserNotFoundException("User is not registered"));
        var jwtToken = tokenService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}

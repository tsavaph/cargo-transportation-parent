package ru.tsavaph.cargotransportationauthservice.service.register;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.tsavaph.cargotransportationauthservice.domain.*;
import ru.tsavaph.cargotransportationauthservice.exception.UserAlreadyExistsException;
import ru.tsavaph.cargotransportationauthservice.repository.UserRepository;
import ru.tsavaph.cargotransportationauthservice.domain.user.Role;
import ru.tsavaph.cargotransportationauthservice.domain.user.User;
import ru.tsavaph.cargotransportationauthservice.service.JwtService;

@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        var phoneNumber = request.getPhoneNumber();
        if (repository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new UserAlreadyExistsException("User " + phoneNumber + " already exists");
        }
        var user = User.builder()
                .phoneNumber(phoneNumber)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}

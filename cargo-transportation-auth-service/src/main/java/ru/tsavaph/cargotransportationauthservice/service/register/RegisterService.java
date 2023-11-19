package ru.tsavaph.cargotransportationauthservice.service.register;

import ru.tsavaph.cargotransportationauthservice.domain.AuthenticationResponse;
import ru.tsavaph.cargotransportationauthservice.domain.RegisterRequest;

public interface RegisterService {

    AuthenticationResponse register(RegisterRequest request);

}

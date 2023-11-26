package ru.tsavaph.cargotransportationauthservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tsavaph.cargotransportationauthservice.domain.*;
import ru.tsavaph.cargotransportationauthservice.service.authentication.AuthenticationService;
import ru.tsavaph.cargotransportationauthservice.service.register.RegisterService;
import ru.tsavaph.cargotransportationauthservice.service.verification.VerificationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final RegisterService registerService;
    private final AuthenticationService authenticationService;
    private final VerificationService verificationService;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        return ResponseEntity.ok(registerService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/verify")
    public ResponseEntity<VerifyResponse> verify(
            @RequestBody @Valid VerifyRequest request
    ) {
        return ResponseEntity.ok(verificationService.verify(request));
    }

}

package ru.tsavaph.cargotransportationjwtservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tsavaph.cargotransportationjwtservice.domain.*;
import ru.tsavaph.cargotransportationjwtservice.service.JwtService;

@RestController
@RequestMapping("/api/v1/jwt")
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;

    @PostMapping("/generate-token")
    public ResponseEntity<GenerateTokenResponse> generate(@RequestBody GenerateTokenRequest request) {
        return ResponseEntity.ok(jwtService.generateToken(request));
    }

    @PostMapping("/verify-token")
    public ResponseEntity<VerifyTokenResponse> verify(@RequestBody VerifyTokenRequest request) {
        return ResponseEntity.ok(jwtService.verifyToken(request));
    }

    @PostMapping("/extract-login")
    public ResponseEntity<ExtractLoginResponse> extractLogin(@RequestBody ExtractLoginRequest request) {
        return ResponseEntity.ok(jwtService.extractLogin(request));
    }

}
